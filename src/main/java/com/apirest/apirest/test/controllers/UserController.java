package com.apirest.apirest.test.controllers;

import com.apirest.apirest.test.model.entity.User;
import com.apirest.apirest.test.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @CrossOrigin
    @GetMapping(value = {"/list"})
    public List<User> getAll() {
        return userService.findAll();
    }


    @Secured({"ROLE_ADMIN"})
    @CrossOrigin
    @PostMapping(value = "/user")
    public void addUser(@RequestBody User user) {
        String pass = user.getPassword();
        String bcryptPassword = passwordEncoder.encode(pass);
        user.setPassword(bcryptPassword);
         userService.save(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        if(id > 0) {
            userService.delete(id);
        }
    }

}
