package com.technonet.BackgroundTask;

import com.technonet.Repository.OrderRepo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by vakhtanggelashvili on 3/26/17.
 */
@Component
@Transactional
public class BackgroundTasks {
    /*@Transactional
    @Scheduled(fixedRate = 5000)*/
    public void removeUnpaidOrders(){
        orderRepo.findUnpaidOld(new DateTime().minusMinutes(15).toDate()).forEach(order -> {
            order.getBookedTimes().forEach(bookedTime -> {
                bookedTime.setActive(false);
            });
            order.setActive(false);
            order.setLastModifyDate(new Date());
            orderRepo.save(order);
        });
    }
    @Autowired
    private OrderRepo orderRepo;
}
