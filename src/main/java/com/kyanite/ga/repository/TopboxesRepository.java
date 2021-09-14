package com.kyanite.ga.repository;

import com.kyanite.ga.domain.Topboxes;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Topboxes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopboxesRepository extends JpaRepository<Topboxes, UUID> {}
