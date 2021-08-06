package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.PublicCardData;
import com.kyanite.ga.repository.PublicCardDataRepository;
import com.kyanite.ga.service.PublicCardDataService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.PublicCardData}.
 */
@RestController
@RequestMapping("/api")
public class PublicCardDataResource {

    private final Logger log = LoggerFactory.getLogger(PublicCardDataResource.class);

    private static final String ENTITY_NAME = "publicCardData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicCardDataService publicCardDataService;

    private final PublicCardDataRepository publicCardDataRepository;

    public PublicCardDataResource(PublicCardDataService publicCardDataService, PublicCardDataRepository publicCardDataRepository) {
        this.publicCardDataService = publicCardDataService;
        this.publicCardDataRepository = publicCardDataRepository;
    }

    /**
     * {@code POST  /public-card-data} : Create a new publicCardData.
     *
     * @param publicCardData the publicCardData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicCardData, or with status {@code 400 (Bad Request)} if the publicCardData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-card-data")
    public ResponseEntity<PublicCardData> createPublicCardData(@RequestBody PublicCardData publicCardData) throws URISyntaxException {
        log.debug("REST request to save PublicCardData : {}", publicCardData);
        if (publicCardData.getId() != null) {
            throw new BadRequestAlertException("A new publicCardData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicCardData result = publicCardDataService.save(publicCardData);
        return ResponseEntity
            .created(new URI("/api/public-card-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-card-data/:id} : Updates an existing publicCardData.
     *
     * @param id the id of the publicCardData to save.
     * @param publicCardData the publicCardData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicCardData,
     * or with status {@code 400 (Bad Request)} if the publicCardData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicCardData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-card-data/{id}")
    public ResponseEntity<PublicCardData> updatePublicCardData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicCardData publicCardData
    ) throws URISyntaxException {
        log.debug("REST request to update PublicCardData : {}, {}", id, publicCardData);
        if (publicCardData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicCardData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicCardDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PublicCardData result = publicCardDataService.save(publicCardData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicCardData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /public-card-data/:id} : Partial updates given fields of an existing publicCardData, field will ignore if it is null
     *
     * @param id the id of the publicCardData to save.
     * @param publicCardData the publicCardData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicCardData,
     * or with status {@code 400 (Bad Request)} if the publicCardData is not valid,
     * or with status {@code 404 (Not Found)} if the publicCardData is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicCardData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/public-card-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PublicCardData> partialUpdatePublicCardData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicCardData publicCardData
    ) throws URISyntaxException {
        log.debug("REST request to partial update PublicCardData partially : {}, {}", id, publicCardData);
        if (publicCardData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicCardData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicCardDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicCardData> result = publicCardDataService.partialUpdate(publicCardData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicCardData.getId().toString())
        );
    }

    /**
     * {@code GET  /public-card-data} : get all the publicCardData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicCardData in body.
     */
    @GetMapping("/public-card-data")
    public List<PublicCardData> getAllPublicCardData() {
        log.debug("REST request to get all PublicCardData");
        return publicCardDataService.findAll();
    }

    /**
     * {@code GET  /public-card-data/:id} : get the "id" publicCardData.
     *
     * @param id the id of the publicCardData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicCardData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-card-data/{id}")
    public ResponseEntity<PublicCardData> getPublicCardData(@PathVariable Long id) {
        log.debug("REST request to get PublicCardData : {}", id);
        Optional<PublicCardData> publicCardData = publicCardDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicCardData);
    }

    /**
     * {@code DELETE  /public-card-data/:id} : delete the "id" publicCardData.
     *
     * @param id the id of the publicCardData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-card-data/{id}")
    public ResponseEntity<Void> deletePublicCardData(@PathVariable Long id) {
        log.debug("REST request to delete PublicCardData : {}", id);
        publicCardDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
