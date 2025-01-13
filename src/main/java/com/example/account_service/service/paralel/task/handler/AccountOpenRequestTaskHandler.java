package com.example.account_service.service.paralel.task.handler;

import com.example.account_service.exeption.OwnerException;
import com.example.account_service.exeption.RequestTaskRepoException;
import com.example.account_service.model.dto.account.AccountRequestDto;
import com.example.account_service.model.dto.account.AccountResponseDto;
import com.example.account_service.model.enums.multitasking.StatusAsyncRequest;
import com.example.account_service.model.enums.multitasking.TaskOfType;
import com.example.account_service.model.enums.multitasking.TaskStatus;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import com.example.account_service.model.multitasking.queries.RequestTask;
import com.example.account_service.repository.multitasking.request.RequestTaskRepository;
import com.example.account_service.service.account.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountOpenRequestTaskHandler implements RequestTaskHandler{
    private final RequestTaskRepository requestTaskRepository;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @Retryable(retryFor = {RequestTaskRepoException.class}, maxAttempts = 30,
            backoff = @Backoff(delay = 100), noRetryFor = {OwnerException.class})
    @Transactional
    @Override
    public void execute(AsyncRequestMultitasking request) throws RequestTaskRepoException {
        RequestTask task = requestTaskRepository.findByTaskOfTypeAndRequest(getTaskOfType(), request);
        if (task == null) {
            throw new  RequestTaskRepoException("Task not found");
        } else if (task.getTaskStatus().equals(TaskStatus.FAILED)) {
            request.setStatusAsyncRequest(StatusAsyncRequest.FAILED);
            log.error("Task is failed");
            RequestTask taskFailed = RequestTask.builder()
                    .request(request)
                    .taskStatus(TaskStatus.FAILED)
                    .taskOfType(getTaskOfType())
                    .build();
            requestTaskRepository.save(taskFailed);
            throw new OwnerException("Task is failed. Id: " + task.getId());
        }
        AccountRequestDto requestDto = objectMapper.convertValue(request.getContext().getAccountRequestDto(), AccountRequestDto.class);
        AccountResponseDto responseDto = accountService.open(requestDto);

        request.getContext().setAccountId(responseDto.getId());
        request.setStatusAsyncRequest(StatusAsyncRequest.PROCESSING);

        RequestTask requestTaskCompleted = RequestTask.builder()
                .taskOfType(getTaskOfType())
                .request(request)
                .taskStatus(TaskStatus.COMPLETED)
                .build();
        requestTaskCompleted = requestTaskRepository.save(requestTaskCompleted);
        log.info("Account created: {}. MultiRequest Id: {}. RequestTsk id: {}", responseDto.getId(), request.getId(), requestTaskCompleted.getId());
    }




    @Override
    public long getHandlerId() {
        return 2;
    }

    @Override
    public TaskOfType getTaskOfType() {
        return TaskOfType.ACCOUNT_OPEN;
    }
}
