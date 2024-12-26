package com.example.account_service.repository.free_account_numbers;

import com.example.account_service.model.FreeAccountNumber;
import com.example.account_service.model.enums.TypeNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface FreeAccountNumbersRepository extends JpaRepository<FreeAccountNumber, Long> {
    @Query(value = """
                       DELETE FROM free_account_numbers WHERE account_number  = (
                            SELECT account_number FROM free_account_numbers fan 
                            WHERE fan.type = :type 
                            ORDER BY account_number ASC LIMIT 1 
                            FOR UPDATE SKIP LOCKED
                        ) RETURNING *
            """, nativeQuery = true)
    FreeAccountNumber getFreeAccountNumberAndDelete(@Param("type") String type);

    boolean existsFreeAccountNumberByType(TypeNumber type);
}
