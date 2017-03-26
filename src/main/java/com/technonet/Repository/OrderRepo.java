package com.technonet.Repository;

import com.technonet.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 3/15/17.
 */
@Transactional
public interface OrderRepo extends JpaRepository<Order,Long> {
    Order findByUuidAndActive(String uuid, boolean active);

    Page<Order> findByActiveOrderByCreateDateDesc(boolean active, Pageable pageable);

    @Query("select o from  Order o where o.active=true and o.createDate<:date")
    List<Order> findUnpaidOld(@Param("date") Date date);

}
