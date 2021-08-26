package com.kyanite.ga.service;

import com.kyanite.ga.domain.AlertCard;
import com.kyanite.ga.repository.AlertCardRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlertCard}.
 */
@Service
@Transactional
public class AlertCardService {

    private final Logger log = LoggerFactory.getLogger(AlertCardService.class);

    private final AlertCardRepository alertCardRepository;

    public AlertCardService(AlertCardRepository alertCardRepository) {
        this.alertCardRepository = alertCardRepository;
    }

    /**
     * Save a alertCard.
     *
     * @param alertCard the entity to save.
     * @return the persisted entity.
     */
    public AlertCard save(AlertCard alertCard) {
        log.debug("Request to save AlertCard : {}", alertCard);
        return alertCardRepository.save(alertCard);
    }

    /**
     * Partially update a alertCard.
     *
     * @param alertCard the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AlertCard> partialUpdate(AlertCard alertCard) {
        log.debug("Request to partially update AlertCard : {}", alertCard);

        return alertCardRepository
            .findById(alertCard.getId())
            .map(
                existingAlertCard -> {
                    if (alertCard.getText() != null) {
                        existingAlertCard.setText(alertCard.getText());
                    }
                    if (alertCard.getUserId() != null) {
                        existingAlertCard.setUserId(alertCard.getUserId());
                    }
                    if (alertCard.getLink() != null) {
                        existingAlertCard.setLink(alertCard.getLink());
                    }
                    if (alertCard.getMd1() != null) {
                        existingAlertCard.setMd1(alertCard.getMd1());
                    }

                    return existingAlertCard;
                }
            )
            .map(alertCardRepository::save);
    }

    /**
     * Get all the alertCards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AlertCard> findAll() {
        log.debug("Request to get all AlertCards");
        return alertCardRepository.findAll();
    }

    /**
     * Get one alertCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlertCard> findOne(Long id) {
        log.debug("Request to get AlertCard : {}", id);
        return alertCardRepository.findById(id);
    }

    /**
     * Delete the alertCard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AlertCard : {}", id);
        alertCardRepository.deleteById(id);
    }
}
