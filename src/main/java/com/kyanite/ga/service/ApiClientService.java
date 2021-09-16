package com.kyanite.ga.service;

import com.kyanite.ga.domain.ApiClient;
import com.kyanite.ga.repository.ApiClientRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApiClient}.
 */
@Service
@Transactional
public class ApiClientService {

    private final Logger log = LoggerFactory.getLogger(ApiClientService.class);

    private final ApiClientRepository apiClientRepository;

    public ApiClientService(ApiClientRepository apiClientRepository) {
        this.apiClientRepository = apiClientRepository;
    }

    /**
     * Save a apiClient.
     *
     * @param apiClient the entity to save.
     * @return the persisted entity.
     */
    public ApiClient save(ApiClient apiClient) {
        log.debug("Request to save ApiClient : {}", apiClient);
        return apiClientRepository.save(apiClient);
    }

    /**
     * Partially update a apiClient.
     *
     * @param apiClient the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApiClient> partialUpdate(ApiClient apiClient) {
        log.debug("Request to partially update ApiClient : {}", apiClient);

        return apiClientRepository
            .findById(apiClient.getId())
            .map(existingApiClient -> {
                if (apiClient.getName() != null) {
                    existingApiClient.setName(apiClient.getName());
                }
                if (apiClient.getApiKey() != null) {
                    existingApiClient.setApiKey(apiClient.getApiKey());
                }
                if (apiClient.getApiSecret() != null) {
                    existingApiClient.setApiSecret(apiClient.getApiSecret());
                }
                if (apiClient.getEnable() != null) {
                    existingApiClient.setEnable(apiClient.getEnable());
                }

                return existingApiClient;
            })
            .map(apiClientRepository::save);
    }

    /**
     * Get all the apiClients.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApiClient> findAll() {
        log.debug("Request to get all ApiClients");
        return apiClientRepository.findAll();
    }

    /**
     * Get one apiClient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApiClient> findOne(Long id) {
        log.debug("Request to get ApiClient : {}", id);
        return apiClientRepository.findById(id);
    }

    /**
     * Delete the apiClient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiClient : {}", id);
        apiClientRepository.deleteById(id);
    }
}
