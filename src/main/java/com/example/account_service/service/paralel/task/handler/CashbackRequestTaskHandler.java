package com.example.account_service.service.paralel.task.handler;

import com.example.account_service.exeption.AsyncRequestCompletedException;
import com.example.account_service.exeption.RequestTaskRepoException;
import com.example.account_service.model.Account;
import com.example.account_service.model.cashback.tariff.TariffCashback;
import com.example.account_service.model.enums.multitasking.StatusAsyncRequest;
import com.example.account_service.model.enums.multitasking.TaskOfType;
import com.example.account_service.model.enums.multitasking.TaskStatus;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import com.example.account_service.model.multitasking.queries.ContextMultitasking;
import com.example.account_service.model.multitasking.queries.RequestTask;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.multitasking.request.AsyncRequestMultitaskingRepository;
import com.example.account_service.repository.multitasking.request.RequestTaskRepository;
import com.example.account_service.service.cashback.TariffCashbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CashbackRequestTaskHandler implements RequestTaskHandler {
    private final TariffCashbackService tariffCashbackService;
    private final AccountRepository accountRepository;
    private final RequestTaskRepository requestTaskRepository;
    private final AsyncRequestMultitaskingRepository asyncRequestMultitaskingRepository;

    @Retryable(retryFor = {RequestTaskRepoException.class}, maxAttempts = 20, backoff = @Backoff(delay = 250),
        noRetryFor = {AsyncRequestCompletedException.class})
    @Transactional
    @Override
    public void execute(AsyncRequestMultitasking request) throws RequestTaskRepoException, AsyncRequestCompletedException {
        AsyncRequestMultitasking asyncRequest = asyncRequestMultitaskingRepository.findById(request.getId())
                .orElseThrow(() -> new RequestTaskRepoException("No Async Request Found"));
        validateAsyncRequest(asyncRequest);
        if (asyncRequest.getStatusAsyncRequest().equals(StatusAsyncRequest.FAILED)) {
            RequestTask requestTask = RequestTask.builder()
                    .taskStatus(TaskStatus.FAILED)
                    .taskOfType(getTaskOfType())
                    .request(request)
                    .build();
            requestTask = requestTaskRepository.save(requestTask);
            request.getRequestTasks().add(requestTask);
            log.error("Task Failed: {}", requestTask.getId());
        }
        TariffCashback tariffCashback =
                tariffCashbackService.getTariffCashbackEntity(request.getContext().getTariffCashbackId());
        Account account = accountRepository.findAccountById(request.getContext().getAccountId());
        account.setTariffCashback(tariffCashback);
        accountRepository.save(account);
        RequestTask taskCompleted = RequestTask.builder()
                .request(request)
                .taskStatus(TaskStatus.COMPLETED)
                .taskOfType(getTaskOfType())
                .build();
        taskCompleted = requestTaskRepository.save(taskCompleted);
        request.getRequestTasks().add(taskCompleted);
        request.setStatusAsyncRequest(StatusAsyncRequest.PROCESSING);
        log.info("Account id: {} ; add tariff cashback id {}", account.getId(), request.getContext().getTariffCashbackId());
    }

    @Override
    public long getHandlerId() {
        return 3;
    }

    @Override
    public TaskOfType getTaskOfType() {
        return TaskOfType.CASHBACK;
    }

    private void validateAsyncRequest(AsyncRequestMultitasking asyncRequest) throws RequestTaskRepoException, AsyncRequestCompletedException {
        if (asyncRequest.getStatusAsyncRequest().equals(StatusAsyncRequest.PROCESSING)) {
            throw new RequestTaskRepoException("Async Request Failed");
        } else if (asyncRequest.getStatusAsyncRequest().equals(StatusAsyncRequest.COMPLETED)) {
            throw  new AsyncRequestCompletedException("Request is completed");
        }
    }
}
