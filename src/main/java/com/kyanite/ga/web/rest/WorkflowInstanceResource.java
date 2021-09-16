package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.WorkflowInstance;
import com.kyanite.ga.repository.WorkflowInstanceRepository;
import com.kyanite.ga.service.WorkflowInstanceService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.WorkflowInstance}.
 */
@RestController
@RequestMapping("/api")
public class WorkflowInstanceResource {

    private final Logger log = LoggerFactory.getLogger(WorkflowInstanceResource.class);

    private static final String ENTITY_NAME = "workflowInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkflowInstanceService workflowInstanceService;

    private final WorkflowInstanceRepository workflowInstanceRepository;

    public WorkflowInstanceResource(
        WorkflowInstanceService workflowInstanceService,
        WorkflowInstanceRepository workflowInstanceRepository
    ) {
        this.workflowInstanceService = workflowInstanceService;
        this.workflowInstanceRepository = workflowInstanceRepository;
    }

    /**
     * {@code POST  /workflow-instances} : Create a new workflowInstance.
     *
     * @param workflowInstance the workflowInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workflowInstance, or with status {@code 400 (Bad Request)} if the workflowInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workflow-instances")
    public ResponseEntity<WorkflowInstance> createWorkflowInstance(@RequestBody WorkflowInstance workflowInstance)
        throws URISyntaxException {
        log.debug("REST request to save WorkflowInstance : {}", workflowInstance);
        if (workflowInstance.getId() != null) {
            throw new BadRequestAlertException("A new workflowInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowInstance result = workflowInstanceService.save(workflowInstance);
        return ResponseEntity
            .created(new URI("/api/workflow-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workflow-instances/:id} : Updates an existing workflowInstance.
     *
     * @param id the id of the workflowInstance to save.
     * @param workflowInstance the workflowInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workflowInstance,
     * or with status {@code 400 (Bad Request)} if the workflowInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workflowInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workflow-instances/{id}")
    public ResponseEntity<WorkflowInstance> updateWorkflowInstance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkflowInstance workflowInstance
    ) throws URISyntaxException {
        log.debug("REST request to update WorkflowInstance : {}, {}", id, workflowInstance);
        if (workflowInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workflowInstance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workflowInstanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkflowInstance result = workflowInstanceService.save(workflowInstance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workflowInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /workflow-instances/:id} : Partial updates given fields of an existing workflowInstance, field will ignore if it is null
     *
     * @param id the id of the workflowInstance to save.
     * @param workflowInstance the workflowInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workflowInstance,
     * or with status {@code 400 (Bad Request)} if the workflowInstance is not valid,
     * or with status {@code 404 (Not Found)} if the workflowInstance is not found,
     * or with status {@code 500 (Internal Server Error)} if the workflowInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/workflow-instances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkflowInstance> partialUpdateWorkflowInstance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkflowInstance workflowInstance
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkflowInstance partially : {}, {}", id, workflowInstance);
        if (workflowInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workflowInstance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workflowInstanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkflowInstance> result = workflowInstanceService.partialUpdate(workflowInstance);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workflowInstance.getId().toString())
        );
    }

    /**
     * {@code GET  /workflow-instances} : get all the workflowInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workflowInstances in body.
     */
    @GetMapping("/workflow-instances")
    public List<WorkflowInstance> getAllWorkflowInstances() {
        log.debug("REST request to get all WorkflowInstances");
        return workflowInstanceService.findAll();
    }

    /**
     * {@code GET  /workflow-instances/:id} : get the "id" workflowInstance.
     *
     * @param id the id of the workflowInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workflowInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workflow-instances/{id}")
    public ResponseEntity<WorkflowInstance> getWorkflowInstance(@PathVariable Long id) {
        log.debug("REST request to get WorkflowInstance : {}", id);
        Optional<WorkflowInstance> workflowInstance = workflowInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workflowInstance);
    }

    /**
     * {@code DELETE  /workflow-instances/:id} : delete the "id" workflowInstance.
     *
     * @param id the id of the workflowInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workflow-instances/{id}")
    public ResponseEntity<Void> deleteWorkflowInstance(@PathVariable Long id) {
        log.debug("REST request to delete WorkflowInstance : {}", id);
        workflowInstanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
