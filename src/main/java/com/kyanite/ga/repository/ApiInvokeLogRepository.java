package com.kyanite.ga.repository;

import com.kyanite.ga.domain.ApiInvokeLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApiInvokeLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiInvokeLogRepository extends JpaRepository<ApiInvokeLog, Long> {}
