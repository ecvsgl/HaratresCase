package com.efecavusoglu.haratrescaseproject.model.dto;

import com.efecavusoglu.haratrescaseproject.model.Role;
import com.efecavusoglu.haratrescaseproject.model.TaskEntity;
import com.efecavusoglu.haratrescaseproject.model.UserEntity;
import com.efecavusoglu.haratrescaseproject.model.dto.TaskResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
public class UserResponse {
    private String userName;
    private Set<Role> authorities;
    private List<TaskResponse> userTaskResponseList;
}
