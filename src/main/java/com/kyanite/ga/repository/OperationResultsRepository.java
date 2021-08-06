package com.kyanite.ga.repository;

import com.kyanite.ga.domain.OperationResults;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OperationResults entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationResultsRepository extends JpaRepository<OperationResults, Long> {}
