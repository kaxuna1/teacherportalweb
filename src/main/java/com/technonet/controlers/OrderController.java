package com.technonet.controlers;

import com.technonet.Repository.*;
import com.technonet.model.BookedTime;
import com.technonet.model.Order;
import com.technonet.model.Session;
import com.technonet.staticData.PermisionChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vakhtanggelashvili on 3/25/17.
 */
@Controller
public class OrderController {
    @RequestMapping("/order/{uuid}")
    @ResponseBody
    public Order getOrderById(@CookieValue("projectSessionId") long sessionId,
                              @PathVariable("uuid") String uuid) {
        Session session = sessionRepository.findOne(sessionId).get();
        Order order = orderRepo.findByUuidAndActive(uuid, true);
        if (PermisionChecks.isAdmin(session) || PermisionChecks.ownOrder(session, order)) {
            return order;
        } else {
            return null;
        }
    }

    @RequestMapping("/order/{uuid}/times")
    @ResponseBody
    public List<BookedTime> getOrderTimesById(@CookieValue("projectSessionId") long sessionId,
                                              @PathVariable("uuid") String uuid) {
        Session session = sessionRepository.findOne(sessionId).get();
        Order order = orderRepo.findByUuidAndActive(uuid, true);
        if (order == null)
            return null;
        if (PermisionChecks.isAdmin(session) || PermisionChecks.ownOrder(session, order)) {
            return order.getBookedTimes();
        } else {
            return null;
        }
    }

    @RequestMapping("/orders/{page}")
    @ResponseBody
    public Page<Order> getOrders(@CookieValue("projectSessionId") long sessionId,
                                 @PathVariable("page") int page) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.orderManagement(session))
            return orderRepo.findByActiveOrderByCreateDateDesc(true, constructPageSpecification(page));
        else
            return null;
    }


    private Pageable constructPageSpecification(int pageIndex) {
        return new PageRequest(pageIndex, 10);
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
