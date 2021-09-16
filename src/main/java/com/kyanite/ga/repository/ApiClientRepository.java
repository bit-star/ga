package com.kyanite.ga.repository;

import com.kyanite.ga.domain.ApiClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApiClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiClientRepository extends JpaRepository<ApiClient, Long> {}
