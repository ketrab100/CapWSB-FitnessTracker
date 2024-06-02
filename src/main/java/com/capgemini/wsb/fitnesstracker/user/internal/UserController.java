package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserSimpleMapper userSimpleMapper;
    private final UserMapper userMapper;
    private final UserBaseMapper userBaseMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(required = true, name = "id") long id) {
        var user = userService.getUser(id)
                .stream()
                .map(userMapper::toDto)
                .findFirst();
        return user.map(userSimpleDto -> ResponseEntity.ok().body(userSimpleDto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/simple")
    public ResponseEntity<List<UserSimpleDto>> getSimpleUser() {
        var users = userService.findAllUsers()
                .stream()
                .map(userSimpleMapper::toDto)
                .toList();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) throws InterruptedException {
        var user = userMapper.toEntity(userDto);
        user = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@PathVariable(required = true, name = "id") long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<List<UserBaseDto>> getUserByEmail(@RequestParam("email") String email) {
        var users = userService.findUsersByEmailLike(email).stream().map(userBaseMapper::toDto).toList();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> getUserOlderThan(@PathVariable(required = true, name = "time") LocalDate time) {
        var users = userService.findUsersOlderThan(time).stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userRequest) {
        var userEntity = userMapper.toEntity(userRequest);
        var user = userService.updateUser(id, userEntity);
        var userResponse = userMapper.toDto(user);
        return ResponseEntity.ok().body(userResponse);
    }

}