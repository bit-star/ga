package com.kyanite.ga.repository;

import com.kyanite.ga.domain.PublicCardData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PublicCardData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicCardDataRepository extends JpaRepository<PublicCardData, Long> {}
