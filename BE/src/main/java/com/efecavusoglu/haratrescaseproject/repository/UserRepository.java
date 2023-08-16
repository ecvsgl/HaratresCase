package com.efecavusoglu.haratrescaseproject.repository;

import com.efecavusoglu.haratrescaseproject.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findUserEntityByUserName(String username);
}
