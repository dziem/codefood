package com.akmal.codefood.controller;

import com.akmal.codefood.entity.dto.UserDto;
import com.akmal.codefood.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class UserController extends BaseController {
    @Autowired
    private ApplicationUserService applicationUserService;

    @PostMapping("/register")
    public ResponseEntity<Object> create(@Valid @RequestBody UserDto form) {
        UserDto dto = applicationUserService.register(form);
        return ok(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserDto form) {
        return ok(applicationUserService.login(form));
    }
}
