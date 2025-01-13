package com.example.account_service.service.paralel.task.handler;

import com.example.account_service.config.kafka.topic.KafkaTopics;
import com.example.account_service.exeption.AsyncRequestCompletedException;
import com.example.account_service.exeption.RequestTaskRepoException;
import com.example.account_service.exeption.UncorrectedValueRequest;
import com.example.account_service.model.dto.MyMessageDto;
import com.example.account_service.model.dto.account.AccountResponseDto;
import com.example.account_service.model.enums.multitasking.StatusAsyncRequest;
import com.example.account_service.model.enums.multitasking.TaskOfType;
import com.example.account_service.model.enums.multitasking.TaskStatus;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import com.example.account_service.model.multitasking.queries.RequestTask;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.multitasking.request.AsyncRequestMultitaskingRepository;
import com.example.account_service.service.kafka.publisher.KafkaPublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRequestTaskHandler implements RequestTaskHandler {
    private final AsyncRequestMultitaskingRepository asyncRequestMultitaskingRepository;
    private final KafkaPublisherService<AccountResponseDto> kafkaPublisherService;
    private final AccountRepository accountRepository;
    private final KafkaTopics kafkaTopics;
    private final ObjectMapper objectMapper;

    @Retryable(retryFor = {IOException.class, RequestTaskRepoException.class},
            noRetryFor = {AsyncRequestCompletedException.class}, maxAttempts = 10, backoff = @Backoff(delay = 500))
    @Transactional
    @Override
    public void execute(AsyncRequestMultitasking request) throws RequestTaskRepoException, AsyncRequestCompletedException {
        AsyncRequestMultitasking requestConsistency = asyncRequestMultitaskingRepository.findById(request.getId())
                .orElseThrow(() -> new UncorrectedValueRequest("AsyncRequestMultitasking not found"));
        validatedRequest(requestConsistency);

        AccountResponseDto responseDto =
                objectMapper.convertValue(request.getContext().getAccountRequestDto(), AccountResponseDto.class);

        kafkaPublisherService.sendResponseMessage
                (MyMessageDto.<AccountResponseDto>builder().myEntity(responseDto).build(),
                        kafkaTopics.responseAuthorisationTopic());
    }

    @Override
    public long getHandlerId() {
        return 4;
    }

    @Override
    public TaskOfType getTaskOfType() {
        return TaskOfType.NOTIFICATION;
    }

    private void validatedRequest(AsyncRequestMultitasking request) throws RequestTaskRepoException, AsyncRequestCompletedException {
        if (request.getRequestTasks().get(3).getTaskStatus().equals(TaskStatus.PROCESSING)) {
            throw new RequestTaskRepoException("RequestTasks - 3 multitasking not complete");
        } else if (request.getStatusAsyncRequest().equals(StatusAsyncRequest.COMPLETED)) {
            log.error("Status Async Multi Request is COMPLETED");
            throw new AsyncRequestCompletedException("Status Async Multi Request is COMPLETED");
        } else if (request.getStatusAsyncRequest().equals(StatusAsyncRequest.FAILED)) {
            rollback(request);
            log.error("Status Async Multi Request: {},  is FAILED", request.getId());
            throw new AsyncRequestCompletedException("Status Async Multi Request failed");
        }
    }

    private void rollback(AsyncRequestMultitasking request) {
        if (request.getContext().getAccountId() == null || request.getContext().getAccountId().isBlank()) {
            log.error("Account Id is blank");
        }
        accountRepository.deleteById(request.getContext().getAccountId());
        List<RequestTask> tasks = request.getRequestTasks();
        tasks.forEach(task -> {
            task.setTaskStatus(TaskStatus.FAILED);
        });
    }
}
