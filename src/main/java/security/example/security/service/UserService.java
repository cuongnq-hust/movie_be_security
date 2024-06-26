package security.example.security.service;

import security.example.security.dto.UserDto;
import security.example.security.model.Role;
import security.example.security.model.User;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addToUser(String username, String rolename);

    User getUserByToken();

    User updateUser(UserDto userDto);
}
