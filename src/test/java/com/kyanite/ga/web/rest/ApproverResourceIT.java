package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.Approver;
import com.kyanite.ga.domain.enumeration.ApproverRole;
import com.kyanite.ga.repository.ApproverRepository;
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
 * Integration tests for the {@link ApproverResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApproverResourceIT {

    private static final ApproverRole DEFAULT_APPROVER_ROLE = ApproverRole.Approver;
    private static final ApproverRole UPDATED_APPROVER_ROLE = ApproverRole.Proposer;

    private static final Long DEFAULT_OA_USER_ID = 1L;
    private static final Long UPDATED_OA_USER_ID = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/approvers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApproverRepository approverRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApproverMockMvc;

    private Approver approver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approver createEntity(EntityManager em) {
        Approver approver = new Approver().approverRole(DEFAULT_APPROVER_ROLE).oaUserId(DEFAULT_OA_USER_ID).email(DEFAULT_EMAIL);
        return approver;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approver createUpdatedEntity(EntityManager em) {
        Approver approver = new Approver().approverRole(UPDATED_APPROVER_ROLE).oaUserId(UPDATED_OA_USER_ID).email(UPDATED_EMAIL);
        return approver;
    }

    @BeforeEach
    public void initTest() {
        approver = createEntity(em);
    }

    @Test
    @Transactional
    void createApprover() throws Exception {
        int databaseSizeBeforeCreate = approverRepository.findAll().size();
        // Create the Approver
        restApproverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approver)))
            .andExpect(status().isCreated());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeCreate + 1);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverRole()).isEqualTo(DEFAULT_APPROVER_ROLE);
        assertThat(testApprover.getOaUserId()).isEqualTo(DEFAULT_OA_USER_ID);
        assertThat(testApprover.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createApproverWithExistingId() throws Exception {
        // Create the Approver with an existing ID
        approver.setId(1L);

        int databaseSizeBeforeCreate = approverRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApproverMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approver)))
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApprovers() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get all the approverList
        restApproverMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approver.getId().intValue())))
            .andExpect(jsonPath("$.[*].approverRole").value(hasItem(DEFAULT_APPROVER_ROLE.toString())))
            .andExpect(jsonPath("$.[*].oaUserId").value(hasItem(DEFAULT_OA_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        // Get the approver
        restApproverMockMvc
            .perform(get(ENTITY_API_URL_ID, approver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approver.getId().intValue()))
            .andExpect(jsonPath("$.approverRole").value(DEFAULT_APPROVER_ROLE.toString()))
            .andExpect(jsonPath("$.oaUserId").value(DEFAULT_OA_USER_ID.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingApprover() throws Exception {
        // Get the approver
        restApproverMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver
        Approver updatedApprover = approverRepository.findById(approver.getId()).get();
        // Disconnect from session so that the updates on updatedApprover are not directly saved in db
        em.detach(updatedApprover);
        updatedApprover.approverRole(UPDATED_APPROVER_ROLE).oaUserId(UPDATED_OA_USER_ID).email(UPDATED_EMAIL);

        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApprover.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApprover))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverRole()).isEqualTo(UPDATED_APPROVER_ROLE);
        assertThat(testApprover.getOaUserId()).isEqualTo(UPDATED_OA_USER_ID);
        assertThat(testApprover.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approver.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approver)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApproverWithPatch() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver using partial update
        Approver partialUpdatedApprover = new Approver();
        partialUpdatedApprover.setId(approver.getId());

        partialUpdatedApprover.oaUserId(UPDATED_OA_USER_ID);

        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprover.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprover))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverRole()).isEqualTo(DEFAULT_APPROVER_ROLE);
        assertThat(testApprover.getOaUserId()).isEqualTo(UPDATED_OA_USER_ID);
        assertThat(testApprover.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateApproverWithPatch() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeUpdate = approverRepository.findAll().size();

        // Update the approver using partial update
        Approver partialUpdatedApprover = new Approver();
        partialUpdatedApprover.setId(approver.getId());

        partialUpdatedApprover.approverRole(UPDATED_APPROVER_ROLE).oaUserId(UPDATED_OA_USER_ID).email(UPDATED_EMAIL);

        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprover.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprover))
            )
            .andExpect(status().isOk());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
        Approver testApprover = approverList.get(approverList.size() - 1);
        assertThat(testApprover.getApproverRole()).isEqualTo(UPDATED_APPROVER_ROLE);
        assertThat(testApprover.getOaUserId()).isEqualTo(UPDATED_OA_USER_ID);
        assertThat(testApprover.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approver.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approver))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprover() throws Exception {
        int databaseSizeBeforeUpdate = approverRepository.findAll().size();
        approver.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApproverMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(approver)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approver in the database
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprover() throws Exception {
        // Initialize the database
        approverRepository.saveAndFlush(approver);

        int databaseSizeBeforeDelete = approverRepository.findAll().size();

        // Delete the approver
        restApproverMockMvc
            .perform(delete(ENTITY_API_URL_ID, approver.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Approver> approverList = approverRepository.findAll();
        assertThat(approverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
