package com.technonet.Repository;

import com.technonet.model.Category;
import com.technonet.model.Schedule;
import com.technonet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kakha on 3/11/2017.
 */
public interface ScheduleRepo extends JpaRepository<Schedule,Long> {
    List<Schedule> findByCategoryAndUserAndActiveAndDayOfWeek(Category category, User user, boolean active, int day);

    List<Schedule> findByCategoryAndUserAndActive(Category category, User user, boolean active);
}
