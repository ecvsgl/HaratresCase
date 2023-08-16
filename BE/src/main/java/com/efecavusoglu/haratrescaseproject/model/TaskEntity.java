package com.efecavusoglu.haratrescaseproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {
    public enum TaskStatus{TAMAMLANDI, IPTAL_EDILDI,ERTELENDI, YAPILACAK}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;
    private String taskName;
    private LocalDateTime taskDeadline;
    private String taskDescription;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @ManyToOne
    private UserEntity taskUserEntityOwner;
}
