package com.kyanite.ga.service;

import com.kyanite.ga.domain.FormField;
import com.kyanite.ga.repository.FormFieldRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormField}.
 */
@Service
@Transactional
public class FormFieldService {

    private final Logger log = LoggerFactory.getLogger(FormFieldService.class);

    private final FormFieldRepository formFieldRepository;

    public FormFieldService(FormFieldRepository formFieldRepository) {
        this.formFieldRepository = formFieldRepository;
    }

    /**
     * Save a formField.
     *
     * @param formField the entity to save.
     * @return the persisted entity.
     */
    public FormField save(FormField formField) {
        log.debug("Request to save FormField : {}", formField);
        return formFieldRepository.save(formField);
    }

    /**
     * Partially update a formField.
     *
     * @param formField the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FormField> partialUpdate(FormField formField) {
        log.debug("Request to partially update FormField : {}", formField);

        return formFieldRepository
            .findById(formField.getId())
            .map(
                existingFormField -> {
                    if (formField.getName() != null) {
                        existingFormField.setName(formField.getName());
                    }
                    if (formField.getValue() != null) {
                        existingFormField.setValue(formField.getValue());
                    }
                    if (formField.getLable() != null) {
                        existingFormField.setLable(formField.getLable());
                    }

                    return existingFormField;
                }
            )
            .map(formFieldRepository::save);
    }

    /**
     * Get all the formFields.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FormField> findAll() {
        log.debug("Request to get all FormFields");
        return formFieldRepository.findAll();
    }

    /**
     * Get one formField by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FormField> findOne(Long id) {
        log.debug("Request to get FormField : {}", id);
        return formFieldRepository.findById(id);
    }

    /**
     * Delete the formField by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FormField : {}", id);
        formFieldRepository.deleteById(id);
    }
}
