package com.urubu.pix.controller;

import com.urubu.pix.domain.user.User;
import com.urubu.pix.dtos.DataUser;
import com.urubu.pix.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<User> CreateUser(@RequestBody DataUser dataUser) {
        var newUser = userService.createUser(dataUser);;
        return  new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        var users = userService.finAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<User> updateUserData(@RequestBody DataUser dataUser) {
        var user = userService.findUserById(dataUser.id());
        user.updateUser(dataUser);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateBalance(@PathVariable Long id,@RequestBody DataUser dataUser) {
        var user = userService.findUserById(id);
        user.updateBalanceUser(user,dataUser.balance());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
