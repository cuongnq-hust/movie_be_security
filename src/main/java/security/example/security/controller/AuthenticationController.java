package security.example.security.controller;

import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import security.example.security.auth.AuthenticationRequest;
import security.example.security.auth.RegisterRequest;
import security.example.security.dto.UserDto;
import security.example.security.model.Role;
import security.example.security.model.User;
import security.example.security.model.request.UploadFileRequest;
import security.example.security.service.impl.AuthenticationService;
import security.example.security.service.impl.AwsS3ServiceImpl;
import security.example.security.service.impl.UserServiceImpl;

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
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/user")
    public User getUserByToken(@RequestHeader(name = "Authorization") String accessToken) {
        return userService.getUserByToken(accessToken);
    }

    @PostMapping("/update")
    public User updateUserByToken(
            @RequestHeader(name = "Authorization") String accessToken,
            @RequestBody UserDto userDto
    ) {
        return userService.updateUser(userDto, accessToken);
    }

    @GetMapping("/roles")
    public List<Role> getUserRoles(@RequestHeader(name = "Authorization") String accessToken) {
        return userService.getUserRolesByUsername(accessToken);
    }
}
