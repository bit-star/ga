package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.LinkSystem;
import com.kyanite.ga.repository.LinkSystemRepository;
import com.kyanite.ga.service.LinkSystemService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.LinkSystem}.
 */
@RestController
@RequestMapping("/api")
public class LinkSystemResource {

    private final Logger log = LoggerFactory.getLogger(LinkSystemResource.class);

    private static final String ENTITY_NAME = "linkSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkSystemService linkSystemService;

    private final LinkSystemRepository linkSystemRepository;

    public LinkSystemResource(LinkSystemService linkSystemService, LinkSystemRepository linkSystemRepository) {
        this.linkSystemService = linkSystemService;
        this.linkSystemRepository = linkSystemRepository;
    }

    /**
     * {@code POST  /link-systems} : Create a new linkSystem.
     *
     * @param linkSystem the linkSystem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linkSystem, or with status {@code 400 (Bad Request)} if the linkSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/link-systems")
    public ResponseEntity<LinkSystem> createLinkSystem(@RequestBody LinkSystem linkSystem) throws URISyntaxException {
        log.debug("REST request to save LinkSystem : {}", linkSystem);
        if (linkSystem.getId() != null) {
            throw new BadRequestAlertException("A new linkSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinkSystem result = linkSystemService.save(linkSystem);
        return ResponseEntity
            .created(new URI("/api/link-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /link-systems/:id} : Updates an existing linkSystem.
     *
     * @param id the id of the linkSystem to save.
     * @param linkSystem the linkSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkSystem,
     * or with status {@code 400 (Bad Request)} if the linkSystem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linkSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/link-systems/{id}")
    public ResponseEntity<LinkSystem> updateLinkSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LinkSystem linkSystem
    ) throws URISyntaxException {
        log.debug("REST request to update LinkSystem : {}, {}", id, linkSystem);
        if (linkSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LinkSystem result = linkSystemService.save(linkSystem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linkSystem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /link-systems/:id} : Partial updates given fields of an existing linkSystem, field will ignore if it is null
     *
     * @param id the id of the linkSystem to save.
     * @param linkSystem the linkSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkSystem,
     * or with status {@code 400 (Bad Request)} if the linkSystem is not valid,
     * or with status {@code 404 (Not Found)} if the linkSystem is not found,
     * or with status {@code 500 (Internal Server Error)} if the linkSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/link-systems/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LinkSystem> partialUpdateLinkSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LinkSystem linkSystem
    ) throws URISyntaxException {
        log.debug("REST request to partial update LinkSystem partially : {}, {}", id, linkSystem);
        if (linkSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LinkSystem> result = linkSystemService.partialUpdate(linkSystem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linkSystem.getId().toString())
        );
    }

    /**
     * {@code GET  /link-systems} : get all the linkSystems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linkSystems in body.
     */
    @GetMapping("/link-systems")
    public List<LinkSystem> getAllLinkSystems() {
        log.debug("REST request to get all LinkSystems");
        return linkSystemService.findAll();
    }

    /**
     * {@code GET  /link-systems/:id} : get the "id" linkSystem.
     *
     * @param id the id of the linkSystem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linkSystem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/link-systems/{id}")
    public ResponseEntity<LinkSystem> getLinkSystem(@PathVariable Long id) {
        log.debug("REST request to get LinkSystem : {}", id);
        Optional<LinkSystem> linkSystem = linkSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linkSystem);
    }

    /**
     * {@code DELETE  /link-systems/:id} : delete the "id" linkSystem.
     *
     * @param id the id of the linkSystem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/link-systems/{id}")
    public ResponseEntity<Void> deleteLinkSystem(@PathVariable Long id) {
        log.debug("REST request to delete LinkSystem : {}", id);
        linkSystemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
