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

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OA_ID = "AAAAAAAAAA";
    private static final String UPDATED_OA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDDBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELDDBTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILTABLE = "AAAAAAAAAA";
    private static final String UPDATED_DETAILTABLE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW = false;
    private static final Boolean UPDATED_SHOW = true;

    private static final Boolean DEFAULT_IS_CARD_FIELD = false;
    private static final Boolean UPDATED_IS_CARD_FIELD = true;

    private static final Boolean DEFAULT_IS_OA_FIELD = false;
    private static final Boolean UPDATED_IS_OA_FIELD = true;

    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final Boolean UPDATED_IS_PRIVATE = true;

    private static final Integer DEFAULT_ORDER_NUM = 1;
    private static final Integer UPDATED_ORDER_NUM = 2;

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
        FormField formField = new FormField()
            .fieldName(DEFAULT_FIELD_NAME)
            .oaId(DEFAULT_OA_ID)
            .fielddbtype(DEFAULT_FIELDDBTYPE)
            .labelName(DEFAULT_LABEL_NAME)
            .detailtable(DEFAULT_DETAILTABLE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .show(DEFAULT_SHOW)
            .isCardField(DEFAULT_IS_CARD_FIELD)
            .isOaField(DEFAULT_IS_OA_FIELD)
            .isPrivate(DEFAULT_IS_PRIVATE)
            .orderNum(DEFAULT_ORDER_NUM);
        return formField;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormField createUpdatedEntity(EntityManager em) {
        FormField formField = new FormField()
            .fieldName(UPDATED_FIELD_NAME)
            .oaId(UPDATED_OA_ID)
            .fielddbtype(UPDATED_FIELDDBTYPE)
            .labelName(UPDATED_LABEL_NAME)
            .detailtable(UPDATED_DETAILTABLE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .show(UPDATED_SHOW)
            .isCardField(UPDATED_IS_CARD_FIELD)
            .isOaField(UPDATED_IS_OA_FIELD)
            .isPrivate(UPDATED_IS_PRIVATE)
            .orderNum(UPDATED_ORDER_NUM);
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
        assertThat(testFormField.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testFormField.getOaId()).isEqualTo(DEFAULT_OA_ID);
        assertThat(testFormField.getFielddbtype()).isEqualTo(DEFAULT_FIELDDBTYPE);
        assertThat(testFormField.getLabelName()).isEqualTo(DEFAULT_LABEL_NAME);
        assertThat(testFormField.getDetailtable()).isEqualTo(DEFAULT_DETAILTABLE);
        assertThat(testFormField.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testFormField.getShow()).isEqualTo(DEFAULT_SHOW);
        assertThat(testFormField.getIsCardField()).isEqualTo(DEFAULT_IS_CARD_FIELD);
        assertThat(testFormField.getIsOaField()).isEqualTo(DEFAULT_IS_OA_FIELD);
        assertThat(testFormField.getIsPrivate()).isEqualTo(DEFAULT_IS_PRIVATE);
        assertThat(testFormField.getOrderNum()).isEqualTo(DEFAULT_ORDER_NUM);
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
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].oaId").value(hasItem(DEFAULT_OA_ID)))
            .andExpect(jsonPath("$.[*].fielddbtype").value(hasItem(DEFAULT_FIELDDBTYPE)))
            .andExpect(jsonPath("$.[*].labelName").value(hasItem(DEFAULT_LABEL_NAME)))
            .andExpect(jsonPath("$.[*].detailtable").value(hasItem(DEFAULT_DETAILTABLE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].show").value(hasItem(DEFAULT_SHOW.booleanValue())))
            .andExpect(jsonPath("$.[*].isCardField").value(hasItem(DEFAULT_IS_CARD_FIELD.booleanValue())))
            .andExpect(jsonPath("$.[*].isOaField").value(hasItem(DEFAULT_IS_OA_FIELD.booleanValue())))
            .andExpect(jsonPath("$.[*].isPrivate").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].orderNum").value(hasItem(DEFAULT_ORDER_NUM)));
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
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.oaId").value(DEFAULT_OA_ID))
            .andExpect(jsonPath("$.fielddbtype").value(DEFAULT_FIELDDBTYPE))
            .andExpect(jsonPath("$.labelName").value(DEFAULT_LABEL_NAME))
            .andExpect(jsonPath("$.detailtable").value(DEFAULT_DETAILTABLE))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.show").value(DEFAULT_SHOW.booleanValue()))
            .andExpect(jsonPath("$.isCardField").value(DEFAULT_IS_CARD_FIELD.booleanValue()))
            .andExpect(jsonPath("$.isOaField").value(DEFAULT_IS_OA_FIELD.booleanValue()))
            .andExpect(jsonPath("$.isPrivate").value(DEFAULT_IS_PRIVATE.booleanValue()))
            .andExpect(jsonPath("$.orderNum").value(DEFAULT_ORDER_NUM));
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
        updatedFormField
            .fieldName(UPDATED_FIELD_NAME)
            .oaId(UPDATED_OA_ID)
            .fielddbtype(UPDATED_FIELDDBTYPE)
            .labelName(UPDATED_LABEL_NAME)
            .detailtable(UPDATED_DETAILTABLE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .show(UPDATED_SHOW)
            .isCardField(UPDATED_IS_CARD_FIELD)
            .isOaField(UPDATED_IS_OA_FIELD)
            .isPrivate(UPDATED_IS_PRIVATE)
            .orderNum(UPDATED_ORDER_NUM);

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
        assertThat(testFormField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testFormField.getOaId()).isEqualTo(UPDATED_OA_ID);
        assertThat(testFormField.getFielddbtype()).isEqualTo(UPDATED_FIELDDBTYPE);
        assertThat(testFormField.getLabelName()).isEqualTo(UPDATED_LABEL_NAME);
        assertThat(testFormField.getDetailtable()).isEqualTo(UPDATED_DETAILTABLE);
        assertThat(testFormField.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testFormField.getShow()).isEqualTo(UPDATED_SHOW);
        assertThat(testFormField.getIsCardField()).isEqualTo(UPDATED_IS_CARD_FIELD);
        assertThat(testFormField.getIsOaField()).isEqualTo(UPDATED_IS_OA_FIELD);
        assertThat(testFormField.getIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
        assertThat(testFormField.getOrderNum()).isEqualTo(UPDATED_ORDER_NUM);
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

        partialUpdatedFormField
            .fieldName(UPDATED_FIELD_NAME)
            .oaId(UPDATED_OA_ID)
            .labelName(UPDATED_LABEL_NAME)
            .isCardField(UPDATED_IS_CARD_FIELD)
            .isPrivate(UPDATED_IS_PRIVATE);

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
        assertThat(testFormField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testFormField.getOaId()).isEqualTo(UPDATED_OA_ID);
        assertThat(testFormField.getFielddbtype()).isEqualTo(DEFAULT_FIELDDBTYPE);
        assertThat(testFormField.getLabelName()).isEqualTo(UPDATED_LABEL_NAME);
        assertThat(testFormField.getDetailtable()).isEqualTo(DEFAULT_DETAILTABLE);
        assertThat(testFormField.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testFormField.getShow()).isEqualTo(DEFAULT_SHOW);
        assertThat(testFormField.getIsCardField()).isEqualTo(UPDATED_IS_CARD_FIELD);
        assertThat(testFormField.getIsOaField()).isEqualTo(DEFAULT_IS_OA_FIELD);
        assertThat(testFormField.getIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
        assertThat(testFormField.getOrderNum()).isEqualTo(DEFAULT_ORDER_NUM);
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

        partialUpdatedFormField
            .fieldName(UPDATED_FIELD_NAME)
            .oaId(UPDATED_OA_ID)
            .fielddbtype(UPDATED_FIELDDBTYPE)
            .labelName(UPDATED_LABEL_NAME)
            .detailtable(UPDATED_DETAILTABLE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .show(UPDATED_SHOW)
            .isCardField(UPDATED_IS_CARD_FIELD)
            .isOaField(UPDATED_IS_OA_FIELD)
            .isPrivate(UPDATED_IS_PRIVATE)
            .orderNum(UPDATED_ORDER_NUM);

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
        assertThat(testFormField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testFormField.getOaId()).isEqualTo(UPDATED_OA_ID);
        assertThat(testFormField.getFielddbtype()).isEqualTo(UPDATED_FIELDDBTYPE);
        assertThat(testFormField.getLabelName()).isEqualTo(UPDATED_LABEL_NAME);
        assertThat(testFormField.getDetailtable()).isEqualTo(UPDATED_DETAILTABLE);
        assertThat(testFormField.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testFormField.getShow()).isEqualTo(UPDATED_SHOW);
        assertThat(testFormField.getIsCardField()).isEqualTo(UPDATED_IS_CARD_FIELD);
        assertThat(testFormField.getIsOaField()).isEqualTo(UPDATED_IS_OA_FIELD);
        assertThat(testFormField.getIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
        assertThat(testFormField.getOrderNum()).isEqualTo(UPDATED_ORDER_NUM);
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
