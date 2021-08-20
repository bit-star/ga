package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.PrivateCardData;
import com.kyanite.ga.repository.PrivateCardDataRepository;
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
 * Integration tests for the {@link PrivateCardDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrivateCardDataResourceIT {

    private static final Boolean DEFAULT_AGREE = false;
    private static final Boolean UPDATED_AGREE = true;

    private static final String DEFAULT_FINISH = "AAAAAAAAAA";
    private static final String UPDATED_FINISH = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY_ME = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY_ME = "BBBBBBBBBB";

    private static final String DEFAULT_VARIABLES = "AAAAAAAAAA";
    private static final String UPDATED_VARIABLES = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/private-card-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrivateCardDataRepository privateCardDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrivateCardDataMockMvc;

    private PrivateCardData privateCardData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrivateCardData createEntity(EntityManager em) {
        PrivateCardData privateCardData = new PrivateCardData()
            .agree(DEFAULT_AGREE)
            .finish(DEFAULT_FINISH)
            .authority(DEFAULT_AUTHORITY)
            .createdByMe(DEFAULT_CREATED_BY_ME)
            .variables(DEFAULT_VARIABLES)
            .updateTime(DEFAULT_UPDATE_TIME);
        return privateCardData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrivateCardData createUpdatedEntity(EntityManager em) {
        PrivateCardData privateCardData = new PrivateCardData()
            .agree(UPDATED_AGREE)
            .finish(UPDATED_FINISH)
            .authority(UPDATED_AUTHORITY)
            .createdByMe(UPDATED_CREATED_BY_ME)
            .variables(UPDATED_VARIABLES)
            .updateTime(UPDATED_UPDATE_TIME);
        return privateCardData;
    }

    @BeforeEach
    public void initTest() {
        privateCardData = createEntity(em);
    }

    @Test
    @Transactional
    void createPrivateCardData() throws Exception {
        int databaseSizeBeforeCreate = privateCardDataRepository.findAll().size();
        // Create the PrivateCardData
        restPrivateCardDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isCreated());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeCreate + 1);
        PrivateCardData testPrivateCardData = privateCardDataList.get(privateCardDataList.size() - 1);
        assertThat(testPrivateCardData.getAgree()).isEqualTo(DEFAULT_AGREE);
        assertThat(testPrivateCardData.getFinish()).isEqualTo(DEFAULT_FINISH);
        assertThat(testPrivateCardData.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
        assertThat(testPrivateCardData.getCreatedByMe()).isEqualTo(DEFAULT_CREATED_BY_ME);
        assertThat(testPrivateCardData.getVariables()).isEqualTo(DEFAULT_VARIABLES);
        assertThat(testPrivateCardData.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void createPrivateCardDataWithExistingId() throws Exception {
        // Create the PrivateCardData with an existing ID
        privateCardData.setId(1L);

        int databaseSizeBeforeCreate = privateCardDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivateCardDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrivateCardData() throws Exception {
        // Initialize the database
        privateCardDataRepository.saveAndFlush(privateCardData);

        // Get all the privateCardDataList
        restPrivateCardDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privateCardData.getId().intValue())))
            .andExpect(jsonPath("$.[*].agree").value(hasItem(DEFAULT_AGREE.booleanValue())))
            .andExpect(jsonPath("$.[*].finish").value(hasItem(DEFAULT_FINISH)))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)))
            .andExpect(jsonPath("$.[*].createdByMe").value(hasItem(DEFAULT_CREATED_BY_ME)))
            .andExpect(jsonPath("$.[*].variables").value(hasItem(DEFAULT_VARIABLES)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getPrivateCardData() throws Exception {
        // Initialize the database
        privateCardDataRepository.saveAndFlush(privateCardData);

        // Get the privateCardData
        restPrivateCardDataMockMvc
            .perform(get(ENTITY_API_URL_ID, privateCardData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(privateCardData.getId().intValue()))
            .andExpect(jsonPath("$.agree").value(DEFAULT_AGREE.booleanValue()))
            .andExpect(jsonPath("$.finish").value(DEFAULT_FINISH))
            .andExpect(jsonPath("$.authority").value(DEFAULT_AUTHORITY))
            .andExpect(jsonPath("$.createdByMe").value(DEFAULT_CREATED_BY_ME))
            .andExpect(jsonPath("$.variables").value(DEFAULT_VARIABLES))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrivateCardData() throws Exception {
        // Get the privateCardData
        restPrivateCardDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPrivateCardData() throws Exception {
        // Initialize the database
        privateCardDataRepository.saveAndFlush(privateCardData);

        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();

        // Update the privateCardData
        PrivateCardData updatedPrivateCardData = privateCardDataRepository.findById(privateCardData.getId()).get();
        // Disconnect from session so that the updates on updatedPrivateCardData are not directly saved in db
        em.detach(updatedPrivateCardData);
        updatedPrivateCardData
            .agree(UPDATED_AGREE)
            .finish(UPDATED_FINISH)
            .authority(UPDATED_AUTHORITY)
            .createdByMe(UPDATED_CREATED_BY_ME)
            .variables(UPDATED_VARIABLES)
            .updateTime(UPDATED_UPDATE_TIME);

        restPrivateCardDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrivateCardData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrivateCardData))
            )
            .andExpect(status().isOk());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
        PrivateCardData testPrivateCardData = privateCardDataList.get(privateCardDataList.size() - 1);
        assertThat(testPrivateCardData.getAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testPrivateCardData.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testPrivateCardData.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
        assertThat(testPrivateCardData.getCreatedByMe()).isEqualTo(UPDATED_CREATED_BY_ME);
        assertThat(testPrivateCardData.getVariables()).isEqualTo(UPDATED_VARIABLES);
        assertThat(testPrivateCardData.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingPrivateCardData() throws Exception {
        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();
        privateCardData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivateCardDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, privateCardData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrivateCardData() throws Exception {
        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();
        privateCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateCardDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrivateCardData() throws Exception {
        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();
        privateCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateCardDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrivateCardDataWithPatch() throws Exception {
        // Initialize the database
        privateCardDataRepository.saveAndFlush(privateCardData);

        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();

        // Update the privateCardData using partial update
        PrivateCardData partialUpdatedPrivateCardData = new PrivateCardData();
        partialUpdatedPrivateCardData.setId(privateCardData.getId());

        partialUpdatedPrivateCardData.finish(UPDATED_FINISH).createdByMe(UPDATED_CREATED_BY_ME).variables(UPDATED_VARIABLES);

        restPrivateCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivateCardData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivateCardData))
            )
            .andExpect(status().isOk());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
        PrivateCardData testPrivateCardData = privateCardDataList.get(privateCardDataList.size() - 1);
        assertThat(testPrivateCardData.getAgree()).isEqualTo(DEFAULT_AGREE);
        assertThat(testPrivateCardData.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testPrivateCardData.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
        assertThat(testPrivateCardData.getCreatedByMe()).isEqualTo(UPDATED_CREATED_BY_ME);
        assertThat(testPrivateCardData.getVariables()).isEqualTo(UPDATED_VARIABLES);
        assertThat(testPrivateCardData.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdatePrivateCardDataWithPatch() throws Exception {
        // Initialize the database
        privateCardDataRepository.saveAndFlush(privateCardData);

        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();

        // Update the privateCardData using partial update
        PrivateCardData partialUpdatedPrivateCardData = new PrivateCardData();
        partialUpdatedPrivateCardData.setId(privateCardData.getId());

        partialUpdatedPrivateCardData
            .agree(UPDATED_AGREE)
            .finish(UPDATED_FINISH)
            .authority(UPDATED_AUTHORITY)
            .createdByMe(UPDATED_CREATED_BY_ME)
            .variables(UPDATED_VARIABLES)
            .updateTime(UPDATED_UPDATE_TIME);

        restPrivateCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivateCardData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivateCardData))
            )
            .andExpect(status().isOk());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
        PrivateCardData testPrivateCardData = privateCardDataList.get(privateCardDataList.size() - 1);
        assertThat(testPrivateCardData.getAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testPrivateCardData.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testPrivateCardData.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
        assertThat(testPrivateCardData.getCreatedByMe()).isEqualTo(UPDATED_CREATED_BY_ME);
        assertThat(testPrivateCardData.getVariables()).isEqualTo(UPDATED_VARIABLES);
        assertThat(testPrivateCardData.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingPrivateCardData() throws Exception {
        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();
        privateCardData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivateCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, privateCardData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrivateCardData() throws Exception {
        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();
        privateCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrivateCardData() throws Exception {
        int databaseSizeBeforeUpdate = privateCardDataRepository.findAll().size();
        privateCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivateCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privateCardData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrivateCardData in the database
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrivateCardData() throws Exception {
        // Initialize the database
        privateCardDataRepository.saveAndFlush(privateCardData);

        int databaseSizeBeforeDelete = privateCardDataRepository.findAll().size();

        // Delete the privateCardData
        restPrivateCardDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, privateCardData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrivateCardData> privateCardDataList = privateCardDataRepository.findAll();
        assertThat(privateCardDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
