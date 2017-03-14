package com.technonet.Repository;

import com.technonet.model.BookedTime;
import com.technonet.model.UserCategoryJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 3/15/17.
 */
@Transactional
public interface BookedTimeRepo extends JpaRepository<BookedTime, Long> {
    @Query("select bt from BookedTime bt where bt.userCategoryJoin=:userCategoryJoin " +
            "and bt.startDate < :end " +
            "and bt.startDate > :start " +
            "and bt.active = true " +
            "order by bt.startDate")
    List<BookedTime> findInsideInterval(@Param("start") Date start,
                                        @Param("end") Date end,
                                        @Param("userCategoryJoin") UserCategoryJoin userCategoryJoin);
}
