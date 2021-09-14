package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.Topboxes;
import com.kyanite.ga.repository.TopboxesRepository;
import com.kyanite.ga.service.TopboxesService;
import com.kyanite.ga.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kyanite.ga.domain.Topboxes}.
 */
@RestController
@RequestMapping("/api")
public class TopboxesResource {

    private final Logger log = LoggerFactory.getLogger(TopboxesResource.class);

    private static final String ENTITY_NAME = "topboxes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopboxesService topboxesService;

    private final TopboxesRepository topboxesRepository;

    public TopboxesResource(TopboxesService topboxesService, TopboxesRepository topboxesRepository) {
        this.topboxesService = topboxesService;
        this.topboxesRepository = topboxesRepository;
    }

    /**
     * {@code POST  /topboxes} : Create a new topboxes.
     *
     * @param topboxes the topboxes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topboxes, or with status {@code 400 (Bad Request)} if the topboxes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topboxes")
    public ResponseEntity<Topboxes> createTopboxes(@RequestBody Topboxes topboxes) throws URISyntaxException {
        log.debug("REST request to save Topboxes : {}", topboxes);
        if (topboxes.getId() != null) {
            throw new BadRequestAlertException("A new topboxes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Topboxes result = topboxesService.save(topboxes);
        return ResponseEntity
            .created(new URI("/api/topboxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /topboxes/:id} : Updates an existing topboxes.
     *
     * @param id the id of the topboxes to save.
     * @param topboxes the topboxes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topboxes,
     * or with status {@code 400 (Bad Request)} if the topboxes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topboxes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topboxes/{id}")
    public ResponseEntity<Topboxes> updateTopboxes(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Topboxes topboxes
    ) throws URISyntaxException {
        log.debug("REST request to update Topboxes : {}, {}", id, topboxes);
        if (topboxes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topboxes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!topboxesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Topboxes result = topboxesService.save(topboxes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topboxes.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /topboxes/:id} : Partial updates given fields of an existing topboxes, field will ignore if it is null
     *
     * @param id the id of the topboxes to save.
     * @param topboxes the topboxes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topboxes,
     * or with status {@code 400 (Bad Request)} if the topboxes is not valid,
     * or with status {@code 404 (Not Found)} if the topboxes is not found,
     * or with status {@code 500 (Internal Server Error)} if the topboxes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/topboxes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Topboxes> partialUpdateTopboxes(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Topboxes topboxes
    ) throws URISyntaxException {
        log.debug("REST request to partial update Topboxes partially : {}, {}", id, topboxes);
        if (topboxes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topboxes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!topboxesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Topboxes> result = topboxesService.partialUpdate(topboxes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topboxes.getId())
        );
    }

    /**
     * {@code GET  /topboxes} : get all the topboxes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topboxes in body.
     */
    @GetMapping("/topboxes")
    public List<Topboxes> getAllTopboxes(@RequestParam(required = false) String filter) {
        if ("publiccarddata-is-null".equals(filter)) {
            log.debug("REST request to get all Topboxess where publicCardData is null");
            return topboxesService.findAllWherePublicCardDataIsNull();
        }
        log.debug("REST request to get all Topboxes");
        return topboxesService.findAll();
    }

    /**
     * {@code GET  /topboxes/:id} : get the "id" topboxes.
     *
     * @param id the id of the topboxes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topboxes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topboxes/{id}")
    public ResponseEntity<Topboxes> getTopboxes(@PathVariable String id) {
        log.debug("REST request to get Topboxes : {}", id);
        Optional<Topboxes> topboxes = topboxesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topboxes);
    }

    /**
     * {@code DELETE  /topboxes/:id} : delete the "id" topboxes.
     *
     * @param id the id of the topboxes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topboxes/{id}")
    public ResponseEntity<Void> deleteTopboxes(@PathVariable String id) {
        log.debug("REST request to delete Topboxes : {}", id);
        topboxesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
