package com.efecavusoglu.haratrescaseproject.service;

import com.efecavusoglu.haratrescaseproject.exception.UsernameAlreadyExistsException;
import com.efecavusoglu.haratrescaseproject.model.Role;
import com.efecavusoglu.haratrescaseproject.model.UserEntity;
import com.efecavusoglu.haratrescaseproject.model.dto.UserRequest;
import com.efecavusoglu.haratrescaseproject.model.dto.UserResponse;
import com.efecavusoglu.haratrescaseproject.repository.RoleRepository;
import com.efecavusoglu.haratrescaseproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity tempUserEntity = userRepository.findUserEntityByUserName(username);
        if (tempUserEntity==null){
            throw new UsernameNotFoundException("Such username does not exist.");
        }
        return tempUserEntity;
    }
}
