package com.apirest.apirest.test.controllers;

import com.apirest.apirest.test.model.entity.User;
import com.apirest.apirest.test.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired

    private IUserService userService;

    @CrossOrigin
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return userService.findAll();
    }


    @CrossOrigin
    @PostMapping(value = "/user")
    public void addUser(@RequestBody @Valid User user) {
         userService.save(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable(value = "id") Long id) {
        if(id > 0) {
            userService.delete(id);
        }
    }

}
