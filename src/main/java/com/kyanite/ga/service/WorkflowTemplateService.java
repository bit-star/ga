package com.kyanite.ga.service;

import com.kyanite.ga.domain.WorkflowTemplate;
import com.kyanite.ga.repository.WorkflowTemplateRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkflowTemplate}.
 */
@Service
@Transactional
public class WorkflowTemplateService {

    private final Logger log = LoggerFactory.getLogger(WorkflowTemplateService.class);

    private final WorkflowTemplateRepository workflowTemplateRepository;

    public WorkflowTemplateService(WorkflowTemplateRepository workflowTemplateRepository) {
        this.workflowTemplateRepository = workflowTemplateRepository;
    }

    /**
     * Save a workflowTemplate.
     *
     * @param workflowTemplate the entity to save.
     * @return the persisted entity.
     */
    public WorkflowTemplate save(WorkflowTemplate workflowTemplate) {
        log.debug("Request to save WorkflowTemplate : {}", workflowTemplate);
        return workflowTemplateRepository.save(workflowTemplate);
    }

    /**
     * Partially update a workflowTemplate.
     *
     * @param workflowTemplate the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkflowTemplate> partialUpdate(WorkflowTemplate workflowTemplate) {
        log.debug("Request to partially update WorkflowTemplate : {}", workflowTemplate);

        return workflowTemplateRepository
            .findById(workflowTemplate.getId())
            .map(
                existingWorkflowTemplate -> {
                    if (workflowTemplate.getFormId() != null) {
                        existingWorkflowTemplate.setFormId(workflowTemplate.getFormId());
                    }
                    if (workflowTemplate.getWorkflowId() != null) {
                        existingWorkflowTemplate.setWorkflowId(workflowTemplate.getWorkflowId());
                    }
                    if (workflowTemplate.getWorkflowName() != null) {
                        existingWorkflowTemplate.setWorkflowName(workflowTemplate.getWorkflowName());
                    }
                    if (workflowTemplate.getWorkflowTypeId() != null) {
                        existingWorkflowTemplate.setWorkflowTypeId(workflowTemplate.getWorkflowTypeId());
                    }
                    if (workflowTemplate.getWorkflowTypeName() != null) {
                        existingWorkflowTemplate.setWorkflowTypeName(workflowTemplate.getWorkflowTypeName());
                    }
                    if (workflowTemplate.getDdGroupTemplateId() != null) {
                        existingWorkflowTemplate.setDdGroupTemplateId(workflowTemplate.getDdGroupTemplateId());
                    }
                    if (workflowTemplate.getDdCardTemplateId() != null) {
                        existingWorkflowTemplate.setDdCardTemplateId(workflowTemplate.getDdCardTemplateId());
                    }
                    if (workflowTemplate.getDdCardCallBackKey() != null) {
                        existingWorkflowTemplate.setDdCardCallBackKey(workflowTemplate.getDdCardCallBackKey());
                    }
                    if (workflowTemplate.geteMobileCreatePageUrl() != null) {
                        existingWorkflowTemplate.seteMobileCreatePageUrl(workflowTemplate.geteMobileCreatePageUrl());
                    }
                    if (workflowTemplate.getChatidField() != null) {
                        existingWorkflowTemplate.setChatidField(workflowTemplate.getChatidField());
                    }
                    if (workflowTemplate.getSourceField() != null) {
                        existingWorkflowTemplate.setSourceField(workflowTemplate.getSourceField());
                    }
                    if (workflowTemplate.getCommentsField() != null) {
                        existingWorkflowTemplate.setCommentsField(workflowTemplate.getCommentsField());
                    }

                    return existingWorkflowTemplate;
                }
            )
            .map(workflowTemplateRepository::save);
    }

    /**
     * Get all the workflowTemplates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WorkflowTemplate> findAll() {
        log.debug("Request to get all WorkflowTemplates");
        return workflowTemplateRepository.findAll();
    }

    /**
     * Get one workflowTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkflowTemplate> findOne(Long id) {
        log.debug("Request to get WorkflowTemplate : {}", id);
        return workflowTemplateRepository.findById(id);
    }

    /**
     * Delete the workflowTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkflowTemplate : {}", id);
        workflowTemplateRepository.deleteById(id);
    }
}
