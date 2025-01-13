package com.example.account_service.repository.multitasking.request;

import com.example.account_service.model.enums.multitasking.StatusAsyncRequest;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface AsyncRequestMultitaskingRepository extends JpaRepository<AsyncRequestMultitasking, Long> {
    List<AsyncRequestMultitasking> findAllByScheduledAtAfter(Instant scheduledAtAfter);

    List<AsyncRequestMultitasking> findAllByScheduledAtBefore(Instant scheduledAtBefore);

    List<AsyncRequestMultitasking> findAllByScheduledAtBeforeAndStatusAsyncRequest(Instant scheduledAtBefore, StatusAsyncRequest statusAsyncRequest);
}
