package com.kyanite.ga.service;

import com.kyanite.ga.domain.LinkSystem;
import com.kyanite.ga.repository.LinkSystemRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LinkSystem}.
 */
@Service
@Transactional
public class LinkSystemService {

    private final Logger log = LoggerFactory.getLogger(LinkSystemService.class);

    private final LinkSystemRepository linkSystemRepository;

    public LinkSystemService(LinkSystemRepository linkSystemRepository) {
        this.linkSystemRepository = linkSystemRepository;
    }

    /**
     * Save a linkSystem.
     *
     * @param linkSystem the entity to save.
     * @return the persisted entity.
     */
    public LinkSystem save(LinkSystem linkSystem) {
        log.debug("Request to save LinkSystem : {}", linkSystem);
        return linkSystemRepository.save(linkSystem);
    }

    /**
     * Partially update a linkSystem.
     *
     * @param linkSystem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LinkSystem> partialUpdate(LinkSystem linkSystem) {
        log.debug("Request to partially update LinkSystem : {}", linkSystem);

        return linkSystemRepository
            .findById(linkSystem.getId())
            .map(existingLinkSystem -> {
                if (linkSystem.getName() != null) {
                    existingLinkSystem.setName(linkSystem.getName());
                }

                return existingLinkSystem;
            })
            .map(linkSystemRepository::save);
    }

    /**
     * Get all the linkSystems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LinkSystem> findAll() {
        log.debug("Request to get all LinkSystems");
        return linkSystemRepository.findAll();
    }

    /**
     * Get one linkSystem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LinkSystem> findOne(Long id) {
        log.debug("Request to get LinkSystem : {}", id);
        return linkSystemRepository.findById(id);
    }

    /**
     * Delete the linkSystem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LinkSystem : {}", id);
        linkSystemRepository.deleteById(id);
    }
}
