package com.technonet.controlers;

import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.*;
import com.technonet.model.*;
import com.technonet.staticData.PermisionChecks;
import com.technonet.staticData.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by kakha on 3/11/2017.
 */
@Controller
public class ScheduleController {
    @RequestMapping("/createscheduleday")
    @ResponseBody
    public JsonMessage createCategory(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                      @RequestParam(value = "user", required = true, defaultValue = "") long user,
                                      @RequestParam(value = "category", required = true, defaultValue = "") long category,
                                      @RequestParam(value = "day", required = true, defaultValue = "") int day
                                      ) {
        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.categoriesManagement(session)) {
            try {
                User user1=userDao.findOne(user);
                Category category1=categoryRepo.findOne(category);
                List<Schedule> scheduleCheck=scheduleRepo.findByCategoryAndUserAndActiveAndDayOfWeek(category1,user1,true,day);
                if(scheduleCheck.size()>0){
                    return new JsonMessage(JsonReturnCodes.ERROR);
                }
                Schedule schedule= new Schedule(user1,category1,day);
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
                                              @RequestParam(value = "category", required = true, defaultValue = "") long category){
        HashMap<Integer,WeekDay> weekdays= Variables.getWeekDays();
        Session session=sessionRepository.findOne(sessionId);
        User user1= userDao.findOne(user);
        Category category1=categoryRepo.findOne(category);
        List<Schedule> schedules=scheduleRepo.findByCategoryAndUserAndActive(category1,user1,true);
        schedules.stream().forEach(new Consumer<Schedule>() {
            @Override
            public void accept(Schedule schedule) {
                weekdays.remove(schedule.getDayOfWeek());
            }
        });

        return new ArrayList<>(weekdays.values());
    }


    @RequestMapping("/getusercategoryscheduledays/{user}/{category}")
    @ResponseBody
    public List<Schedule> getUserCategoryScheduleDays(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                                      @PathVariable(value = "user", required = true) long user,
                                                      @PathVariable(value = "category", required = true) long category){
        return scheduleRepo.findByCategoryAndUserAndActive(categoryRepo.findOne(category),userDao.findOne(user),true);
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
}
