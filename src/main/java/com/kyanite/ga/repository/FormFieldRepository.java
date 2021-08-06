package com.kyanite.ga.repository;

import com.kyanite.ga.domain.FormField;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FormField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormFieldRepository extends JpaRepository<FormField, Long> {}
