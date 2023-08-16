package com.efecavusoglu.haratrescaseproject.initialize;

import com.efecavusoglu.haratrescaseproject.model.Role;
import com.efecavusoglu.haratrescaseproject.model.TaskEntity;
import com.efecavusoglu.haratrescaseproject.model.UserEntity;
import com.efecavusoglu.haratrescaseproject.repository.RoleRepository;
import com.efecavusoglu.haratrescaseproject.repository.TaskRepository;
import com.efecavusoglu.haratrescaseproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class SystemDataInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception{
        if(userRepository.count()==0 && taskRepository.count()==0){
            //Test objects creation & persistance.

            //Roles.
            Role standardUserRole = new Role();
            standardUserRole.setAuthority("STANDARD_USER");
            standardUserRole = roleRepository.save(standardUserRole);
            Set<Role> standardUserRoleSet = new HashSet<>();
            standardUserRoleSet.add(standardUserRole);

            Role adminRole = new Role();
            adminRole.setAuthority("ADMIN");
            adminRole = roleRepository.save(adminRole);
            Set<Role> adminUserRoleSet = new HashSet<>();
            adminUserRoleSet.add(standardUserRole);
            adminUserRoleSet.add(adminRole);

            //Users.
            UserEntity admin = UserEntity.builder()
                    .userName("admin")
                    .userPassword(passwordEncoder.encode("admin"))
                    .authorities(adminUserRoleSet)
                    .build();

            UserEntity user1 = UserEntity.builder()
                    .userName("user1")
                    .userPassword(passwordEncoder.encode("password1"))
                    .authorities(standardUserRoleSet)
                    .build();

            UserEntity user2 = UserEntity.builder()
                    .userName("user2")
                    .userPassword(passwordEncoder.encode("password2"))
                    .authorities(standardUserRoleSet)
                    .build();

            UserEntity user3 = UserEntity.builder()
                    .userName("user3")
                    .userPassword(passwordEncoder.encode("password3"))
                    .authorities(standardUserRoleSet)
                    .build();

            UserEntity user4 = UserEntity.builder()
                    .userName("user4")
                    .userPassword(passwordEncoder.encode("password4"))
                    .authorities(standardUserRoleSet)
                    .build();
            userRepository.saveAll(Arrays.asList(admin, user1, user2, user3, user4));

            //Tasks.
            TaskEntity task1 = TaskEntity.builder()
                    .taskName("Task 1 for User 2")
                    .taskDeadline(LocalDateTime.now().plusDays(7))
                    .taskDescription("Description for Task 2")
                    .taskStatus(TaskEntity.TaskStatus.YAPILACAK)
                    .taskUserEntityOwner(user2)
                    .build();

            TaskEntity task2 = TaskEntity.builder()
                    .taskName("Task 1 for User 3")
                    .taskDeadline(LocalDateTime.now().plusDays(10))
                    .taskDescription("Description for Task 3")
                    .taskStatus(TaskEntity.TaskStatus.ERTELENDI)
                    .taskUserEntityOwner(user3)
                    .build();

            TaskEntity task3 = TaskEntity.builder()
                    .taskName("Task 1 for User 4")
                    .taskDeadline(LocalDateTime.now().plusDays(3))
                    .taskDescription("Description for Task 1")
                    .taskStatus(TaskEntity.TaskStatus.YAPILACAK)
                    .taskUserEntityOwner(user4)
                    .build();

            TaskEntity task4 = TaskEntity.builder()
                    .taskName("Task 2 for User 4")
                    .taskDeadline(LocalDateTime.now().plusDays(15))
                    .taskDescription("Description for Task 2")
                    .taskStatus(TaskEntity.TaskStatus.YAPILACAK)
                    .taskUserEntityOwner(user4)
                    .build();

            taskRepository.saveAll(Arrays.asList(task1, task2, task3,task4));

            log.info("Sample data has been initialized.");
        }
    }
}
