package com.technonet.Repository;

import com.technonet.model.SysStringTranslations;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vakhtanggelashvili on 3/26/17.
 */
public interface SysStringTranslationRepo extends JpaRepository<SysStringTranslations,Long> {
}