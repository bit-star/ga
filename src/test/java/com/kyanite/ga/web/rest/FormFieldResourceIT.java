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

    private static final String DEFAULT_FIELDNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELDNAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDDBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELDDBTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LABELNAME = "AAAAAAAAAA";
    private static final String UPDATED_LABELNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDLABEL = "AAAAAAAAAA";
    private static final String UPDATED_FIELDLABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILTABLE = "AAAAAAAAAA";
    private static final String UPDATED_DETAILTABLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW = false;
    private static final Boolean UPDATED_SHOW = true;

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
            .fieldname(DEFAULT_FIELDNAME)
            .value(DEFAULT_VALUE)
            .fielddbtype(DEFAULT_FIELDDBTYPE)
            .labelname(DEFAULT_LABELNAME)
            .fieldlabel(DEFAULT_FIELDLABEL)
            .detailtable(DEFAULT_DETAILTABLE)
            .show(DEFAULT_SHOW)
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
            .fieldname(UPDATED_FIELDNAME)
            .value(UPDATED_VALUE)
            .fielddbtype(UPDATED_FIELDDBTYPE)
            .labelname(UPDATED_LABELNAME)
            .fieldlabel(UPDATED_FIELDLABEL)
            .detailtable(UPDATED_DETAILTABLE)
            .show(UPDATED_SHOW)
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
        assertThat(testFormField.getFieldname()).isEqualTo(DEFAULT_FIELDNAME);
        assertThat(testFormField.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testFormField.getFielddbtype()).isEqualTo(DEFAULT_FIELDDBTYPE);
        assertThat(testFormField.getLabelname()).isEqualTo(DEFAULT_LABELNAME);
        assertThat(testFormField.getFieldlabel()).isEqualTo(DEFAULT_FIELDLABEL);
        assertThat(testFormField.getDetailtable()).isEqualTo(DEFAULT_DETAILTABLE);
        assertThat(testFormField.getShow()).isEqualTo(DEFAULT_SHOW);
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
            .andExpect(jsonPath("$.[*].fieldname").value(hasItem(DEFAULT_FIELDNAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].fielddbtype").value(hasItem(DEFAULT_FIELDDBTYPE)))
            .andExpect(jsonPath("$.[*].labelname").value(hasItem(DEFAULT_LABELNAME)))
            .andExpect(jsonPath("$.[*].fieldlabel").value(hasItem(DEFAULT_FIELDLABEL)))
            .andExpect(jsonPath("$.[*].detailtable").value(hasItem(DEFAULT_DETAILTABLE)))
            .andExpect(jsonPath("$.[*].show").value(hasItem(DEFAULT_SHOW.booleanValue())))
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
            .andExpect(jsonPath("$.fieldname").value(DEFAULT_FIELDNAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.fielddbtype").value(DEFAULT_FIELDDBTYPE))
            .andExpect(jsonPath("$.labelname").value(DEFAULT_LABELNAME))
            .andExpect(jsonPath("$.fieldlabel").value(DEFAULT_FIELDLABEL))
            .andExpect(jsonPath("$.detailtable").value(DEFAULT_DETAILTABLE))
            .andExpect(jsonPath("$.show").value(DEFAULT_SHOW.booleanValue()))
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
            .fieldname(UPDATED_FIELDNAME)
            .value(UPDATED_VALUE)
            .fielddbtype(UPDATED_FIELDDBTYPE)
            .labelname(UPDATED_LABELNAME)
            .fieldlabel(UPDATED_FIELDLABEL)
            .detailtable(UPDATED_DETAILTABLE)
            .show(UPDATED_SHOW)
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
        assertThat(testFormField.getFieldname()).isEqualTo(UPDATED_FIELDNAME);
        assertThat(testFormField.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFormField.getFielddbtype()).isEqualTo(UPDATED_FIELDDBTYPE);
        assertThat(testFormField.getLabelname()).isEqualTo(UPDATED_LABELNAME);
        assertThat(testFormField.getFieldlabel()).isEqualTo(UPDATED_FIELDLABEL);
        assertThat(testFormField.getDetailtable()).isEqualTo(UPDATED_DETAILTABLE);
        assertThat(testFormField.getShow()).isEqualTo(UPDATED_SHOW);
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

        partialUpdatedFormField.fieldname(UPDATED_FIELDNAME).value(UPDATED_VALUE).labelname(UPDATED_LABELNAME).orderNum(UPDATED_ORDER_NUM);

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
        assertThat(testFormField.getFieldname()).isEqualTo(UPDATED_FIELDNAME);
        assertThat(testFormField.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFormField.getFielddbtype()).isEqualTo(DEFAULT_FIELDDBTYPE);
        assertThat(testFormField.getLabelname()).isEqualTo(UPDATED_LABELNAME);
        assertThat(testFormField.getFieldlabel()).isEqualTo(DEFAULT_FIELDLABEL);
        assertThat(testFormField.getDetailtable()).isEqualTo(DEFAULT_DETAILTABLE);
        assertThat(testFormField.getShow()).isEqualTo(DEFAULT_SHOW);
        assertThat(testFormField.getOrderNum()).isEqualTo(UPDATED_ORDER_NUM);
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
            .fieldname(UPDATED_FIELDNAME)
            .value(UPDATED_VALUE)
            .fielddbtype(UPDATED_FIELDDBTYPE)
            .labelname(UPDATED_LABELNAME)
            .fieldlabel(UPDATED_FIELDLABEL)
            .detailtable(UPDATED_DETAILTABLE)
            .show(UPDATED_SHOW)
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
        assertThat(testFormField.getFieldname()).isEqualTo(UPDATED_FIELDNAME);
        assertThat(testFormField.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFormField.getFielddbtype()).isEqualTo(UPDATED_FIELDDBTYPE);
        assertThat(testFormField.getLabelname()).isEqualTo(UPDATED_LABELNAME);
        assertThat(testFormField.getFieldlabel()).isEqualTo(UPDATED_FIELDLABEL);
        assertThat(testFormField.getDetailtable()).isEqualTo(UPDATED_DETAILTABLE);
        assertThat(testFormField.getShow()).isEqualTo(UPDATED_SHOW);
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
