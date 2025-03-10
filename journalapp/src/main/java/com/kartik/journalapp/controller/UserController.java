package com.kartik.journalapp.controller;

import com.kartik.journalapp.entity.User;
import com.kartik.journalapp.repository.UserRepository;
import com.kartik.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       User userInDb = userService.findByUsername(userName);
       if(userInDb != null){
           userInDb.setUserName(user.getUserName());
           userInDb.setPassword(user.getPassword());
           userService.saveEntry(userInDb);
       }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userRepository.deleteByUsername(authentication.getName());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}