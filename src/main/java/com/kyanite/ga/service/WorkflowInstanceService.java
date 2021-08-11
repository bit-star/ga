package com.kyanite.ga.service;

import com.kyanite.ga.domain.WorkflowInstance;
import com.kyanite.ga.repository.WorkflowInstanceRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkflowInstance}.
 */
@Service
@Transactional
public class WorkflowInstanceService {

    private final Logger log = LoggerFactory.getLogger(WorkflowInstanceService.class);

    private final WorkflowInstanceRepository workflowInstanceRepository;

    public WorkflowInstanceService(WorkflowInstanceRepository workflowInstanceRepository) {
        this.workflowInstanceRepository = workflowInstanceRepository;
    }

    /**
     * Save a workflowInstance.
     *
     * @param workflowInstance the entity to save.
     * @return the persisted entity.
     */
    public WorkflowInstance save(WorkflowInstance workflowInstance) {
        log.debug("Request to save WorkflowInstance : {}", workflowInstance);
        return workflowInstanceRepository.save(workflowInstance);
    }

    /**
     * Partially update a workflowInstance.
     *
     * @param workflowInstance the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkflowInstance> partialUpdate(WorkflowInstance workflowInstance) {
        log.debug("Request to partially update WorkflowInstance : {}", workflowInstance);

        return workflowInstanceRepository
            .findById(workflowInstance.getId())
            .map(
                existingWorkflowInstance -> {
                    if (workflowInstance.getForm() != null) {
                        existingWorkflowInstance.setForm(workflowInstance.getForm());
                    }
                    if (workflowInstance.getDdCardId() != null) {
                        existingWorkflowInstance.setDdCardId(workflowInstance.getDdCardId());
                    }
                    if (workflowInstance.getTitle() != null) {
                        existingWorkflowInstance.setTitle(workflowInstance.getTitle());
                    }
                    if (workflowInstance.getDdCardTemplateId() != null) {
                        existingWorkflowInstance.setDdCardTemplateId(workflowInstance.getDdCardTemplateId());
                    }
                    if (workflowInstance.getDdCardCallBackKey() != null) {
                        existingWorkflowInstance.setDdCardCallBackKey(workflowInstance.getDdCardCallBackKey());
                    }
                    if (workflowInstance.getStatus() != null) {
                        existingWorkflowInstance.setStatus(workflowInstance.getStatus());
                    }

                    return existingWorkflowInstance;
                }
            )
            .map(workflowInstanceRepository::save);
    }

    /**
     * Get all the workflowInstances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WorkflowInstance> findAll() {
        log.debug("Request to get all WorkflowInstances");
        return workflowInstanceRepository.findAll();
    }

    /**
     * Get one workflowInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkflowInstance> findOne(Long id) {
        log.debug("Request to get WorkflowInstance : {}", id);
        return workflowInstanceRepository.findById(id);
    }

    /**
     * Delete the workflowInstance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkflowInstance : {}", id);
        workflowInstanceRepository.deleteById(id);
    }
}
