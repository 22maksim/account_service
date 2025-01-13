package com.example.account_service.repository.multitasking.request;

import com.example.account_service.model.enums.multitasking.TaskOfType;
import com.example.account_service.model.multitasking.queries.AsyncRequestMultitasking;
import com.example.account_service.model.multitasking.queries.RequestTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestTaskRepository extends JpaRepository<RequestTask, Long> {

    RequestTask findByTaskOfTypeAndRequest(TaskOfType taskOfType, AsyncRequestMultitasking request);
}
