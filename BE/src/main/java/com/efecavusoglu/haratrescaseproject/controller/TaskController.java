package com.efecavusoglu.haratrescaseproject.controller;

import com.efecavusoglu.haratrescaseproject.model.dto.TaskCreateRequest;
import com.efecavusoglu.haratrescaseproject.model.dto.TaskResponse;
import com.efecavusoglu.haratrescaseproject.model.dto.TaskUpdateRequest;
import com.efecavusoglu.haratrescaseproject.model.dto.UserRequest;
import com.efecavusoglu.haratrescaseproject.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;
    @GetMapping("/user/mytasks")
    public List<TaskResponse> getUserEntityTaskList(@RequestHeader("Authorization") String bearerToken){
            return taskService.getUserEntityTaskList(getJwtFromRequestHeader(bearerToken));
    }
    @PostMapping("/user/createNewTask")
    public TaskResponse createNewTaskAsUser(@RequestHeader("Authorization") String bearerToken, @RequestBody TaskCreateRequest taskCreateRequest){
            return taskService.createNewTaskAsUser(getJwtFromRequestHeader(bearerToken), taskCreateRequest);
    }
    @PostMapping("/admin/createNewTask")
    public TaskResponse createNewTaskAsAdmin(@RequestBody TaskCreateRequest taskCreateRequest){
        return taskService.createNewTaskAsAdmin(taskCreateRequest);
    }
    @PostMapping("/user/updatetask")
    public TaskResponse updateTaskEntityAsUser(@RequestHeader("Authorization") String bearerToken, @RequestBody TaskUpdateRequest taskUpdateRequest){
            return taskService.updateTaskEntityAsUser(getJwtFromRequestHeader(bearerToken),taskUpdateRequest);
    }
    @PostMapping("/admin/updatetask")
    public TaskResponse updateTaskEntityAsAdmin(@RequestBody TaskUpdateRequest taskUpdateRequest){
        return taskService.updateTaskEntityAsAdmin(taskUpdateRequest);
    }
    @GetMapping("/admin/getAllTasks")
    public List<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    public String getJwtFromRequestHeader(String bearerToken){
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication Failed.");
    }
}
