package com.example.account_service.repository.account;

import com.example.account_service.model.Account;
import com.example.account_service.model.enums.TypeNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsAccountById(String id);

    Account findAccountById(String id);

    Page<Account> findAllByType(TypeNumber type, Pageable pageable);
}
