package com.efecavusoglu.haratrescaseproject.repository;

import com.efecavusoglu.haratrescaseproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByAuthority(String query);
}
