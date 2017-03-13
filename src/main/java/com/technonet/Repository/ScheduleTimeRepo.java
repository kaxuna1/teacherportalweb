package com.technonet.Repository;

import com.technonet.model.ScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kakha on 3/11/2017.
 */
public interface ScheduleTimeRepo extends JpaRepository<ScheduleTime,Long> {
}
