package com.example.account_service.service.paralel.task;

import com.example.account_service.model.enums.multitasking.StatusAsyncRequest;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import com.example.account_service.repository.multitasking.request.AsyncRequestMultitaskingRepository;
import com.example.account_service.service.paralel.task.handler.RequestTaskHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestExecutorServiceImpl implements RequestExecutorService {
    private final AsyncRequestMultitaskingRepository asyncRequestMultitaskingRepository;
    private final List<RequestTaskHandler> handlers;

    @Override
    public void startingRequests() {
        List<AsyncRequestMultitasking> asyncRequestsWaiting =
                asyncRequestMultitaskingRepository
                        .findAllByScheduledAtBeforeAndStatusAsyncRequest(Instant.now(), StatusAsyncRequest.WAITING);

        asyncRequestsWaiting.stream()
                .filter(Objects::nonNull)
                .filter(request -> request.getScheduledAt().isBefore(Instant.now()))
                .forEach(request -> {
                    handlers.stream()
                            .sorted(Comparator.comparingLong(RequestTaskHandler::getHandlerId))
                            .forEach(handler -> {
                                try {
                                    handler.execute(request);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                });
    }
}
