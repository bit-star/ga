package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.OperationResults;
import com.kyanite.ga.repository.OperationResultsRepository;
import com.kyanite.ga.service.OperationResultsService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.OperationResults}.
 */
@RestController
@RequestMapping("/api")
public class OperationResultsResource {

    private final Logger log = LoggerFactory.getLogger(OperationResultsResource.class);

    private static final String ENTITY_NAME = "operationResults";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationResultsService operationResultsService;

    private final OperationResultsRepository operationResultsRepository;

    public OperationResultsResource(
        OperationResultsService operationResultsService,
        OperationResultsRepository operationResultsRepository
    ) {
        this.operationResultsService = operationResultsService;
        this.operationResultsRepository = operationResultsRepository;
    }

    /**
     * {@code POST  /operation-results} : Create a new operationResults.
     *
     * @param operationResults the operationResults to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationResults, or with status {@code 400 (Bad Request)} if the operationResults has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-results")
    public ResponseEntity<OperationResults> createOperationResults(@RequestBody OperationResults operationResults)
        throws URISyntaxException {
        log.debug("REST request to save OperationResults : {}", operationResults);
        if (operationResults.getId() != null) {
            throw new BadRequestAlertException("A new operationResults cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationResults result = operationResultsService.save(operationResults);
        return ResponseEntity
            .created(new URI("/api/operation-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-results/:id} : Updates an existing operationResults.
     *
     * @param id the id of the operationResults to save.
     * @param operationResults the operationResults to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationResults,
     * or with status {@code 400 (Bad Request)} if the operationResults is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationResults couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-results/{id}")
    public ResponseEntity<OperationResults> updateOperationResults(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationResults operationResults
    ) throws URISyntaxException {
        log.debug("REST request to update OperationResults : {}, {}", id, operationResults);
        if (operationResults.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationResults.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationResultsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperationResults result = operationResultsService.save(operationResults);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationResults.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /operation-results/:id} : Partial updates given fields of an existing operationResults, field will ignore if it is null
     *
     * @param id the id of the operationResults to save.
     * @param operationResults the operationResults to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationResults,
     * or with status {@code 400 (Bad Request)} if the operationResults is not valid,
     * or with status {@code 404 (Not Found)} if the operationResults is not found,
     * or with status {@code 500 (Internal Server Error)} if the operationResults couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operation-results/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperationResults> partialUpdateOperationResults(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationResults operationResults
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperationResults partially : {}, {}", id, operationResults);
        if (operationResults.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationResults.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationResultsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperationResults> result = operationResultsService.partialUpdate(operationResults);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationResults.getId().toString())
        );
    }

    /**
     * {@code GET  /operation-results} : get all the operationResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationResults in body.
     */
    @GetMapping("/operation-results")
    public List<OperationResults> getAllOperationResults() {
        log.debug("REST request to get all OperationResults");
        return operationResultsService.findAll();
    }

    /**
     * {@code GET  /operation-results/:id} : get the "id" operationResults.
     *
     * @param id the id of the operationResults to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationResults, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-results/{id}")
    public ResponseEntity<OperationResults> getOperationResults(@PathVariable Long id) {
        log.debug("REST request to get OperationResults : {}", id);
        Optional<OperationResults> operationResults = operationResultsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationResults);
    }

    /**
     * {@code DELETE  /operation-results/:id} : delete the "id" operationResults.
     *
     * @param id the id of the operationResults to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-results/{id}")
    public ResponseEntity<Void> deleteOperationResults(@PathVariable Long id) {
        log.debug("REST request to delete OperationResults : {}", id);
        operationResultsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
