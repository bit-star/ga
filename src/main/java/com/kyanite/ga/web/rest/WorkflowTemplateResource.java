package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.WorkflowTemplate;
import com.kyanite.ga.repository.WorkflowTemplateRepository;
import com.kyanite.ga.service.WorkflowTemplateService;
import com.kyanite.ga.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kyanite.ga.domain.WorkflowTemplate}.
 */
@RestController
@RequestMapping("/api")
public class WorkflowTemplateResource {

    private final Logger log = LoggerFactory.getLogger(WorkflowTemplateResource.class);

    private static final String ENTITY_NAME = "workflowTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkflowTemplateService workflowTemplateService;

    private final WorkflowTemplateRepository workflowTemplateRepository;

    public WorkflowTemplateResource(
        WorkflowTemplateService workflowTemplateService,
        WorkflowTemplateRepository workflowTemplateRepository
    ) {
        this.workflowTemplateService = workflowTemplateService;
        this.workflowTemplateRepository = workflowTemplateRepository;
    }

    /**
     * {@code POST  /workflow-templates} : Create a new workflowTemplate.
     *
     * @param workflowTemplate the workflowTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workflowTemplate, or with status {@code 400 (Bad Request)} if the workflowTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workflow-templates")
    public ResponseEntity<WorkflowTemplate> createWorkflowTemplate(@RequestBody WorkflowTemplate workflowTemplate)
        throws URISyntaxException {
        log.debug("REST request to save WorkflowTemplate : {}", workflowTemplate);
        if (workflowTemplate.getId() != null) {
            throw new BadRequestAlertException("A new workflowTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowTemplate result = workflowTemplateService.save(workflowTemplate);
        return ResponseEntity
            .created(new URI("/api/workflow-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workflow-templates/:id} : Updates an existing workflowTemplate.
     *
     * @param id the id of the workflowTemplate to save.
     * @param workflowTemplate the workflowTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workflowTemplate,
     * or with status {@code 400 (Bad Request)} if the workflowTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workflowTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workflow-templates/{id}")
    public ResponseEntity<WorkflowTemplate> updateWorkflowTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkflowTemplate workflowTemplate
    ) throws URISyntaxException {
        log.debug("REST request to update WorkflowTemplate : {}, {}", id, workflowTemplate);
        if (workflowTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workflowTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workflowTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkflowTemplate result = workflowTemplateService.save(workflowTemplate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workflowTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /workflow-templates/:id} : Partial updates given fields of an existing workflowTemplate, field will ignore if it is null
     *
     * @param id the id of the workflowTemplate to save.
     * @param workflowTemplate the workflowTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workflowTemplate,
     * or with status {@code 400 (Bad Request)} if the workflowTemplate is not valid,
     * or with status {@code 404 (Not Found)} if the workflowTemplate is not found,
     * or with status {@code 500 (Internal Server Error)} if the workflowTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/workflow-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkflowTemplate> partialUpdateWorkflowTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkflowTemplate workflowTemplate
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkflowTemplate partially : {}, {}", id, workflowTemplate);
        if (workflowTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workflowTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workflowTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkflowTemplate> result = workflowTemplateService.partialUpdate(workflowTemplate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workflowTemplate.getId().toString())
        );
    }

    /**
     * {@code GET  /workflow-templates} : get all the workflowTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workflowTemplates in body.
     */
    @GetMapping("/workflow-templates")
    public List<WorkflowTemplate> getAllWorkflowTemplates() {
        log.debug("REST request to get all WorkflowTemplates");
        return workflowTemplateService.findAll();
    }

    /**
     * {@code GET  /workflow-templates/:id} : get the "id" workflowTemplate.
     *
     * @param id the id of the workflowTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workflowTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workflow-templates/{id}")
    public ResponseEntity<WorkflowTemplate> getWorkflowTemplate(@PathVariable Long id) {
        log.debug("REST request to get WorkflowTemplate : {}", id);
        Optional<WorkflowTemplate> workflowTemplate = workflowTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workflowTemplate);
    }

    /**
     * {@code DELETE  /workflow-templates/:id} : delete the "id" workflowTemplate.
     *
     * @param id the id of the workflowTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workflow-templates/{id}")
    public ResponseEntity<Void> deleteWorkflowTemplate(@PathVariable Long id) {
        log.debug("REST request to delete WorkflowTemplate : {}", id);
        workflowTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
