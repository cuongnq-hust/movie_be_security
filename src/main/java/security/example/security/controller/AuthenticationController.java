package security.example.security.controller;

import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.auth.AuthenticationRequest;
import security.example.security.auth.RegisterRequest;
import security.example.security.dto.UserDto;
import security.example.security.model.Role;
import security.example.security.model.User;
import security.example.security.service.AuthenticationService;
import security.example.security.service.AwsS3ServiceImpl;
import security.example.security.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
        @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
        @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."),
}
)
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserServiceImpl userService;
    @Autowired
    private AwsS3ServiceImpl awsS3Service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByToken() {
        return ResponseEntity.ok(userService.getUserByToken());
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUserByToken(
            @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getUserRoles() {
        return ResponseEntity.ok(userService.getUserRolesByUsername());
    }
}
