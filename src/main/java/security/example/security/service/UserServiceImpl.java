package security.example.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security.example.security.dto.UserDto;
import security.example.security.model.Role;
import security.example.security.model.User;
import security.example.security.repository.RoleRepository;
import security.example.security.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addToUser(String username, String rolename) {
        if (!userRepository.findByEmail(username).isPresent()) {
            throw new IllegalArgumentException("User witth email " + username + "does not exits");
        }
        Role role = roleRepository.findByName(rolename);
        if (role == null) {
            throw new IllegalArgumentException("Role witth name " + rolename + "does not exits");
        }
        User user = userRepository.findByEmail(username).get();
        user.getRoles().add(role);
    }

    @Override
    public User getUserByToken() {
        DecodedJWT jwt = jwtService.decodeToken();
        String userName = jwt.getSubject();
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userName));
        return user;
    }

    @Override
    public User updateUser(UserDto userDto) {
        DecodedJWT jwt = jwtService.decodeToken();
        String userNameByToken = jwt.getSubject();
        User user = userRepository.findByEmail(userNameByToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userNameByToken));
        user.setUser_name(userDto.getUser_name());
        user.setImage(userDto.getImage());
        user.setMobile_number(userDto.getMobile_number());
        return userRepository.save(user);
    }
    public List<Role> getUserRolesByUsername() {
        DecodedJWT jwt = jwtService.decodeToken();
        String userNameByToken = jwt.getSubject();
        return roleRepository.findUserRolesByEmail(userNameByToken);
    }

}
