package com.apirest.apirest.test.controllers;

import com.apirest.apirest.test.model.entity.User;
import com.apirest.apirest.test.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired

    private IUserService userService;


    @GetMapping(value = {"/list" })
    public List<User> getAll() {
        return userService.findAll();
    }


    @CrossOrigin
    @PostMapping(value = "/user")
    public void addUser(@RequestBody User user) {
         userService.save(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        if(id > 0) {
            userService.delete(id);
        }
    }

}
