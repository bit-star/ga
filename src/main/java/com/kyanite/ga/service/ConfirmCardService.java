package com.kyanite.ga.service;

import com.kyanite.ga.domain.ConfirmCard;
import com.kyanite.ga.repository.ConfirmCardRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConfirmCard}.
 */
@Service
@Transactional
public class ConfirmCardService {

    private final Logger log = LoggerFactory.getLogger(ConfirmCardService.class);

    private final ConfirmCardRepository confirmCardRepository;

    public ConfirmCardService(ConfirmCardRepository confirmCardRepository) {
        this.confirmCardRepository = confirmCardRepository;
    }

    /**
     * Save a confirmCard.
     *
     * @param confirmCard the entity to save.
     * @return the persisted entity.
     */
    public ConfirmCard save(ConfirmCard confirmCard) {
        log.debug("Request to save ConfirmCard : {}", confirmCard);
        return confirmCardRepository.save(confirmCard);
    }

    /**
     * Partially update a confirmCard.
     *
     * @param confirmCard the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConfirmCard> partialUpdate(ConfirmCard confirmCard) {
        log.debug("Request to partially update ConfirmCard : {}", confirmCard);

        return confirmCardRepository
            .findById(confirmCard.getId())
            .map(existingConfirmCard -> {
                if (confirmCard.getText() != null) {
                    existingConfirmCard.setText(confirmCard.getText());
                }
                if (confirmCard.getFinish() != null) {
                    existingConfirmCard.setFinish(confirmCard.getFinish());
                }
                if (confirmCard.getUserId() != null) {
                    existingConfirmCard.setUserId(confirmCard.getUserId());
                }
                if (confirmCard.getLink() != null) {
                    existingConfirmCard.setLink(confirmCard.getLink());
                }
                if (confirmCard.getMd1() != null) {
                    existingConfirmCard.setMd1(confirmCard.getMd1());
                }

                return existingConfirmCard;
            })
            .map(confirmCardRepository::save);
    }

    /**
     * Get all the confirmCards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConfirmCard> findAll() {
        log.debug("Request to get all ConfirmCards");
        return confirmCardRepository.findAll();
    }

    /**
     * Get one confirmCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConfirmCard> findOne(Long id) {
        log.debug("Request to get ConfirmCard : {}", id);
        return confirmCardRepository.findById(id);
    }

    /**
     * Delete the confirmCard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConfirmCard : {}", id);
        confirmCardRepository.deleteById(id);
    }
}
