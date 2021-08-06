package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.WorkflowInstance;
import com.kyanite.ga.repository.WorkflowInstanceRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link WorkflowInstanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkflowInstanceResourceIT {

    private static final String DEFAULT_FORM = "AAAAAAAAAA";
    private static final String UPDATED_FORM = "BBBBBBBBBB";

    private static final String DEFAULT_DD_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_DD_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/workflow-instances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkflowInstanceMockMvc;

    private WorkflowInstance workflowInstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkflowInstance createEntity(EntityManager em) {
        WorkflowInstance workflowInstance = new WorkflowInstance().form(DEFAULT_FORM).ddCardId(DEFAULT_DD_CARD_ID).title(DEFAULT_TITLE);
        return workflowInstance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkflowInstance createUpdatedEntity(EntityManager em) {
        WorkflowInstance workflowInstance = new WorkflowInstance().form(UPDATED_FORM).ddCardId(UPDATED_DD_CARD_ID).title(UPDATED_TITLE);
        return workflowInstance;
    }

    @BeforeEach
    public void initTest() {
        workflowInstance = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkflowInstance() throws Exception {
        int databaseSizeBeforeCreate = workflowInstanceRepository.findAll().size();
        // Create the WorkflowInstance
        restWorkflowInstanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isCreated());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkflowInstance testWorkflowInstance = workflowInstanceList.get(workflowInstanceList.size() - 1);
        assertThat(testWorkflowInstance.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testWorkflowInstance.getDdCardId()).isEqualTo(DEFAULT_DD_CARD_ID);
        assertThat(testWorkflowInstance.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createWorkflowInstanceWithExistingId() throws Exception {
        // Create the WorkflowInstance with an existing ID
        workflowInstance.setId(1L);

        int databaseSizeBeforeCreate = workflowInstanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkflowInstanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkflowInstances() throws Exception {
        // Initialize the database
        workflowInstanceRepository.saveAndFlush(workflowInstance);

        // Get all the workflowInstanceList
        restWorkflowInstanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workflowInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].form").value(hasItem(DEFAULT_FORM.toString())))
            .andExpect(jsonPath("$.[*].ddCardId").value(hasItem(DEFAULT_DD_CARD_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getWorkflowInstance() throws Exception {
        // Initialize the database
        workflowInstanceRepository.saveAndFlush(workflowInstance);

        // Get the workflowInstance
        restWorkflowInstanceMockMvc
            .perform(get(ENTITY_API_URL_ID, workflowInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workflowInstance.getId().intValue()))
            .andExpect(jsonPath("$.form").value(DEFAULT_FORM.toString()))
            .andExpect(jsonPath("$.ddCardId").value(DEFAULT_DD_CARD_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getNonExistingWorkflowInstance() throws Exception {
        // Get the workflowInstance
        restWorkflowInstanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkflowInstance() throws Exception {
        // Initialize the database
        workflowInstanceRepository.saveAndFlush(workflowInstance);

        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();

        // Update the workflowInstance
        WorkflowInstance updatedWorkflowInstance = workflowInstanceRepository.findById(workflowInstance.getId()).get();
        // Disconnect from session so that the updates on updatedWorkflowInstance are not directly saved in db
        em.detach(updatedWorkflowInstance);
        updatedWorkflowInstance.form(UPDATED_FORM).ddCardId(UPDATED_DD_CARD_ID).title(UPDATED_TITLE);

        restWorkflowInstanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkflowInstance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkflowInstance))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
        WorkflowInstance testWorkflowInstance = workflowInstanceList.get(workflowInstanceList.size() - 1);
        assertThat(testWorkflowInstance.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testWorkflowInstance.getDdCardId()).isEqualTo(UPDATED_DD_CARD_ID);
        assertThat(testWorkflowInstance.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingWorkflowInstance() throws Exception {
        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();
        workflowInstance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowInstanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workflowInstance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkflowInstance() throws Exception {
        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();
        workflowInstance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowInstanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkflowInstance() throws Exception {
        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();
        workflowInstance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowInstanceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkflowInstanceWithPatch() throws Exception {
        // Initialize the database
        workflowInstanceRepository.saveAndFlush(workflowInstance);

        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();

        // Update the workflowInstance using partial update
        WorkflowInstance partialUpdatedWorkflowInstance = new WorkflowInstance();
        partialUpdatedWorkflowInstance.setId(workflowInstance.getId());

        partialUpdatedWorkflowInstance.title(UPDATED_TITLE);

        restWorkflowInstanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflowInstance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflowInstance))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
        WorkflowInstance testWorkflowInstance = workflowInstanceList.get(workflowInstanceList.size() - 1);
        assertThat(testWorkflowInstance.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testWorkflowInstance.getDdCardId()).isEqualTo(DEFAULT_DD_CARD_ID);
        assertThat(testWorkflowInstance.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateWorkflowInstanceWithPatch() throws Exception {
        // Initialize the database
        workflowInstanceRepository.saveAndFlush(workflowInstance);

        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();

        // Update the workflowInstance using partial update
        WorkflowInstance partialUpdatedWorkflowInstance = new WorkflowInstance();
        partialUpdatedWorkflowInstance.setId(workflowInstance.getId());

        partialUpdatedWorkflowInstance.form(UPDATED_FORM).ddCardId(UPDATED_DD_CARD_ID).title(UPDATED_TITLE);

        restWorkflowInstanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflowInstance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflowInstance))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
        WorkflowInstance testWorkflowInstance = workflowInstanceList.get(workflowInstanceList.size() - 1);
        assertThat(testWorkflowInstance.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testWorkflowInstance.getDdCardId()).isEqualTo(UPDATED_DD_CARD_ID);
        assertThat(testWorkflowInstance.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingWorkflowInstance() throws Exception {
        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();
        workflowInstance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowInstanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workflowInstance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkflowInstance() throws Exception {
        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();
        workflowInstance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowInstanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkflowInstance() throws Exception {
        int databaseSizeBeforeUpdate = workflowInstanceRepository.findAll().size();
        workflowInstance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowInstanceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowInstance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkflowInstance in the database
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkflowInstance() throws Exception {
        // Initialize the database
        workflowInstanceRepository.saveAndFlush(workflowInstance);

        int databaseSizeBeforeDelete = workflowInstanceRepository.findAll().size();

        // Delete the workflowInstance
        restWorkflowInstanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, workflowInstance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkflowInstance> workflowInstanceList = workflowInstanceRepository.findAll();
        assertThat(workflowInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
