package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Api(tags = "User Controller API", value = "UserControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("BCryptEncoder")
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/users")
    @ApiOperation(value = "getAllUsers", notes = "Fetch List all users", nickname = "users list")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server error"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 200, message = "User fetched", response = User.class, responseContainer = "List") })
    public List<User> getAllUsers(Authentication authentication) {

        return userService.findAllUsers();

    }

    @PostMapping("/users")
    @ApiOperation(value = "Save user")
    public ResponseEntity<User> saveusers(@RequestBody User newUser, Authentication auth) {
        System.out.println(newUser.getUserName() + "  " + auth.getName());
        // bcryptPasswordEncoder=new BCryptPasswordEncoder(BCryptVersion.$2Y, 12);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body((userService.saveUser(newUser)));

    }

    @PreAuthorize("@userSecurity.hasUserId(authentication,#userId)")
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@ApiParam(value = "userId", required = true) @PathVariable("userId") Long userId,
            Authentication authentication) {
        System.out.println("Inside getuserbyid method");
        return ResponseEntity.ok().body(userService.findUserById(userId).get());

    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long UserId, @RequestBody User newUser) {
        return ResponseEntity.ok().body(userService.updateUser(UserId, newUser));

    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Long UserId) {
        userService.deleteUser(UserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/users/search")
    @PostAuthorize("returnObject.body.userName==authenticated.user")
    public ResponseEntity<User> userDetails(Authentication authentication, @RequestParam("cname") String cName) throws Exception {
        System.out.println(authentication.getName().toString());
        User User = userService.findByUserName(cName);
        if (User == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok().body(User);

    }

}
