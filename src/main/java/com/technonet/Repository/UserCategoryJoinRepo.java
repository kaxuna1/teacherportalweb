package com.technonet.Repository;

import com.technonet.model.UserCategoryJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kaxa on 3/8/17.
 */
@Transactional
public interface UserCategoryJoinRepo extends JpaRepository<UserCategoryJoin,Long> {
}
