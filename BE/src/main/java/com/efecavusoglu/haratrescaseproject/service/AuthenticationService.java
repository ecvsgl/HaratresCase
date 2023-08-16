package com.efecavusoglu.haratrescaseproject.service;

import com.efecavusoglu.haratrescaseproject.exception.UsernameAlreadyExistsException;
import com.efecavusoglu.haratrescaseproject.model.Role;
import com.efecavusoglu.haratrescaseproject.model.UserEntity;
import com.efecavusoglu.haratrescaseproject.model.dto.LoginResponse;
import com.efecavusoglu.haratrescaseproject.model.dto.UserRequest;
import com.efecavusoglu.haratrescaseproject.model.dto.UserResponse;
import com.efecavusoglu.haratrescaseproject.repository.RoleRepository;
import com.efecavusoglu.haratrescaseproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.config.Task;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final TaskService taskService;

    public UserResponse registerUser(UserRequest userRequest){
        if (userRepository.findUserEntityByUserName(userRequest.getUserName())!=null){
            throw new UsernameAlreadyExistsException("Please try another username.");
        }
        Set<Role> userAuthorities= new HashSet<>();
        userAuthorities.add(roleRepository.findRoleByAuthority("STANDARD_USER"));
        //Request to Entity mapping...
        UserEntity userEntity = UserEntity.builder()
                .userName(userRequest.getUserName())
                .userPassword(passwordEncoder.encode(userRequest.getUserPassword()))
                .authorities(userAuthorities)
                .build();
        userEntity = userRepository.save(userEntity);
        //Entity to Response mapping...
        UserResponse userResponse = UserResponse.builder()
                .userName(userEntity.getUsername())
                .authorities((Set<Role>) userEntity.getAuthorities())
                .build();
        return userResponse;
    }

    public LoginResponse loginUser(UserRequest userRequest){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getUserPassword())
            );
            String token = tokenService.generateJwt(auth);

            UserEntity userEntity = userRepository.findUserEntityByUserName(userRequest.getUserName());
            UserResponse userResponse = UserResponse.builder()
                    .userName(userEntity.getUsername())
                    .authorities((Set<Role>) userEntity.getAuthorities())
                    .userTaskResponseList(taskService.taskEntityListToTaskResponseListMapper(userEntity.getUserTaskEntityList()))
                    .build();
            LoginResponse response = new LoginResponse(userResponse,token);
            System.out.println(response.toString());
            return response;
        }catch (Exception e){
            return new LoginResponse(null,"");
        }
    }
}
