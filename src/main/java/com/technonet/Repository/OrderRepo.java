package com.technonet.Repository;

import com.technonet.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vakhtanggelashvili on 3/15/17.
 */
@Transactional
public interface OrderRepo extends JpaRepository<Order,Long> {
}