package com.kyanite.ga.repository;

import com.kyanite.ga.domain.ConfirmCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConfirmCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfirmCardRepository extends JpaRepository<ConfirmCard, Long> {}
