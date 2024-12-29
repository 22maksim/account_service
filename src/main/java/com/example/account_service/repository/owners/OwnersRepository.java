package com.example.account_service.repository.owners;

import com.example.account_service.model.Owner;
import com.example.account_service.model.enums.TypeOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnersRepository extends JpaRepository<Owner, Long> {
    Owner findByOwnerId(Long ownerId);

    boolean existsOwnerById(Long id);

    boolean existsOwnerByOwnerId(Long ownerId);
}
