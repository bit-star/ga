package com.kyanite.ga.service;

import com.kyanite.ga.domain.Topboxes;
import com.kyanite.ga.repository.TopboxesRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Topboxes}.
 */
@Service
@Transactional
public class TopboxesService {

    private final Logger log = LoggerFactory.getLogger(TopboxesService.class);

    private final TopboxesRepository topboxesRepository;

    public TopboxesService(TopboxesRepository topboxesRepository) {
        this.topboxesRepository = topboxesRepository;
    }

    /**
     * Save a topboxes.
     *
     * @param topboxes the entity to save.
     * @return the persisted entity.
     */
    public Topboxes save(Topboxes topboxes) {
        log.debug("Request to save Topboxes : {}", topboxes);
        return topboxesRepository.save(topboxes);
    }

    /**
     * Partially update a topboxes.
     *
     * @param topboxes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Topboxes> partialUpdate(Topboxes topboxes) {
        log.debug("Request to partially update Topboxes : {}", topboxes);

        return topboxesRepository
            .findById(topboxes.getId())
            .map(
                existingTopboxes -> {
                    if (topboxes.getText() != null) {
                        existingTopboxes.setText(topboxes.getText());
                    }
                    if (topboxes.getLink() != null) {
                        existingTopboxes.setLink(topboxes.getLink());
                    }
                    if (topboxes.getCardId() != null) {
                        existingTopboxes.setCardId(topboxes.getCardId());
                    }
                    if (topboxes.getAuxiliary() != null) {
                        existingTopboxes.setAuxiliary(topboxes.getAuxiliary());
                    }
                    if (topboxes.getOpen() != null) {
                        existingTopboxes.setOpen(topboxes.getOpen());
                    }

                    return existingTopboxes;
                }
            )
            .map(topboxesRepository::save);
    }

    /**
     * Get all the topboxes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Topboxes> findAll() {
        log.debug("Request to get all Topboxes");
        return topboxesRepository.findAll();
    }

    /**
     *  Get all the topboxes where PublicCardData is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Topboxes> findAllWherePublicCardDataIsNull() {
        log.debug("Request to get all topboxes where PublicCardData is null");
        return StreamSupport
            .stream(topboxesRepository.findAll().spliterator(), false)
            .filter(topboxes -> topboxes.getPublicCardData() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one topboxes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Topboxes> findOne(Long id) {
        log.debug("Request to get Topboxes : {}", id);
        return topboxesRepository.findById(id);
    }

    /**
     * Delete the topboxes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Topboxes : {}", id);
        topboxesRepository.deleteById(id);
    }
}
