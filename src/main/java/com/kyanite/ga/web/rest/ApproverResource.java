package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.Approver;
import com.kyanite.ga.repository.ApproverRepository;
import com.kyanite.ga.service.ApproverService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.Approver}.
 */
@RestController
@RequestMapping("/api")
public class ApproverResource {

    private final Logger log = LoggerFactory.getLogger(ApproverResource.class);

    private static final String ENTITY_NAME = "approver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApproverService approverService;

    private final ApproverRepository approverRepository;

    public ApproverResource(ApproverService approverService, ApproverRepository approverRepository) {
        this.approverService = approverService;
        this.approverRepository = approverRepository;
    }

    /**
     * {@code POST  /approvers} : Create a new approver.
     *
     * @param approver the approver to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approver, or with status {@code 400 (Bad Request)} if the approver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approvers")
    public ResponseEntity<Approver> createApprover(@RequestBody Approver approver) throws URISyntaxException {
        log.debug("REST request to save Approver : {}", approver);
        if (approver.getId() != null) {
            throw new BadRequestAlertException("A new approver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Approver result = approverService.save(approver);
        return ResponseEntity
            .created(new URI("/api/approvers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approvers/:id} : Updates an existing approver.
     *
     * @param id the id of the approver to save.
     * @param approver the approver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approver,
     * or with status {@code 400 (Bad Request)} if the approver is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approvers/{id}")
    public ResponseEntity<Approver> updateApprover(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Approver approver
    ) throws URISyntaxException {
        log.debug("REST request to update Approver : {}, {}", id, approver);
        if (approver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approver.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Approver result = approverService.save(approver);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approver.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approvers/:id} : Partial updates given fields of an existing approver, field will ignore if it is null
     *
     * @param id the id of the approver to save.
     * @param approver the approver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approver,
     * or with status {@code 400 (Bad Request)} if the approver is not valid,
     * or with status {@code 404 (Not Found)} if the approver is not found,
     * or with status {@code 500 (Internal Server Error)} if the approver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approvers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Approver> partialUpdateApprover(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Approver approver
    ) throws URISyntaxException {
        log.debug("REST request to partial update Approver partially : {}, {}", id, approver);
        if (approver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approver.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Approver> result = approverService.partialUpdate(approver);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approver.getId().toString())
        );
    }

    /**
     * {@code GET  /approvers} : get all the approvers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approvers in body.
     */
    @GetMapping("/approvers")
    public List<Approver> getAllApprovers() {
        log.debug("REST request to get all Approvers");
        return approverService.findAll();
    }

    /**
     * {@code GET  /approvers/:id} : get the "id" approver.
     *
     * @param id the id of the approver to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approver, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approvers/{id}")
    public ResponseEntity<Approver> getApprover(@PathVariable Long id) {
        log.debug("REST request to get Approver : {}", id);
        Optional<Approver> approver = approverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approver);
    }

    /**
     * {@code DELETE  /approvers/:id} : delete the "id" approver.
     *
     * @param id the id of the approver to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approvers/{id}")
    public ResponseEntity<Void> deleteApprover(@PathVariable Long id) {
        log.debug("REST request to delete Approver : {}", id);
        approverService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
