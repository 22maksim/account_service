package com.example.account_service.repository.account_number_sequence;

import com.example.account_service.model.AccountNumbersSequence;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountNumbersSequenceRepository extends JpaRepository<AccountNumbersSequence, String> {

    @Modifying
    @Query(value = """
        UPDATE account_numbers_sequence SET counter = counter + :box WHERE type = :type 
        RETURNING  counter;
""", nativeQuery = true)
    int updateCounter(@Param("type") String type, @Param("box") int box);

    @Query(value = """
         SELECT ans.counter FROM account_numbers_sequence ans WHERE ans.type = :type
""", nativeQuery = true)
    int getCounter(@Param("type") String type);
}
