package edu.miu.cs545.simplespringsecurity.controllers;

import edu.miu.cs545.simplespringsecurity.model.User;
import edu.miu.cs545.simplespringsecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    final private UserService _service;

    public UserController(@Qualifier("userServiceImplementation") UserService service) {
        this._service = service;
    }

    @GetMapping("")
    public List<User> getAll() {
        return _service.getAllUsers();
    }

    //@PutMapping
}
