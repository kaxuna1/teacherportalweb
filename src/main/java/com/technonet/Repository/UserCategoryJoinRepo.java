package com.technonet.Repository;

import com.technonet.model.Category;
import com.technonet.model.City;
import com.technonet.model.User;
import com.technonet.model.UserCategoryJoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kaxa on 3/8/17.
 */
@Transactional
public interface UserCategoryJoinRepo extends JpaRepository<UserCategoryJoin,Long> {
    List<UserCategoryJoin> findByUserAndCategoryAndActive(User user, Category category, boolean active);

    @Query("select u from UserCategoryJoin u join u.user us " +
            "where u.category=:category and us.city=:city")
    Page<UserCategoryJoin> findByCategoryAndCity(@Param("category") Category category, @Param("city") City city, Pageable pageable);
}
