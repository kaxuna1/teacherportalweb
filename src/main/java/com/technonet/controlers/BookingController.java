package com.technonet.controlers;

import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.*;
import com.technonet.model.*;
import com.technonet.staticData.PermisionChecks;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kakha on 3/16/2017.
 */
@Controller
public class BookingController {
    @RequestMapping("/bookforuser/{userId}/{usercat}")
    @ResponseBody
    public JsonMessage removeUserPermissions(@CookieValue("projectSessionId") long sessionId,
                                             @PathVariable("userId") long userId,
                                             @PathVariable("usercat") long usercat,
                                             @RequestParam(value = "times") ArrayList<Long> times) {

        Session session = sessionRepository.findOne(sessionId);
        if(PermisionChecks.isAdmin(session)){
            User user = userDao.findOne(userId);
            UserCategoryJoin userCategoryJoin= userCategoryJoinRepo.findOne(usercat);
            Order order= new Order(user);
            order.setPrice(userCategoryJoin.getPrice()*times.size());
            orderRepo.save(order);
            for (Long time : times) {
                BookedTime bookedTime= new BookedTime(new Date(time),new DateTime(time).plusMinutes(userCategoryJoin.getDuration()).toDate(),user,userCategoryJoin,order);
                bookedTimeRepo.save(bookedTime);
            }
            Payment payment = new Payment(order.getOrderPrice(),order);
            paymentsRepo.save(payment);
            return new JsonMessage(JsonReturnCodes.Ok);
        }
        return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);

    }


    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private UserRepository userDao;
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private CountryRepo countryRepo;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ScheduleRepo scheduleRepo;
    @Autowired
    private ScheduleTimeRepo scheduleTimeRepo;
    @Autowired
    private UserCategoryJoinRepo userCategoryJoinRepo;
    @Autowired
    private BookedTimeRepo bookedTimeRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private PaymentsRepo paymentsRepo;
}
