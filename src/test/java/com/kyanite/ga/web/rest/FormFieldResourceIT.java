package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.FormField;
import com.kyanite.ga.repository.FormFieldRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormFieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormFieldResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_LABLE = "AAAAAAAAAA";
    private static final String UPDATED_LABLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/form-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormFieldRepository formFieldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormFieldMockMvc;

    private FormField formField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormField createEntity(EntityManager em) {
        FormField formField = new FormField().name(DEFAULT_NAME).value(DEFAULT_VALUE).lable(DEFAULT_LABLE);
        return formField;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormField createUpdatedEntity(EntityManager em) {
        FormField formField = new FormField().name(UPDATED_NAME).value(UPDATED_VALUE).lable(UPDATED_LABLE);
        return formField;
    }

    @BeforeEach
    public void initTest() {
        formField = createEntity(em);
    }

    @Test
    @Transactional
    void createFormField() throws Exception {
        int databaseSizeBeforeCreate = formFieldRepository.findAll().size();
        // Create the FormField
        restFormFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formField)))
            .andExpect(status().isCreated());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeCreate + 1);
        FormField testFormField = formFieldList.get(formFieldList.size() - 1);
        assertThat(testFormField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormField.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testFormField.getLable()).isEqualTo(DEFAULT_LABLE);
    }

    @Test
    @Transactional
    void createFormFieldWithExistingId() throws Exception {
        // Create the FormField with an existing ID
        formField.setId(1L);

        int databaseSizeBeforeCreate = formFieldRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formField)))
            .andExpect(status().isBadRequest());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormFields() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        // Get all the formFieldList
        restFormFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formField.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].lable").value(hasItem(DEFAULT_LABLE)));
    }

    @Test
    @Transactional
    void getFormField() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        // Get the formField
        restFormFieldMockMvc
            .perform(get(ENTITY_API_URL_ID, formField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formField.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.lable").value(DEFAULT_LABLE));
    }

    @Test
    @Transactional
    void getNonExistingFormField() throws Exception {
        // Get the formField
        restFormFieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormField() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();

        // Update the formField
        FormField updatedFormField = formFieldRepository.findById(formField.getId()).get();
        // Disconnect from session so that the updates on updatedFormField are not directly saved in db
        em.detach(updatedFormField);
        updatedFormField.name(UPDATED_NAME).value(UPDATED_VALUE).lable(UPDATED_LABLE);

        restFormFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormField.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormField))
            )
            .andExpect(status().isOk());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
        FormField testFormField = formFieldList.get(formFieldList.size() - 1);
        assertThat(testFormField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormField.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFormField.getLable()).isEqualTo(UPDATED_LABLE);
    }

    @Test
    @Transactional
    void putNonExistingFormField() throws Exception {
        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();
        formField.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formField.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formField))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormField() throws Exception {
        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();
        formField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formField))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormField() throws Exception {
        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();
        formField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormFieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formField)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormFieldWithPatch() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();

        // Update the formField using partial update
        FormField partialUpdatedFormField = new FormField();
        partialUpdatedFormField.setId(formField.getId());

        partialUpdatedFormField.name(UPDATED_NAME).value(UPDATED_VALUE);

        restFormFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormField))
            )
            .andExpect(status().isOk());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
        FormField testFormField = formFieldList.get(formFieldList.size() - 1);
        assertThat(testFormField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormField.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFormField.getLable()).isEqualTo(DEFAULT_LABLE);
    }

    @Test
    @Transactional
    void fullUpdateFormFieldWithPatch() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();

        // Update the formField using partial update
        FormField partialUpdatedFormField = new FormField();
        partialUpdatedFormField.setId(formField.getId());

        partialUpdatedFormField.name(UPDATED_NAME).value(UPDATED_VALUE).lable(UPDATED_LABLE);

        restFormFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormField))
            )
            .andExpect(status().isOk());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
        FormField testFormField = formFieldList.get(formFieldList.size() - 1);
        assertThat(testFormField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormField.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFormField.getLable()).isEqualTo(UPDATED_LABLE);
    }

    @Test
    @Transactional
    void patchNonExistingFormField() throws Exception {
        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();
        formField.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formField))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormField() throws Exception {
        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();
        formField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formField))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormField() throws Exception {
        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();
        formField.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormFieldMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formField))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormField() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        int databaseSizeBeforeDelete = formFieldRepository.findAll().size();

        // Delete the formField
        restFormFieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, formField.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
