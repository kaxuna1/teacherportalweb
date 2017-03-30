package com.technonet.controlers;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.*;
import com.technonet.model.*;
import com.technonet.staticData.PermisionChecks;
import com.technonet.staticData.Variables;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by kakha on 3/11/2017.
 */
@Controller
public class ScheduleController {


    @RequestMapping("/createscheduleday")
    @ResponseBody
    public JsonMessage createCategory(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                      @RequestParam(value = "category", required = true, defaultValue = "") long category,
                                      @RequestParam(value = "day", required = true, defaultValue = "") int day
    ) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.categoriesManagement(session)) {
            try {
                UserCategoryJoin category1 = userCategoryJoinRepo.findOne(category).get();
                List<Schedule> scheduleCheck = scheduleRepo.findByUserCategoryJoinAndActiveAndDayOfWeek(category1, true, day);
                if (scheduleCheck.size() > 0) {
                    return new JsonMessage(JsonReturnCodes.ERROR);
                }
                Schedule schedule = new Schedule(category1, day);
                scheduleRepo.save(schedule);
                return new JsonMessage(JsonReturnCodes.Ok);
            } catch (Exception e) {
                return new JsonMessage(JsonReturnCodes.ERROR);
            }
        } else {
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }

    @RequestMapping("/getweekdaysforcategorytoadd")
    @ResponseBody
    public List<WeekDay> getWeekDaysForAdding(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                              @RequestParam(value = "user", required = true, defaultValue = "") long user,
                                              @RequestParam(value = "category", required = true, defaultValue = "") long category) {
        HashMap<Integer, WeekDay> weekdays = Variables.getWeekDays();
        Session session = sessionRepository.findOne(sessionId).get();
        User user1 = userDao.findOne(user).get();
        UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(category).get();
        List<Schedule> schedules = scheduleRepo.findByUserCategoryJoinAndActive(userCategoryJoin, true);
        schedules.stream().forEach(schedule -> weekdays.remove(schedule.getDayOfWeek()));

        return new ArrayList<>(weekdays.values());
    }

    @RequestMapping("/getmyweekdaysforcategorytoadd")
    @ResponseBody
    public List<WeekDay> getMyWeekDaysForAdding(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                                @RequestParam(value = "category", required = true, defaultValue = "") long category) {
        HashMap<Integer, WeekDay> weekdays = Variables.getWeekDays();
        Session session = sessionRepository.findOne(sessionId).get();
        User user1 = session.getUser();
        UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(category).get();
        List<Schedule> schedules = scheduleRepo.findByUserCategoryJoinAndActive(userCategoryJoin, true);
        schedules.stream().forEach(schedule -> weekdays.remove(schedule.getDayOfWeek()));

        return new ArrayList<>(weekdays.values());
    }


    @RequestMapping("/getusercategoryscheduledays/{category}")
    @ResponseBody
    public List<Schedule> getUserCategoryScheduleDays(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                                      @PathVariable(value = "category", required = true) long category) {
        return scheduleRepo.findByUserCategoryJoinAndActive(userCategoryJoinRepo.findOne(category).get(), true);
    }


    @RequestMapping("/scheduledtimes/{id}")
    @ResponseBody
    public List<ScheduleTime> getScheduledTimes(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                                @PathVariable(value = "id", required = true) long id) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.isAdmin(session) || PermisionChecks.student(session)) {
            Schedule schedule = scheduleRepo.findOne(id).get();
            return scheduleTimeRepo.findByScheduleAndActiveOrderByStartTimeAsc(schedule, true);
        } else {
            return null;
        }
    }

    @RequestMapping("createscheduletime/{id}")
    @ResponseBody
    public boolean createScheduleTime(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                      @PathVariable(value = "id", required = true) long id, long from, long to) {
        Session session = sessionRepository.findOne(sessionId).get();
        Schedule schedule = scheduleRepo.findOne(id).get();
        if (PermisionChecks.scheduleManagement(session) || schedule.getCategory().getUser().getId() == session.getUser().getId()) {


            Time fromTime = new Time(from);
            Time toTime = new Time(to);
            if (from > to) {
                return false;
            }

            List<ScheduleTime> betweenTimes1 = scheduleTimeRepo.findBetweenSchedule((fromTime), (fromTime), schedule);
            List<ScheduleTime> betweenTimes2 = scheduleTimeRepo.findBetweenSchedule((toTime), (toTime), schedule);

            if (betweenTimes1.size() > 0 || betweenTimes2.size() > 0) {
                return false;
            }
            if (new Duration(from, to).getStandardMinutes() < schedule.getCategory().getDuration()) {
                return false;
            }
            ScheduleTime scheduleTime = new ScheduleTime(fromTime, toTime, schedule);
            scheduleTimeRepo.save(scheduleTime);


            return true;
        } else {
            return false;
        }

    }

    @RequestMapping("/schedulefordays/{id}/{days}")
    @ResponseBody
    public List<FreeInterval> nextWeekScheduleForUser(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                                      @PathVariable(value = "id") long id, @PathVariable(value = "days") int days) {
        List<FreeInterval> list = new ArrayList<>();
        List<FreeInterval> returnList = new ArrayList<>();
        UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(id).get();
        Date date = new Date();
        Calendar client = null;
        CalendarListEntry calendarItem = null;
        if (userCategoryJoin.getUser().getCalendarId() != null) {

            try {


                client = Variables.getCalendarClient(userCategoryJoin.getUser());


                calendarItem = client.calendarList().get(userCategoryJoin.getUser().getCalendarId()).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Interval> intervalsBusy = new ArrayList<>();
        if (client != null && calendarItem != null) {
            FreeBusyRequest freeBusyRequest = new FreeBusyRequest();
            freeBusyRequest.setTimeMin(new com.google.api.client.util.DateTime(new DateTime().toDate(), TimeZone.getTimeZone(calendarItem.getTimeZone())));
            freeBusyRequest.setTimeMax(new com.google.api.client.util.DateTime(new DateTime().plusDays(days).withHourOfDay(0)
                    .withMinuteOfHour(0).withSecondOfMinute(0).toDate(), TimeZone.getTimeZone(calendarItem.getTimeZone())));
            FreeBusyRequestItem freeBusyRequestItem = new FreeBusyRequestItem();
            freeBusyRequestItem.setId(userCategoryJoin.getUser().getCalendarId());
            List<FreeBusyRequestItem> freeBusyRequestItems = new ArrayList<>();
            freeBusyRequestItems.add(freeBusyRequestItem);
            freeBusyRequest.setItems(freeBusyRequestItems);
            try {
                FreeBusyResponse freeBusyResponse = client.freebusy().query(freeBusyRequest).execute();
                List<TimePeriod> busyPeriods = freeBusyResponse.getCalendars().get(calendarItem.getId()).getBusy();
                busyPeriods.forEach(timePeriod -> intervalsBusy.add(new Interval(timePeriod.getStart().getValue(),timePeriod.getEnd().getValue())));
            } catch (IOException e) {
                e.printStackTrace();

            }
        }


        for (int i = 0; i < days; i++) {
            DateTime dateTime = new DateTime().plusDays(i);
            int dayOfWeek = (dateTime.getDayOfWeek() - 1);
            List<Schedule> schedules = scheduleRepo.findByUserCategoryJoinAndActiveAndDayOfWeek(userCategoryJoin, true, dayOfWeek);
            if (schedules.size() == 0) {
                continue;
            }
            Schedule schedule = schedules.get(0);
            List<ScheduleTime> scheduledTimes = schedule.getScheduleTimes();
            for (ScheduleTime scheduleTime :
                    scheduledTimes) {
                FreeInterval freeInterval = new FreeInterval(scheduleTime.startTime(), scheduleTime.endTime(), dateTime.toDate());
                List<BookedTime> bookedInFreeInterval = bookedTimeRepo
                        .findInsideInterval(freeInterval.getStarting_time(), freeInterval.getEnding_time());
                if (bookedInFreeInterval.size() > 0) {
                    final Date[] lastFreeTime = {freeInterval.getStarting_time()};

                    bookedInFreeInterval.forEach(bookedTime -> {

                        FreeInterval freeIntervalInsideInterval = new FreeInterval(new Time(lastFreeTime[0].getTime()),
                                new Time(bookedTime.getStartDate().getTime()),
                                dateTime.toDate());
                        lastFreeTime[0] = bookedTime.getEndDate();
                        list.add(freeIntervalInsideInterval);
                    });
                    if (lastFreeTime[0].getTime() < freeInterval.getEnding_time().getTime()) {

                        FreeInterval freeIntervalLast = new FreeInterval(new Time(lastFreeTime[0].getTime()),
                                new Time(freeInterval.getEnding_time().getTime()), dateTime.toDate());

                        list.add(freeIntervalLast);


                    }
                } else {
                    list.add(freeInterval);
                }
            }


        }

        Calendar finalClient = client;
        CalendarListEntry finalCalendarItem = calendarItem;
        list.forEach(freeInterval -> {
            while (freeInterval.getDuration().getStandardMinutes() >= userCategoryJoin.getDuration()) {
                FreeInterval freeIntervalToAdd = new FreeInterval();
                freeIntervalToAdd.setStart(freeInterval.getStarting_time());
                Date intervalEndDate = new DateTime(freeInterval.getStarting_time()).plusMinutes(userCategoryJoin.getDuration()).toDate();
                freeIntervalToAdd.setEnd(intervalEndDate);
                if(intervalsBusy.stream().noneMatch(interval ->
                        interval.overlap(new Interval(freeIntervalToAdd.getStarting_time().getTime(),freeIntervalToAdd.getEnding_time().getTime()))!=null)){
                    returnList.add(freeIntervalToAdd);
                }
                freeInterval.setStart(intervalEndDate);
            }

        });


        return returnList;
    }

    @RequestMapping("/getscheduledtimeforlesson/{id}/{cat}/{days}")
    @ResponseBody
    public List<BookedTime> getScheduledLessons(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                                @PathVariable(value = "id") long id,
                                                @PathVariable(value = "cat") long cat,
                                                @PathVariable(value = "days") int days) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.isAdmin(session)) {
            return bookedTimeRepo.findInsideIntervalWithCat(new DateTime().minusDays(days).toDate(), new DateTime().plusDays(days).toDate(), userCategoryJoinRepo.findOne(cat).get());
        } else {
            return null;
        }
    }

    @RequestMapping("/getscheduledtimeforuser/{id}/{days}")
    @ResponseBody
    public List<BookedTime> getScheduledLessons(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                                @PathVariable(value = "id") long id,
                                                @PathVariable(value = "days") int days) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.isAdmin(session)) {
            return bookedTimeRepo.findInsideIntervalWithUser(new DateTime().minusDays(days).toDate(), new DateTime().plusDays(days).toDate(), userDao.findOne(id).get());
        } else {
            return null;
        }
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
}
