package com.example.account_service.model.multitasking.queries;

import com.example.account_service.converter.ContextMultitaskingJsonConverter;
import com.example.account_service.model.enums.multitasking.StatusAsyncRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "async_request_multitasking")
public class AsyncRequestMultitasking {
    @Id
    private Long id;

    @Column(name = "context", columnDefinition = "jsonb")
    @Convert(converter = ContextMultitaskingJsonConverter.class)
    private ContextMultitasking context;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_async_request")
    private StatusAsyncRequest statusAsyncRequest;

    @Column(name = "scheduled_at", nullable = false, updatable = false)
    private Instant scheduledAt;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<RequestTask> requestTasks;

    @Version
    private int version;

    // -------services-------
    @PrePersist
    protected void onCreate() {
        scheduledAt = Instant.now().plus(3, ChronoUnit.MINUTES);
    }
}