package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.PrivateCardData;
import com.kyanite.ga.repository.PrivateCardDataRepository;
import com.kyanite.ga.service.PrivateCardDataService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.PrivateCardData}.
 */
@RestController
@RequestMapping("/api")
public class PrivateCardDataResource {

    private final Logger log = LoggerFactory.getLogger(PrivateCardDataResource.class);

    private static final String ENTITY_NAME = "privateCardData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrivateCardDataService privateCardDataService;

    private final PrivateCardDataRepository privateCardDataRepository;

    public PrivateCardDataResource(PrivateCardDataService privateCardDataService, PrivateCardDataRepository privateCardDataRepository) {
        this.privateCardDataService = privateCardDataService;
        this.privateCardDataRepository = privateCardDataRepository;
    }

    /**
     * {@code POST  /private-card-data} : Create a new privateCardData.
     *
     * @param privateCardData the privateCardData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new privateCardData, or with status {@code 400 (Bad Request)} if the privateCardData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/private-card-data")
    public ResponseEntity<PrivateCardData> createPrivateCardData(@RequestBody PrivateCardData privateCardData) throws URISyntaxException {
        log.debug("REST request to save PrivateCardData : {}", privateCardData);
        if (privateCardData.getId() != null) {
            throw new BadRequestAlertException("A new privateCardData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrivateCardData result = privateCardDataService.save(privateCardData);
        return ResponseEntity
            .created(new URI("/api/private-card-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /private-card-data/:id} : Updates an existing privateCardData.
     *
     * @param id the id of the privateCardData to save.
     * @param privateCardData the privateCardData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privateCardData,
     * or with status {@code 400 (Bad Request)} if the privateCardData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the privateCardData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/private-card-data/{id}")
    public ResponseEntity<PrivateCardData> updatePrivateCardData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrivateCardData privateCardData
    ) throws URISyntaxException {
        log.debug("REST request to update PrivateCardData : {}, {}", id, privateCardData);
        if (privateCardData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privateCardData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privateCardDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrivateCardData result = privateCardDataService.save(privateCardData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privateCardData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /private-card-data/:id} : Partial updates given fields of an existing privateCardData, field will ignore if it is null
     *
     * @param id the id of the privateCardData to save.
     * @param privateCardData the privateCardData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privateCardData,
     * or with status {@code 400 (Bad Request)} if the privateCardData is not valid,
     * or with status {@code 404 (Not Found)} if the privateCardData is not found,
     * or with status {@code 500 (Internal Server Error)} if the privateCardData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/private-card-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrivateCardData> partialUpdatePrivateCardData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrivateCardData privateCardData
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrivateCardData partially : {}, {}", id, privateCardData);
        if (privateCardData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privateCardData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privateCardDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrivateCardData> result = privateCardDataService.partialUpdate(privateCardData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privateCardData.getId().toString())
        );
    }

    /**
     * {@code GET  /private-card-data} : get all the privateCardData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of privateCardData in body.
     */
    @GetMapping("/private-card-data")
    public List<PrivateCardData> getAllPrivateCardData() {
        log.debug("REST request to get all PrivateCardData");
        return privateCardDataService.findAll();
    }

    /**
     * {@code GET  /private-card-data/:id} : get the "id" privateCardData.
     *
     * @param id the id of the privateCardData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the privateCardData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/private-card-data/{id}")
    public ResponseEntity<PrivateCardData> getPrivateCardData(@PathVariable Long id) {
        log.debug("REST request to get PrivateCardData : {}", id);
        Optional<PrivateCardData> privateCardData = privateCardDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(privateCardData);
    }

    /**
     * {@code DELETE  /private-card-data/:id} : delete the "id" privateCardData.
     *
     * @param id the id of the privateCardData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/private-card-data/{id}")
    public ResponseEntity<Void> deletePrivateCardData(@PathVariable Long id) {
        log.debug("REST request to delete PrivateCardData : {}", id);
        privateCardDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
