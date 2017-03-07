package com.technonet.Repository;

import com.technonet.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kakha on 3/7/2017.
 */
@Transactional
public interface CategoryRepo extends JpaRepository<Category,Long> {
}
