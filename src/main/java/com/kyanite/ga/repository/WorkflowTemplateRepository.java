package com.kyanite.ga.repository;

import com.kyanite.ga.domain.WorkflowTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WorkflowTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkflowTemplateRepository extends JpaRepository<WorkflowTemplate, Long> {}
