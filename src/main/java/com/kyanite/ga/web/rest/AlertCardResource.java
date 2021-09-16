package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.AlertCard;
import com.kyanite.ga.repository.AlertCardRepository;
import com.kyanite.ga.service.AlertCardService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.AlertCard}.
 */
@RestController
@RequestMapping("/api")
public class AlertCardResource {

    private final Logger log = LoggerFactory.getLogger(AlertCardResource.class);

    private static final String ENTITY_NAME = "alertCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertCardService alertCardService;

    private final AlertCardRepository alertCardRepository;

    public AlertCardResource(AlertCardService alertCardService, AlertCardRepository alertCardRepository) {
        this.alertCardService = alertCardService;
        this.alertCardRepository = alertCardRepository;
    }

    /**
     * {@code POST  /alert-cards} : Create a new alertCard.
     *
     * @param alertCard the alertCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertCard, or with status {@code 400 (Bad Request)} if the alertCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alert-cards")
    public ResponseEntity<AlertCard> createAlertCard(@RequestBody AlertCard alertCard) throws URISyntaxException {
        log.debug("REST request to save AlertCard : {}", alertCard);
        if (alertCard.getId() != null) {
            throw new BadRequestAlertException("A new alertCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertCard result = alertCardService.save(alertCard);
        return ResponseEntity
            .created(new URI("/api/alert-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alert-cards/:id} : Updates an existing alertCard.
     *
     * @param id the id of the alertCard to save.
     * @param alertCard the alertCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertCard,
     * or with status {@code 400 (Bad Request)} if the alertCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alert-cards/{id}")
    public ResponseEntity<AlertCard> updateAlertCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlertCard alertCard
    ) throws URISyntaxException {
        log.debug("REST request to update AlertCard : {}, {}", id, alertCard);
        if (alertCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alertCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlertCard result = alertCardService.save(alertCard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alertCard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alert-cards/:id} : Partial updates given fields of an existing alertCard, field will ignore if it is null
     *
     * @param id the id of the alertCard to save.
     * @param alertCard the alertCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertCard,
     * or with status {@code 400 (Bad Request)} if the alertCard is not valid,
     * or with status {@code 404 (Not Found)} if the alertCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the alertCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alert-cards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlertCard> partialUpdateAlertCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlertCard alertCard
    ) throws URISyntaxException {
        log.debug("REST request to partial update AlertCard partially : {}, {}", id, alertCard);
        if (alertCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alertCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlertCard> result = alertCardService.partialUpdate(alertCard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alertCard.getId().toString())
        );
    }

    /**
     * {@code GET  /alert-cards} : get all the alertCards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertCards in body.
     */
    @GetMapping("/alert-cards")
    public List<AlertCard> getAllAlertCards() {
        log.debug("REST request to get all AlertCards");
        return alertCardService.findAll();
    }

    /**
     * {@code GET  /alert-cards/:id} : get the "id" alertCard.
     *
     * @param id the id of the alertCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alert-cards/{id}")
    public ResponseEntity<AlertCard> getAlertCard(@PathVariable Long id) {
        log.debug("REST request to get AlertCard : {}", id);
        Optional<AlertCard> alertCard = alertCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertCard);
    }

    /**
     * {@code DELETE  /alert-cards/:id} : delete the "id" alertCard.
     *
     * @param id the id of the alertCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alert-cards/{id}")
    public ResponseEntity<Void> deleteAlertCard(@PathVariable Long id) {
        log.debug("REST request to delete AlertCard : {}", id);
        alertCardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
