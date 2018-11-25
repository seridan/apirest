package com.apirest.apirest.test.controllers;

import com.apirest.apirest.test.model.entity.User;
import com.apirest.apirest.test.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    @Qualifier("service")
    private IUserService userService;

    @GetMapping(value = "/list")
    public List<User> getAll() {
        return userService.findAll();
    }


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
