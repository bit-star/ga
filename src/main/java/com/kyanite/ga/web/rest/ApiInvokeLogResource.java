package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.ApiInvokeLog;
import com.kyanite.ga.repository.ApiInvokeLogRepository;
import com.kyanite.ga.service.ApiInvokeLogService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.ApiInvokeLog}.
 */
@RestController
@RequestMapping("/api")
public class ApiInvokeLogResource {

    private final Logger log = LoggerFactory.getLogger(ApiInvokeLogResource.class);

    private static final String ENTITY_NAME = "apiInvokeLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiInvokeLogService apiInvokeLogService;

    private final ApiInvokeLogRepository apiInvokeLogRepository;

    public ApiInvokeLogResource(ApiInvokeLogService apiInvokeLogService, ApiInvokeLogRepository apiInvokeLogRepository) {
        this.apiInvokeLogService = apiInvokeLogService;
        this.apiInvokeLogRepository = apiInvokeLogRepository;
    }

    /**
     * {@code POST  /api-invoke-logs} : Create a new apiInvokeLog.
     *
     * @param apiInvokeLog the apiInvokeLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiInvokeLog, or with status {@code 400 (Bad Request)} if the apiInvokeLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-invoke-logs")
    public ResponseEntity<ApiInvokeLog> createApiInvokeLog(@RequestBody ApiInvokeLog apiInvokeLog) throws URISyntaxException {
        log.debug("REST request to save ApiInvokeLog : {}", apiInvokeLog);
        if (apiInvokeLog.getId() != null) {
            throw new BadRequestAlertException("A new apiInvokeLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiInvokeLog result = apiInvokeLogService.save(apiInvokeLog);
        return ResponseEntity
            .created(new URI("/api/api-invoke-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-invoke-logs/:id} : Updates an existing apiInvokeLog.
     *
     * @param id the id of the apiInvokeLog to save.
     * @param apiInvokeLog the apiInvokeLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiInvokeLog,
     * or with status {@code 400 (Bad Request)} if the apiInvokeLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiInvokeLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-invoke-logs/{id}")
    public ResponseEntity<ApiInvokeLog> updateApiInvokeLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApiInvokeLog apiInvokeLog
    ) throws URISyntaxException {
        log.debug("REST request to update ApiInvokeLog : {}, {}", id, apiInvokeLog);
        if (apiInvokeLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiInvokeLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiInvokeLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApiInvokeLog result = apiInvokeLogService.save(apiInvokeLog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiInvokeLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /api-invoke-logs/:id} : Partial updates given fields of an existing apiInvokeLog, field will ignore if it is null
     *
     * @param id the id of the apiInvokeLog to save.
     * @param apiInvokeLog the apiInvokeLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiInvokeLog,
     * or with status {@code 400 (Bad Request)} if the apiInvokeLog is not valid,
     * or with status {@code 404 (Not Found)} if the apiInvokeLog is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiInvokeLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/api-invoke-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApiInvokeLog> partialUpdateApiInvokeLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApiInvokeLog apiInvokeLog
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApiInvokeLog partially : {}, {}", id, apiInvokeLog);
        if (apiInvokeLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiInvokeLog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiInvokeLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApiInvokeLog> result = apiInvokeLogService.partialUpdate(apiInvokeLog);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiInvokeLog.getId().toString())
        );
    }

    /**
     * {@code GET  /api-invoke-logs} : get all the apiInvokeLogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiInvokeLogs in body.
     */
    @GetMapping("/api-invoke-logs")
    public List<ApiInvokeLog> getAllApiInvokeLogs() {
        log.debug("REST request to get all ApiInvokeLogs");
        return apiInvokeLogService.findAll();
    }

    /**
     * {@code GET  /api-invoke-logs/:id} : get the "id" apiInvokeLog.
     *
     * @param id the id of the apiInvokeLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiInvokeLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-invoke-logs/{id}")
    public ResponseEntity<ApiInvokeLog> getApiInvokeLog(@PathVariable Long id) {
        log.debug("REST request to get ApiInvokeLog : {}", id);
        Optional<ApiInvokeLog> apiInvokeLog = apiInvokeLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiInvokeLog);
    }

    /**
     * {@code DELETE  /api-invoke-logs/:id} : delete the "id" apiInvokeLog.
     *
     * @param id the id of the apiInvokeLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-invoke-logs/{id}")
    public ResponseEntity<Void> deleteApiInvokeLog(@PathVariable Long id) {
        log.debug("REST request to delete ApiInvokeLog : {}", id);
        apiInvokeLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
