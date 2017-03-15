package com.technonet.Repository;

import com.technonet.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vakhtanggelashvili on 3/15/17.
 */
@Transactional
public interface PaymentsRepo extends JpaRepository<Payment,Long> {
    Payment findByUuid(String uuid);
}
