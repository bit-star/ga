package com.kyanite.ga.service;

import com.kyanite.ga.domain.PrivateCardData;
import com.kyanite.ga.repository.PrivateCardDataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PrivateCardData}.
 */
@Service
@Transactional
public class PrivateCardDataService {

    private final Logger log = LoggerFactory.getLogger(PrivateCardDataService.class);

    private final PrivateCardDataRepository privateCardDataRepository;

    public PrivateCardDataService(PrivateCardDataRepository privateCardDataRepository) {
        this.privateCardDataRepository = privateCardDataRepository;
    }

    /**
     * Save a privateCardData.
     *
     * @param privateCardData the entity to save.
     * @return the persisted entity.
     */
    public PrivateCardData save(PrivateCardData privateCardData) {
        log.debug("Request to save PrivateCardData : {}", privateCardData);
        return privateCardDataRepository.save(privateCardData);
    }

    /**
     * Partially update a privateCardData.
     *
     * @param privateCardData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrivateCardData> partialUpdate(PrivateCardData privateCardData) {
        log.debug("Request to partially update PrivateCardData : {}", privateCardData);

        return privateCardDataRepository
            .findById(privateCardData.getId())
            .map(
                existingPrivateCardData -> {
                    if (privateCardData.getAgree() != null) {
                        existingPrivateCardData.setAgree(privateCardData.getAgree());
                    }
                    if (privateCardData.getFinish() != null) {
                        existingPrivateCardData.setFinish(privateCardData.getFinish());
                    }
                    if (privateCardData.getAuthority() != null) {
                        existingPrivateCardData.setAuthority(privateCardData.getAuthority());
                    }
                    if (privateCardData.getCreatedByMe() != null) {
                        existingPrivateCardData.setCreatedByMe(privateCardData.getCreatedByMe());
                    }
                    if (privateCardData.getUpdateTime() != null) {
                        existingPrivateCardData.setUpdateTime(privateCardData.getUpdateTime());
                    }

                    return existingPrivateCardData;
                }
            )
            .map(privateCardDataRepository::save);
    }

    /**
     * Get all the privateCardData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrivateCardData> findAll() {
        log.debug("Request to get all PrivateCardData");
        return privateCardDataRepository.findAll();
    }

    /**
     * Get one privateCardData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrivateCardData> findOne(Long id) {
        log.debug("Request to get PrivateCardData : {}", id);
        return privateCardDataRepository.findById(id);
    }

    /**
     * Delete the privateCardData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrivateCardData : {}", id);
        privateCardDataRepository.deleteById(id);
    }
}
