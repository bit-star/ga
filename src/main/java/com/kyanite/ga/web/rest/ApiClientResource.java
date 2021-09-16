package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.ApiClient;
import com.kyanite.ga.repository.ApiClientRepository;
import com.kyanite.ga.service.ApiClientService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.ApiClient}.
 */
@RestController
@RequestMapping("/api")
public class ApiClientResource {

    private final Logger log = LoggerFactory.getLogger(ApiClientResource.class);

    private static final String ENTITY_NAME = "apiClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiClientService apiClientService;

    private final ApiClientRepository apiClientRepository;

    public ApiClientResource(ApiClientService apiClientService, ApiClientRepository apiClientRepository) {
        this.apiClientService = apiClientService;
        this.apiClientRepository = apiClientRepository;
    }

    /**
     * {@code POST  /api-clients} : Create a new apiClient.
     *
     * @param apiClient the apiClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiClient, or with status {@code 400 (Bad Request)} if the apiClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-clients")
    public ResponseEntity<ApiClient> createApiClient(@RequestBody ApiClient apiClient) throws URISyntaxException {
        log.debug("REST request to save ApiClient : {}", apiClient);
        if (apiClient.getId() != null) {
            throw new BadRequestAlertException("A new apiClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiClient result = apiClientService.save(apiClient);
        return ResponseEntity
            .created(new URI("/api/api-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-clients/:id} : Updates an existing apiClient.
     *
     * @param id the id of the apiClient to save.
     * @param apiClient the apiClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiClient,
     * or with status {@code 400 (Bad Request)} if the apiClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-clients/{id}")
    public ResponseEntity<ApiClient> updateApiClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApiClient apiClient
    ) throws URISyntaxException {
        log.debug("REST request to update ApiClient : {}, {}", id, apiClient);
        if (apiClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApiClient result = apiClientService.save(apiClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /api-clients/:id} : Partial updates given fields of an existing apiClient, field will ignore if it is null
     *
     * @param id the id of the apiClient to save.
     * @param apiClient the apiClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiClient,
     * or with status {@code 400 (Bad Request)} if the apiClient is not valid,
     * or with status {@code 404 (Not Found)} if the apiClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/api-clients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApiClient> partialUpdateApiClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApiClient apiClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApiClient partially : {}, {}", id, apiClient);
        if (apiClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApiClient> result = apiClientService.partialUpdate(apiClient);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiClient.getId().toString())
        );
    }

    /**
     * {@code GET  /api-clients} : get all the apiClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiClients in body.
     */
    @GetMapping("/api-clients")
    public List<ApiClient> getAllApiClients() {
        log.debug("REST request to get all ApiClients");
        return apiClientService.findAll();
    }

    /**
     * {@code GET  /api-clients/:id} : get the "id" apiClient.
     *
     * @param id the id of the apiClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-clients/{id}")
    public ResponseEntity<ApiClient> getApiClient(@PathVariable Long id) {
        log.debug("REST request to get ApiClient : {}", id);
        Optional<ApiClient> apiClient = apiClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiClient);
    }

    /**
     * {@code DELETE  /api-clients/:id} : delete the "id" apiClient.
     *
     * @param id the id of the apiClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-clients/{id}")
    public ResponseEntity<Void> deleteApiClient(@PathVariable Long id) {
        log.debug("REST request to delete ApiClient : {}", id);
        apiClientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
