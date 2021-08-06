package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.OperationResults;
import com.kyanite.ga.domain.enumeration.OperationType;
import com.kyanite.ga.repository.OperationResultsRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link OperationResultsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperationResultsResourceIT {

    private static final OperationType DEFAULT_OPERATION_TYPE = OperationType.Agree;
    private static final OperationType UPDATED_OPERATION_TYPE = OperationType.Refuse;

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/operation-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperationResultsRepository operationResultsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationResultsMockMvc;

    private OperationResults operationResults;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationResults createEntity(EntityManager em) {
        OperationResults operationResults = new OperationResults()
            .operationType(DEFAULT_OPERATION_TYPE)
            .time(DEFAULT_TIME)
            .text(DEFAULT_TEXT);
        return operationResults;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationResults createUpdatedEntity(EntityManager em) {
        OperationResults operationResults = new OperationResults()
            .operationType(UPDATED_OPERATION_TYPE)
            .time(UPDATED_TIME)
            .text(UPDATED_TEXT);
        return operationResults;
    }

    @BeforeEach
    public void initTest() {
        operationResults = createEntity(em);
    }

    @Test
    @Transactional
    void createOperationResults() throws Exception {
        int databaseSizeBeforeCreate = operationResultsRepository.findAll().size();
        // Create the OperationResults
        restOperationResultsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isCreated());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeCreate + 1);
        OperationResults testOperationResults = operationResultsList.get(operationResultsList.size() - 1);
        assertThat(testOperationResults.getOperationType()).isEqualTo(DEFAULT_OPERATION_TYPE);
        assertThat(testOperationResults.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testOperationResults.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    void createOperationResultsWithExistingId() throws Exception {
        // Create the OperationResults with an existing ID
        operationResults.setId(1L);

        int databaseSizeBeforeCreate = operationResultsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationResultsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperationResults() throws Exception {
        // Initialize the database
        operationResultsRepository.saveAndFlush(operationResults);

        // Get all the operationResultsList
        restOperationResultsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationResults.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationType").value(hasItem(DEFAULT_OPERATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

    @Test
    @Transactional
    void getOperationResults() throws Exception {
        // Initialize the database
        operationResultsRepository.saveAndFlush(operationResults);

        // Get the operationResults
        restOperationResultsMockMvc
            .perform(get(ENTITY_API_URL_ID, operationResults.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operationResults.getId().intValue()))
            .andExpect(jsonPath("$.operationType").value(DEFAULT_OPERATION_TYPE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingOperationResults() throws Exception {
        // Get the operationResults
        restOperationResultsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOperationResults() throws Exception {
        // Initialize the database
        operationResultsRepository.saveAndFlush(operationResults);

        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();

        // Update the operationResults
        OperationResults updatedOperationResults = operationResultsRepository.findById(operationResults.getId()).get();
        // Disconnect from session so that the updates on updatedOperationResults are not directly saved in db
        em.detach(updatedOperationResults);
        updatedOperationResults.operationType(UPDATED_OPERATION_TYPE).time(UPDATED_TIME).text(UPDATED_TEXT);

        restOperationResultsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOperationResults.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOperationResults))
            )
            .andExpect(status().isOk());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
        OperationResults testOperationResults = operationResultsList.get(operationResultsList.size() - 1);
        assertThat(testOperationResults.getOperationType()).isEqualTo(UPDATED_OPERATION_TYPE);
        assertThat(testOperationResults.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testOperationResults.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingOperationResults() throws Exception {
        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();
        operationResults.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationResultsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationResults.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperationResults() throws Exception {
        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();
        operationResults.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationResultsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperationResults() throws Exception {
        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();
        operationResults.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationResultsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperationResultsWithPatch() throws Exception {
        // Initialize the database
        operationResultsRepository.saveAndFlush(operationResults);

        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();

        // Update the operationResults using partial update
        OperationResults partialUpdatedOperationResults = new OperationResults();
        partialUpdatedOperationResults.setId(operationResults.getId());

        partialUpdatedOperationResults.time(UPDATED_TIME).text(UPDATED_TEXT);

        restOperationResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationResults.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationResults))
            )
            .andExpect(status().isOk());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
        OperationResults testOperationResults = operationResultsList.get(operationResultsList.size() - 1);
        assertThat(testOperationResults.getOperationType()).isEqualTo(DEFAULT_OPERATION_TYPE);
        assertThat(testOperationResults.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testOperationResults.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void fullUpdateOperationResultsWithPatch() throws Exception {
        // Initialize the database
        operationResultsRepository.saveAndFlush(operationResults);

        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();

        // Update the operationResults using partial update
        OperationResults partialUpdatedOperationResults = new OperationResults();
        partialUpdatedOperationResults.setId(operationResults.getId());

        partialUpdatedOperationResults.operationType(UPDATED_OPERATION_TYPE).time(UPDATED_TIME).text(UPDATED_TEXT);

        restOperationResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationResults.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationResults))
            )
            .andExpect(status().isOk());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
        OperationResults testOperationResults = operationResultsList.get(operationResultsList.size() - 1);
        assertThat(testOperationResults.getOperationType()).isEqualTo(UPDATED_OPERATION_TYPE);
        assertThat(testOperationResults.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testOperationResults.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingOperationResults() throws Exception {
        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();
        operationResults.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operationResults.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperationResults() throws Exception {
        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();
        operationResults.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationResultsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperationResults() throws Exception {
        int databaseSizeBeforeUpdate = operationResultsRepository.findAll().size();
        operationResults.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationResultsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationResults))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationResults in the database
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperationResults() throws Exception {
        // Initialize the database
        operationResultsRepository.saveAndFlush(operationResults);

        int databaseSizeBeforeDelete = operationResultsRepository.findAll().size();

        // Delete the operationResults
        restOperationResultsMockMvc
            .perform(delete(ENTITY_API_URL_ID, operationResults.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationResults> operationResultsList = operationResultsRepository.findAll();
        assertThat(operationResultsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
