package com.example.account_service.repository.owners;

import com.example.account_service.model.Owner;
import com.example.account_service.model.enums.TypeOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface OwnersRepository extends JpaRepository<Owner, Long>, QueryByExampleExecutor<Owner> {
    Owner findByOwnerId(Long ownerId);

    boolean existsOwnerById(Long id);

    boolean existsOwnerByOwnerId(Long ownerId);
}
