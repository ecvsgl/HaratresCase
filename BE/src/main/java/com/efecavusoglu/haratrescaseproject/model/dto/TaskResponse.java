package com.efecavusoglu.haratrescaseproject.model.dto;

import com.efecavusoglu.haratrescaseproject.model.TaskEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
public class TaskResponse {
    private int taskId;
    private String taskName;
    private LocalDateTime taskDeadline;
    private String taskDescription;
    private TaskEntity.TaskStatus taskStatus;
    private String taskOwnerUsername;
}
