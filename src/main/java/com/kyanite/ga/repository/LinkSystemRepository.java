package com.kyanite.ga.repository;

import com.kyanite.ga.domain.LinkSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LinkSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkSystemRepository extends JpaRepository<LinkSystem, Long> {}
