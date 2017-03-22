package com.technonet.controlers;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
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
import java.util.List;
import java.util.TimeZone;

/**
 * Created by kakha on 3/16/2017.
 */
@Controller
public class BookingController {
    private HttpTransport httpTransport;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private com.google.api.services.calendar.Calendar client;

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
                BookedTime bookedTime = new BookedTime(new Date(time), new DateTime(time).plusMinutes(userCategoryJoin.getDuration()).toDate(), user, userCategoryJoin, order);
                bookedTimeRepo.save(bookedTime);
                bookedTimes.add(bookedTime);
            }
            Payment payment = new Payment(order.getOrderPrice(), order);
            paymentsRepo.save(payment);

            if(userCategoryJoin.getUser().getCalendarId()!=null)
            try {
                httpTransport = GoogleNetHttpTransport.newTrustedTransport();

                // initialize the data store factory

                GoogleRefreshTokenRequest g = new GoogleRefreshTokenRequest(httpTransport,
                        JSON_FACTORY,
                        session.getUser().getCalendarRefreshToken(),
                        "55995473742-00obqav5bir1au4qdn4l1jgdvf7kbmv2.apps.googleusercontent.com",
                        "qUPLRbRgZjm-wMJ_VBDWrEPC");
                TokenResponse tokenResponse = g.execute();
                // initialize the transport


                GoogleCredential credential = new GoogleCredential().setAccessToken(tokenResponse.getAccessToken());
                String ref = credential.getRefreshToken();

                // set up global Calendar instance
                client = new com.google.api.services.calendar.Calendar.Builder(
                        httpTransport, JSON_FACTORY, credential).setApplicationName("ALLWITZ").build();
                for (BookedTime bookedTime : bookedTimes) {
                    Event event = new Event();
                    event.setSummary(bookedTime.getCategoryName()+" "+bookedTime.getStudent().getNameSurname());
                    Date startDate = bookedTime.getStartDate();
                    Date endDate = bookedTime.getEndDate();
                    com.google.api.client.util.DateTime start = new com.google.api.client.util.DateTime(startDate);
                    event.setStart(new EventDateTime().setDateTime(start));
                    com.google.api.client.util.DateTime end = new com.google.api.client.util.DateTime(endDate);
                    event.setEnd(new EventDateTime().setDateTime(end));
                    Event result = client.events().insert(userCategoryJoin.getUser().getCalendarId(),
                            event).execute();
                }


            } catch (Exception e) {

            }


            return new JsonMessage(JsonReturnCodes.Ok);
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
