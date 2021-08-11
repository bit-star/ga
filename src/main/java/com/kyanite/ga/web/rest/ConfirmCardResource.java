package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.ConfirmCard;
import com.kyanite.ga.repository.ConfirmCardRepository;
import com.kyanite.ga.service.ConfirmCardService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.ConfirmCard}.
 */
@RestController
@RequestMapping("/api")
public class ConfirmCardResource {

    private final Logger log = LoggerFactory.getLogger(ConfirmCardResource.class);

    private static final String ENTITY_NAME = "confirmCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfirmCardService confirmCardService;

    private final ConfirmCardRepository confirmCardRepository;

    public ConfirmCardResource(ConfirmCardService confirmCardService, ConfirmCardRepository confirmCardRepository) {
        this.confirmCardService = confirmCardService;
        this.confirmCardRepository = confirmCardRepository;
    }

    /**
     * {@code POST  /confirm-cards} : Create a new confirmCard.
     *
     * @param confirmCard the confirmCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new confirmCard, or with status {@code 400 (Bad Request)} if the confirmCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/confirm-cards")
    public ResponseEntity<ConfirmCard> createConfirmCard(@RequestBody ConfirmCard confirmCard) throws URISyntaxException {
        log.debug("REST request to save ConfirmCard : {}", confirmCard);
        if (confirmCard.getId() != null) {
            throw new BadRequestAlertException("A new confirmCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfirmCard result = confirmCardService.save(confirmCard);
        return ResponseEntity
            .created(new URI("/api/confirm-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /confirm-cards/:id} : Updates an existing confirmCard.
     *
     * @param id the id of the confirmCard to save.
     * @param confirmCard the confirmCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confirmCard,
     * or with status {@code 400 (Bad Request)} if the confirmCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the confirmCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/confirm-cards/{id}")
    public ResponseEntity<ConfirmCard> updateConfirmCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfirmCard confirmCard
    ) throws URISyntaxException {
        log.debug("REST request to update ConfirmCard : {}, {}", id, confirmCard);
        if (confirmCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confirmCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confirmCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfirmCard result = confirmCardService.save(confirmCard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, confirmCard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /confirm-cards/:id} : Partial updates given fields of an existing confirmCard, field will ignore if it is null
     *
     * @param id the id of the confirmCard to save.
     * @param confirmCard the confirmCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confirmCard,
     * or with status {@code 400 (Bad Request)} if the confirmCard is not valid,
     * or with status {@code 404 (Not Found)} if the confirmCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the confirmCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/confirm-cards/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ConfirmCard> partialUpdateConfirmCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfirmCard confirmCard
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConfirmCard partially : {}, {}", id, confirmCard);
        if (confirmCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confirmCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confirmCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfirmCard> result = confirmCardService.partialUpdate(confirmCard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, confirmCard.getId().toString())
        );
    }

    /**
     * {@code GET  /confirm-cards} : get all the confirmCards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of confirmCards in body.
     */
    @GetMapping("/confirm-cards")
    public List<ConfirmCard> getAllConfirmCards() {
        log.debug("REST request to get all ConfirmCards");
        return confirmCardService.findAll();
    }

    /**
     * {@code GET  /confirm-cards/:id} : get the "id" confirmCard.
     *
     * @param id the id of the confirmCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the confirmCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/confirm-cards/{id}")
    public ResponseEntity<ConfirmCard> getConfirmCard(@PathVariable Long id) {
        log.debug("REST request to get ConfirmCard : {}", id);
        Optional<ConfirmCard> confirmCard = confirmCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(confirmCard);
    }

    /**
     * {@code DELETE  /confirm-cards/:id} : delete the "id" confirmCard.
     *
     * @param id the id of the confirmCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/confirm-cards/{id}")
    public ResponseEntity<Void> deleteConfirmCard(@PathVariable Long id) {
        log.debug("REST request to delete ConfirmCard : {}", id);
        confirmCardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
