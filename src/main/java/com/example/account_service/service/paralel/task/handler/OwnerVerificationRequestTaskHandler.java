package com.example.account_service.service.paralel.task.handler;

import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.model.Account;
import com.example.account_service.model.Owner;
import com.example.account_service.model.dto.account.AccountRequestDto;
import com.example.account_service.model.enums.multitasking.StatusAsyncRequest;
import com.example.account_service.model.enums.multitasking.TaskOfType;
import com.example.account_service.model.enums.multitasking.TaskStatus;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import com.example.account_service.model.multitasking.queries.RequestTask;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.multitasking.request.RequestTaskRepository;
import com.example.account_service.repository.owners.OwnersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class OwnerVerificationRequestTaskHandler implements RequestTaskHandler{
    private final AccountRepository accountRepository;
    private final OwnersRepository ownersRepository;
    private final RequestTaskRepository requestTaskRepository;
    private final ObjectMapper objectMapper;

    @Value("${size.max_account_from_owner}")
    private int maxAccountFromOwner;

    @Override
    public void execute(AsyncRequestMultitasking request) {
        AccountRequestDto accountDto =
                objectMapper.convertValue(request.getContext().getAccountRequestDto(), AccountRequestDto.class);
        Owner owner = ownersRepository.findById(accountDto.getOwnerId())
                .orElseThrow(() -> new DataAccountException("Owner not found/ Id: " + accountDto.getOwnerId()));
        List<Account> accounts = accountRepository.findAllByOwner(owner);
        if (accounts.size() >= maxAccountFromOwner) {
            request.setStatusAsyncRequest(StatusAsyncRequest.FAILED);
            RequestTask task = RequestTask.builder()
                    .taskOfType(getTaskOfType())
                    .taskStatus(TaskStatus.FAILED)
                    .request(request)
                    .build();
            requestTaskRepository.save(task);
            log.error("Max account from the owner is {}", maxAccountFromOwner);
            throw new DataAccountException("Max account from the owner is: " + maxAccountFromOwner);
        }
        request.setStatusAsyncRequest(StatusAsyncRequest.PROCESSING);
        RequestTask task = RequestTask.builder()
                .taskOfType(getTaskOfType())
                .taskStatus(TaskStatus.COMPLETED)
                .request(request)
                .build();
        task = requestTaskRepository.save(task);
        log.info("Task completed: {}", task.getId());
    }




    @Override
    public long getHandlerId() {
        return 1;
    }

    @Override
    public TaskOfType getTaskOfType() {
        return TaskOfType.OWNER_VERIFICATION;
    }
}
