package com.kyanite.ga.repository;

import com.kyanite.ga.domain.WorkflowInstance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WorkflowInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {}
