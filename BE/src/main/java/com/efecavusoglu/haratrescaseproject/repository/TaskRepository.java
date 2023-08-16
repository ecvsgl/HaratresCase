package com.efecavusoglu.haratrescaseproject.repository;

import com.efecavusoglu.haratrescaseproject.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Integer> {
    TaskEntity findTaskEntityByTaskId(int taskId);
}
