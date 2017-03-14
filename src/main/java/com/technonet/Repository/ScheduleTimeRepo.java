package com.technonet.Repository;

import com.technonet.model.Schedule;
import com.technonet.model.ScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;

/**
 * Created by kakha on 3/11/2017.
 */
public interface ScheduleTimeRepo extends JpaRepository<ScheduleTime,Long> {
    List<ScheduleTime> findByScheduleAndActiveOrderByStartTimeAsc(Schedule schedule, boolean active);

    List<ScheduleTime> findByStartTimeAfterAndEndTimeBeforeAndActive(Time startTime, Time endTime, boolean active);
}
