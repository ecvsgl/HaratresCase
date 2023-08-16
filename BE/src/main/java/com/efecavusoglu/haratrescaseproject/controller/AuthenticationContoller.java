package com.efecavusoglu.haratrescaseproject.controller;

import com.efecavusoglu.haratrescaseproject.model.dto.LoginResponse;
import com.efecavusoglu.haratrescaseproject.model.dto.UserRequest;
import com.efecavusoglu.haratrescaseproject.model.dto.UserResponse;
import com.efecavusoglu.haratrescaseproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
public class AuthenticationContoller {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest){
        return authenticationService.registerUser(userRequest);
    }
    @PostMapping("/auth/login")
    public LoginResponse loginUser(@RequestBody UserRequest userRequest){
        return authenticationService.loginUser(userRequest);
    }

}
