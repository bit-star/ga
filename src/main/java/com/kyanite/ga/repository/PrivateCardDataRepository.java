package com.kyanite.ga.repository;

import com.kyanite.ga.domain.PrivateCardData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PrivateCardData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrivateCardDataRepository extends JpaRepository<PrivateCardData, Long> {}
