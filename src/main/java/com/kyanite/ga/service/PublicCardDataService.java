package com.kyanite.ga.service;

import com.kyanite.ga.domain.PublicCardData;
import com.kyanite.ga.repository.PublicCardDataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PublicCardData}.
 */
@Service
@Transactional
public class PublicCardDataService {

    private final Logger log = LoggerFactory.getLogger(PublicCardDataService.class);

    private final PublicCardDataRepository publicCardDataRepository;

    public PublicCardDataService(PublicCardDataRepository publicCardDataRepository) {
        this.publicCardDataRepository = publicCardDataRepository;
    }

    /**
     * Save a publicCardData.
     *
     * @param publicCardData the entity to save.
     * @return the persisted entity.
     */
    public PublicCardData save(PublicCardData publicCardData) {
        log.debug("Request to save PublicCardData : {}", publicCardData);
        return publicCardDataRepository.save(publicCardData);
    }

    /**
     * Partially update a publicCardData.
     *
     * @param publicCardData the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicCardData> partialUpdate(PublicCardData publicCardData) {
        log.debug("Request to partially update PublicCardData : {}", publicCardData);

        return publicCardDataRepository
            .findById(publicCardData.getId())
            .map(
                existingPublicCardData -> {
                    if (publicCardData.getRequestid() != null) {
                        existingPublicCardData.setRequestid(publicCardData.getRequestid());
                    }
                    if (publicCardData.getWorkflowid() != null) {
                        existingPublicCardData.setWorkflowid(publicCardData.getWorkflowid());
                    }
                    if (publicCardData.getValid() != null) {
                        existingPublicCardData.setValid(publicCardData.getValid());
                    }
                    if (publicCardData.getLink() != null) {
                        existingPublicCardData.setLink(publicCardData.getLink());
                    }
                    if (publicCardData.getName() != null) {
                        existingPublicCardData.setName(publicCardData.getName());
                    }
                    if (publicCardData.getFeeValue() != null) {
                        existingPublicCardData.setFeeValue(publicCardData.getFeeValue());
                    }
                    if (publicCardData.getReason() != null) {
                        existingPublicCardData.setReason(publicCardData.getReason());
                    }
                    if (publicCardData.getItemType() != null) {
                        existingPublicCardData.setItemType(publicCardData.getItemType());
                    }
                    if (publicCardData.getTypesOfFee() != null) {
                        existingPublicCardData.setTypesOfFee(publicCardData.getTypesOfFee());
                    }
                    if (publicCardData.getAgree() != null) {
                        existingPublicCardData.setAgree(publicCardData.getAgree());
                    }
                    if (publicCardData.getFinish() != null) {
                        existingPublicCardData.setFinish(publicCardData.getFinish());
                    }
                    if (publicCardData.getStatus() != null) {
                        existingPublicCardData.setStatus(publicCardData.getStatus());
                    }
                    if (publicCardData.getContent() != null) {
                        existingPublicCardData.setContent(publicCardData.getContent());
                    }
                    if (publicCardData.getRefuse() != null) {
                        existingPublicCardData.setRefuse(publicCardData.getRefuse());
                    }

                    return existingPublicCardData;
                }
            )
            .map(publicCardDataRepository::save);
    }

    /**
     * Get all the publicCardData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublicCardData> findAll() {
        log.debug("Request to get all PublicCardData");
        return publicCardDataRepository.findAll();
    }

    /**
     * Get one publicCardData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicCardData> findOne(Long id) {
        log.debug("Request to get PublicCardData : {}", id);
        return publicCardDataRepository.findById(id);
    }

    /**
     * Delete the publicCardData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PublicCardData : {}", id);
        publicCardDataRepository.deleteById(id);
    }
}
