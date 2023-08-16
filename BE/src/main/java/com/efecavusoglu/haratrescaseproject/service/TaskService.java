package com.efecavusoglu.haratrescaseproject.service;

import com.efecavusoglu.haratrescaseproject.exception.TaskNotFoundException;
import com.efecavusoglu.haratrescaseproject.exception.UnauthorizedOperationException;
import com.efecavusoglu.haratrescaseproject.model.TaskEntity;
import com.efecavusoglu.haratrescaseproject.model.UserEntity;
import com.efecavusoglu.haratrescaseproject.model.dto.TaskCreateRequest;
import com.efecavusoglu.haratrescaseproject.model.dto.TaskResponse;
import com.efecavusoglu.haratrescaseproject.model.dto.TaskUpdateRequest;
import com.efecavusoglu.haratrescaseproject.repository.TaskRepository;
import com.efecavusoglu.haratrescaseproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public List<TaskResponse> getUserEntityTaskList(String jwt){
        return taskEntityListToTaskResponseListMapper(jwtUserEntityExtractor(jwt).getUserTaskEntityList());
    }
    public TaskResponse createNewTaskAsUser(String jwt, TaskCreateRequest taskCreateRequest){
        UserEntity requestMaker = jwtUserEntityExtractor(jwt);
        TaskEntity taskEntity = taskCreateRequestToTaskEntityMapper(taskCreateRequest);
        if(taskEntity.getTaskUserEntityOwner().equals(requestMaker)){
            taskEntity = taskRepository.save(taskEntity);
            return taskEntityToTaskResponseMapper(taskEntity);
        } else{
            throw new UnauthorizedOperationException("Users cannot assign tasks to other users.");
        }
    }
    public TaskResponse createNewTaskAsAdmin(TaskCreateRequest taskCreateRequest){
        //Request to TaskEntity Mapping..
        TaskEntity taskEntity = taskCreateRequestToTaskEntityMapper(taskCreateRequest);
        taskEntity = taskRepository.save(taskEntity);
        return taskEntityToTaskResponseMapper(taskEntity);
    }
    public TaskResponse updateTaskEntityAsUser(String jwt,TaskUpdateRequest taskUpdateRequest){
        UserEntity requestMaker = jwtUserEntityExtractor(jwt);
        TaskEntity taskEntity = taskRepository.findTaskEntityByTaskId(taskUpdateRequest.getTaskId());
        if(taskEntity==null){
            throw new TaskNotFoundException("This task does not exist. Please create as a new one.");
        }
        if(!taskEntity.getTaskUserEntityOwner().equals(requestMaker)){
            throw new UnauthorizedOperationException("Users cannot assign tasks to other users.");
        }
        taskEntity.setTaskStatus(taskUpdateRequest.getTaskStatus());
        taskEntity = taskRepository.save(taskEntity);
        return taskEntityToTaskResponseMapper(taskEntity);
    }

    public TaskResponse updateTaskEntityAsAdmin(TaskUpdateRequest taskUpdateRequest){
        TaskEntity taskEntity = taskRepository.findTaskEntityByTaskId(taskUpdateRequest.getTaskId());
        if(taskEntity==null){
            throw new TaskNotFoundException("This task does not exist. Please create as a new one.");
        }
        taskEntity.setTaskName(taskUpdateRequest.getTaskName());
        taskEntity.setTaskDeadline(taskUpdateRequest.getTaskDeadline());
        taskEntity.setTaskDescription(taskUpdateRequest.getTaskDescription());
        taskEntity.setTaskStatus(taskUpdateRequest.getTaskStatus());
        taskEntity = taskRepository.save(taskEntity);
        return taskEntityToTaskResponseMapper(taskEntity);
    }
    public List<TaskResponse> getAllTasks() {
        return taskEntityListToTaskResponseListMapper(taskRepository.findAll());
    }

    //Methods from Refactoring.
    public List<TaskResponse> taskEntityListToTaskResponseListMapper(List<TaskEntity> inputTaskEntityList){
        List<TaskResponse> outputTaskResponseList = new ArrayList<>();
        for(TaskEntity x: inputTaskEntityList){
            TaskResponse taskResponse = TaskResponse.builder()
                    .taskId(x.getTaskId())
                    .taskName(x.getTaskName())
                    .taskDeadline(x.getTaskDeadline())
                    .taskDescription(x.getTaskDescription())
                    .taskStatus(x.getTaskStatus())
                    .taskOwnerUsername(x.getTaskUserEntityOwner().getUsername())
                    .build();
            outputTaskResponseList.add(taskResponse);
        }
        return outputTaskResponseList;
    }

    public TaskResponse taskEntityToTaskResponseMapper(TaskEntity taskEntity){
        return TaskResponse.builder()
                .taskId(taskEntity.getTaskId())
                .taskName(taskEntity.getTaskName())
                .taskDeadline(taskEntity.getTaskDeadline())
                .taskDescription(taskEntity.getTaskDescription())
                .taskStatus(taskEntity.getTaskStatus())
                .taskOwnerUsername(taskEntity.getTaskUserEntityOwner().getUsername())
                .build();
    }
    public TaskEntity taskCreateRequestToTaskEntityMapper(TaskCreateRequest taskCreateRequest){
        return TaskEntity.builder()
                .taskName(taskCreateRequest.getTaskName())
                .taskDeadline(taskCreateRequest.getTaskDeadline())
                .taskDescription(taskCreateRequest.getTaskDescription())
                .taskStatus(taskCreateRequest.getTaskStatus())
                .taskUserEntityOwner(userRepository.findUserEntityByUserName(taskCreateRequest.getTaskOwnerUsername()))
                .build();
    }
    public UserEntity jwtUserEntityExtractor(String jwt){
        Map<String, Object> claims = tokenService.decodeJwt(jwt).getClaims();
        return userRepository.findUserEntityByUserName((String)claims.get("sub"));
    }
}
