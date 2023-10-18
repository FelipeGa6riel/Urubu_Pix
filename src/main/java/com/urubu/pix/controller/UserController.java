package com.urubu.pix.controller;

import com.urubu.pix.domain.user.User;
import com.urubu.pix.dtos.DataAuth;
import com.urubu.pix.dtos.DataTokenResponse;
import com.urubu.pix.dtos.DataUser;
import com.urubu.pix.repositories.UserRepository;
import com.urubu.pix.services.TokenService;
import com.urubu.pix.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody @Valid DataAuth dataAuth) {
        var userAuth = new UsernamePasswordAuthenticationToken(dataAuth.cpf(),dataAuth.password());
        var auth = this.authenticationManager.authenticate(userAuth);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new DataTokenResponse(token));
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<User> CreateUser(@RequestBody DataUser dataUser) {
        if(this.userRepository.findUsersByCpf(dataUser.cpf()) != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        String encodePassword = new BCryptPasswordEncoder().encode(dataUser.password());

        var newUser = new User(dataUser.nome(),dataUser.email(),dataUser.cpf(),encodePassword);
        userRepository.save(newUser);
        return  new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/")
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

    @PutMapping("/")
    @Transactional
    public ResponseEntity<User> updateUserData(@RequestBody DataUser dataUser) {
        var user = userService.findUserById(dataUser.id());
        user.updateUser(dataUser);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
