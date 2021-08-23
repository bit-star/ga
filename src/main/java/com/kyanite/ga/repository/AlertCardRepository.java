package com.kyanite.ga.repository;

import com.kyanite.ga.domain.AlertCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AlertCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertCardRepository extends JpaRepository<AlertCard, Long> {}
