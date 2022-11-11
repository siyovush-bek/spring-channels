package me.siyovushbek.channels.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/register")
    ResponseEntity<Object> register(@RequestBody @Validated User user) {
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/users/{username}")
    User getUserDetails(@PathVariable String username) {
        return userService.getUserById(username);
    }

}
