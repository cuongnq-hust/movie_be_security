package security.example.security.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import security.example.security.auth.AuthenticationRequest;
import security.example.security.auth.RegisterRequest;
import security.example.security.model.User;
import security.example.security.service.AuthenticationService;
import security.example.security.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserServiceImpl userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
    @GetMapping("/user")
    public User getUserByToken(@RequestHeader(name = "Authorization") String accessToken){
//        System.out.println("token dc vo la" + accessToken);
        return userService.getUserByToken(accessToken);
    }
}
