package com.example.account_service.service.paralel.task.handler;

import com.example.account_service.exeption.RequestTaskRepoException;
import com.example.account_service.model.enums.multitasking.TaskOfType;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

public interface RequestTaskHandler {
    @Async("workerAsync")
    void execute(AsyncRequestMultitasking request) throws IOException;

    long getHandlerId();

    TaskOfType getTaskOfType();
}

