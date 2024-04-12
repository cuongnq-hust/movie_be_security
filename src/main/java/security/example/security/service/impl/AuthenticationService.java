package security.example.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import security.example.security.auth.AuthenticationRequest;
import security.example.security.auth.AuthenticationResponse;
import security.example.security.auth.RegisterRequest;
import security.example.security.model.Role;
import security.example.security.model.User;
import security.example.security.repository.RoleCustomRepo;
import security.example.security.repository.UserRepository;
import security.example.security.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleCustomRepo roleCustomRepo;
    private final UserService userService;

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        try {
            if (userRepository.existsById(registerRequest.getEmail().toString())) {
                throw new IllegalArgumentException("User with " + registerRequest.getEmail().toString() + "email already");
            }
            userService.saveUser(
                    new User(registerRequest.getMobile_number(),
                            registerRequest.getUser_name(),
                            registerRequest.getEmail(),
                            registerRequest.getPassword(),
                            new HashSet<>(),
                            registerRequest.getImage()));
            userService.addToUser(registerRequest.getEmail(), "ROLE_USER");
            User user = userRepository.findByEmail(registerRequest.getEmail()).orElseThrow();
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) {
        try {
            User user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            List<Role> roles = roleCustomRepo.getRole(user);

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<Role> roleSet = new HashSet<>();
            roles.forEach(role -> {
                roleSet.add(role);
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

            user.setRoles(roleSet);

            String jwtAccessToken = jwtService.generateToken(user, authorities);
            String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .access_token(jwtAccessToken)
                    .refresh_token(jwtRefreshToken)
                    .email(user.getEmail())
                    .user_name(user.getUser_name())
                    .roles(roles)
                    .build());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid Credential");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
