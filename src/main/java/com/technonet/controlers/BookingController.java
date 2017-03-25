package com.technonet.controlers;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.*;
import com.technonet.model.*;
import com.technonet.staticData.PermisionChecks;
import com.technonet.staticData.Variables;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by kakha on 3/16/2017.
 */
@Controller
public class BookingController {

    @RequestMapping("/bookforuser/{userId}/{usercat}")
    @ResponseBody
    public JsonMessage book(@CookieValue("projectSessionId") long sessionId,
                            @PathVariable("userId") long userId,
                            @PathVariable("usercat") long usercat,
                            @RequestParam(value = "times") ArrayList<Long> times) {

        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.isAdmin(session)) {
            List<BookedTime> bookedTimes = new ArrayList<>();
            User user = userDao.findOne(userId);
            UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(usercat);
            Order order = new Order(user);
            order.setPrice(userCategoryJoin.getPrice() * times.size());
            orderRepo.save(order);
            for (Long time : times) {
                BookedTime bookedTime = new BookedTime(new DateTime(time).withSecondOfMinute(0).withMillisOfSecond(0).toDate(),
                        new DateTime(time).plusMinutes(userCategoryJoin.getDuration()).withSecondOfMinute(0).withMillisOfSecond(0).toDate(), user, userCategoryJoin, order);
                bookedTimeRepo.save(bookedTime);
                bookedTimes.add(bookedTime);
            }
            Payment payment = new Payment(order.getOrderPrice(), order);
            paymentsRepo.save(payment);

            if (userCategoryJoin.getUser().getCalendarId() != null)
                try {

                    Calendar client = Variables.getCalendarClient(userCategoryJoin.getUser());
                    CalendarListEntry calendar = client.calendarList().get(userCategoryJoin.getUser().getCalendarId()).execute();

                    for (BookedTime bookedTime : bookedTimes) {
                        Event event = new Event();
                        event.setSummary(bookedTime.getCategoryName() + " " + bookedTime.getStudent().getNameSurname());
                        Date startDate = bookedTime.getStartDate();
                        Date endDate = bookedTime.getEndDate();
                        com.google.api.client.util.DateTime start = new com.google.api.client.util.DateTime(startDate, TimeZone.getTimeZone(calendar.getTimeZone()));
                        event.setStart(new EventDateTime().setDateTime(start));
                        com.google.api.client.util.DateTime end = new com.google.api.client.util.DateTime(endDate, TimeZone.getTimeZone(calendar.getTimeZone()));
                        event.setEnd(new EventDateTime().setDateTime(end));
                        Event result = client.events().insert(userCategoryJoin.getUser().getCalendarId(),
                                event).execute();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            return new JsonMessage(JsonReturnCodes.Ok.getCODE(),order.getUuid());
        }
        return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);

    }

    @RequestMapping("/bookforuser/{usercat}")
    @ResponseBody
    public JsonMessage book(@CookieValue("projectSessionId") long sessionId,
                            @PathVariable("usercat") long usercat,
                            @RequestParam(value = "times") ArrayList<Long> times) {

        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.student(session)) {
            User user = session.getUser();
            UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(usercat);
            Order order = new Order(user);
            order.setPrice(userCategoryJoin.getPrice() * times.size());
            orderRepo.save(order);
            for (Long time : times) {
                BookedTime bookedTime = new BookedTime(new Date(time), new DateTime(time).plusMinutes(userCategoryJoin.getDuration()).toDate(), user, userCategoryJoin, order);
                bookedTimeRepo.save(bookedTime);
            }
            Payment payment = new Payment(order.getOrderPrice(), order);
            paymentsRepo.save(payment);
            return new JsonMessage(JsonReturnCodes.Ok.getCODE(), payment.getUuid());
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
