package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.FormField;
import com.kyanite.ga.repository.FormFieldRepository;
import com.kyanite.ga.service.FormFieldService;
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
 * REST controller for managing {@link com.kyanite.ga.domain.FormField}.
 */
@RestController
@RequestMapping("/api")
public class FormFieldResource {

    private final Logger log = LoggerFactory.getLogger(FormFieldResource.class);

    private static final String ENTITY_NAME = "formField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormFieldService formFieldService;

    private final FormFieldRepository formFieldRepository;

    public FormFieldResource(FormFieldService formFieldService, FormFieldRepository formFieldRepository) {
        this.formFieldService = formFieldService;
        this.formFieldRepository = formFieldRepository;
    }

    /**
     * {@code POST  /form-fields} : Create a new formField.
     *
     * @param formField the formField to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formField, or with status {@code 400 (Bad Request)} if the formField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-fields")
    public ResponseEntity<FormField> createFormField(@RequestBody FormField formField) throws URISyntaxException {
        log.debug("REST request to save FormField : {}", formField);
        if (formField.getId() != null) {
            throw new BadRequestAlertException("A new formField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormField result = formFieldService.save(formField);
        return ResponseEntity
            .created(new URI("/api/form-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /form-fields/:id} : Updates an existing formField.
     *
     * @param id the id of the formField to save.
     * @param formField the formField to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formField,
     * or with status {@code 400 (Bad Request)} if the formField is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formField couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-fields/{id}")
    public ResponseEntity<FormField> updateFormField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormField formField
    ) throws URISyntaxException {
        log.debug("REST request to update FormField : {}, {}", id, formField);
        if (formField.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formField.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormField result = formFieldService.save(formField);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formField.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /form-fields/:id} : Partial updates given fields of an existing formField, field will ignore if it is null
     *
     * @param id the id of the formField to save.
     * @param formField the formField to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formField,
     * or with status {@code 400 (Bad Request)} if the formField is not valid,
     * or with status {@code 404 (Not Found)} if the formField is not found,
     * or with status {@code 500 (Internal Server Error)} if the formField couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/form-fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormField> partialUpdateFormField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormField formField
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormField partially : {}, {}", id, formField);
        if (formField.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formField.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormField> result = formFieldService.partialUpdate(formField);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formField.getId().toString())
        );
    }

    /**
     * {@code GET  /form-fields} : get all the formFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formFields in body.
     */
    @GetMapping("/form-fields")
    public List<FormField> getAllFormFields() {
        log.debug("REST request to get all FormFields");
        return formFieldService.findAll();
    }

    /**
     * {@code GET  /form-fields/:id} : get the "id" formField.
     *
     * @param id the id of the formField to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formField, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-fields/{id}")
    public ResponseEntity<FormField> getFormField(@PathVariable Long id) {
        log.debug("REST request to get FormField : {}", id);
        Optional<FormField> formField = formFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formField);
    }

    /**
     * {@code DELETE  /form-fields/:id} : delete the "id" formField.
     *
     * @param id the id of the formField to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-fields/{id}")
    public ResponseEntity<Void> deleteFormField(@PathVariable Long id) {
        log.debug("REST request to delete FormField : {}", id);
        formFieldService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
