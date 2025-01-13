package com.example.account_service.model.multitasking.queries;

import com.example.account_service.model.enums.multitasking.TaskOfType;
import com.example.account_service.model.enums.multitasking.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@Table(name = "request_task")
@NoArgsConstructor
@AllArgsConstructor
public class RequestTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_of_type", nullable = false)
    private TaskOfType taskOfType;

    @ManyToOne
    @JoinColumn(name = "async_request_multitasking_id", referencedColumnName = "id", nullable = false)
    private AsyncRequestMultitasking request;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Version
    private int version;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
