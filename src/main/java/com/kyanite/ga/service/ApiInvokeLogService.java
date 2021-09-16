package com.kyanite.ga.service;

import com.kyanite.ga.domain.ApiInvokeLog;
import com.kyanite.ga.repository.ApiInvokeLogRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApiInvokeLog}.
 */
@Service
@Transactional
public class ApiInvokeLogService {

    private final Logger log = LoggerFactory.getLogger(ApiInvokeLogService.class);

    private final ApiInvokeLogRepository apiInvokeLogRepository;

    public ApiInvokeLogService(ApiInvokeLogRepository apiInvokeLogRepository) {
        this.apiInvokeLogRepository = apiInvokeLogRepository;
    }

    /**
     * Save a apiInvokeLog.
     *
     * @param apiInvokeLog the entity to save.
     * @return the persisted entity.
     */
    public ApiInvokeLog save(ApiInvokeLog apiInvokeLog) {
        log.debug("Request to save ApiInvokeLog : {}", apiInvokeLog);
        return apiInvokeLogRepository.save(apiInvokeLog);
    }

    /**
     * Partially update a apiInvokeLog.
     *
     * @param apiInvokeLog the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApiInvokeLog> partialUpdate(ApiInvokeLog apiInvokeLog) {
        log.debug("Request to partially update ApiInvokeLog : {}", apiInvokeLog);

        return apiInvokeLogRepository
            .findById(apiInvokeLog.getId())
            .map(existingApiInvokeLog -> {
                if (apiInvokeLog.getLogin() != null) {
                    existingApiInvokeLog.setLogin(apiInvokeLog.getLogin());
                }
                if (apiInvokeLog.getApiName() != null) {
                    existingApiInvokeLog.setApiName(apiInvokeLog.getApiName());
                }
                if (apiInvokeLog.getMethod() != null) {
                    existingApiInvokeLog.setMethod(apiInvokeLog.getMethod());
                }
                if (apiInvokeLog.getDirection() != null) {
                    existingApiInvokeLog.setDirection(apiInvokeLog.getDirection());
                }
                if (apiInvokeLog.getHttpStatusCode() != null) {
                    existingApiInvokeLog.setHttpStatusCode(apiInvokeLog.getHttpStatusCode());
                }
                if (apiInvokeLog.getTime() != null) {
                    existingApiInvokeLog.setTime(apiInvokeLog.getTime());
                }
                if (apiInvokeLog.getReqeustData() != null) {
                    existingApiInvokeLog.setReqeustData(apiInvokeLog.getReqeustData());
                }
                if (apiInvokeLog.getResponseData() != null) {
                    existingApiInvokeLog.setResponseData(apiInvokeLog.getResponseData());
                }
                if (apiInvokeLog.getOk() != null) {
                    existingApiInvokeLog.setOk(apiInvokeLog.getOk());
                }

                return existingApiInvokeLog;
            })
            .map(apiInvokeLogRepository::save);
    }

    /**
     * Get all the apiInvokeLogs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApiInvokeLog> findAll() {
        log.debug("Request to get all ApiInvokeLogs");
        return apiInvokeLogRepository.findAll();
    }

    /**
     * Get one apiInvokeLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApiInvokeLog> findOne(Long id) {
        log.debug("Request to get ApiInvokeLog : {}", id);
        return apiInvokeLogRepository.findById(id);
    }

    /**
     * Delete the apiInvokeLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiInvokeLog : {}", id);
        apiInvokeLogRepository.deleteById(id);
    }
}
