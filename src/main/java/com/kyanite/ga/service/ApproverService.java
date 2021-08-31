package com.kyanite.ga.service;

import com.kyanite.ga.domain.Approver;
import com.kyanite.ga.repository.ApproverRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Approver}.
 */
@Service
@Transactional
public class ApproverService {

    private final Logger log = LoggerFactory.getLogger(ApproverService.class);

    private final ApproverRepository approverRepository;

    public ApproverService(ApproverRepository approverRepository) {
        this.approverRepository = approverRepository;
    }

    /**
     * Save a approver.
     *
     * @param approver the entity to save.
     * @return the persisted entity.
     */
    public Approver save(Approver approver) {
        log.debug("Request to save Approver : {}", approver);
        return approverRepository.save(approver);
    }

    /**
     * Partially update a approver.
     *
     * @param approver the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Approver> partialUpdate(Approver approver) {
        log.debug("Request to partially update Approver : {}", approver);

        return approverRepository
            .findById(approver.getId())
            .map(
                existingApprover -> {
                    if (approver.getApproverRole() != null) {
                        existingApprover.setApproverRole(approver.getApproverRole());
                    }
                    if (approver.getOaUserId() != null) {
                        existingApprover.setOaUserId(approver.getOaUserId());
                    }
                    if (approver.getEmail() != null) {
                        existingApprover.setEmail(approver.getEmail());
                    }

                    return existingApprover;
                }
            )
            .map(approverRepository::save);
    }

    /**
     * Get all the approvers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Approver> findAll() {
        log.debug("Request to get all Approvers");
        return approverRepository.findAll();
    }

    /**
     * Get one approver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Approver> findOne(Long id) {
        log.debug("Request to get Approver : {}", id);
        return approverRepository.findById(id);
    }

    /**
     * Delete the approver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Approver : {}", id);
        approverRepository.deleteById(id);
    }
}
