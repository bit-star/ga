package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.LinkSystem;
import com.kyanite.ga.repository.LinkSystemRepository;
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
 * Integration tests for the {@link LinkSystemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinkSystemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/link-systems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinkSystemRepository linkSystemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkSystemMockMvc;

    private LinkSystem linkSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkSystem createEntity(EntityManager em) {
        LinkSystem linkSystem = new LinkSystem().name(DEFAULT_NAME);
        return linkSystem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkSystem createUpdatedEntity(EntityManager em) {
        LinkSystem linkSystem = new LinkSystem().name(UPDATED_NAME);
        return linkSystem;
    }

    @BeforeEach
    public void initTest() {
        linkSystem = createEntity(em);
    }

    @Test
    @Transactional
    void createLinkSystem() throws Exception {
        int databaseSizeBeforeCreate = linkSystemRepository.findAll().size();
        // Create the LinkSystem
        restLinkSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkSystem)))
            .andExpect(status().isCreated());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeCreate + 1);
        LinkSystem testLinkSystem = linkSystemList.get(linkSystemList.size() - 1);
        assertThat(testLinkSystem.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createLinkSystemWithExistingId() throws Exception {
        // Create the LinkSystem with an existing ID
        linkSystem.setId(1L);

        int databaseSizeBeforeCreate = linkSystemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkSystemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkSystem)))
            .andExpect(status().isBadRequest());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLinkSystems() throws Exception {
        // Initialize the database
        linkSystemRepository.saveAndFlush(linkSystem);

        // Get all the linkSystemList
        restLinkSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getLinkSystem() throws Exception {
        // Initialize the database
        linkSystemRepository.saveAndFlush(linkSystem);

        // Get the linkSystem
        restLinkSystemMockMvc
            .perform(get(ENTITY_API_URL_ID, linkSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linkSystem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingLinkSystem() throws Exception {
        // Get the linkSystem
        restLinkSystemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLinkSystem() throws Exception {
        // Initialize the database
        linkSystemRepository.saveAndFlush(linkSystem);

        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();

        // Update the linkSystem
        LinkSystem updatedLinkSystem = linkSystemRepository.findById(linkSystem.getId()).get();
        // Disconnect from session so that the updates on updatedLinkSystem are not directly saved in db
        em.detach(updatedLinkSystem);
        updatedLinkSystem.name(UPDATED_NAME);

        restLinkSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLinkSystem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLinkSystem))
            )
            .andExpect(status().isOk());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
        LinkSystem testLinkSystem = linkSystemList.get(linkSystemList.size() - 1);
        assertThat(testLinkSystem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingLinkSystem() throws Exception {
        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();
        linkSystem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkSystem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLinkSystem() throws Exception {
        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();
        linkSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLinkSystem() throws Exception {
        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();
        linkSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkSystemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkSystem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinkSystemWithPatch() throws Exception {
        // Initialize the database
        linkSystemRepository.saveAndFlush(linkSystem);

        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();

        // Update the linkSystem using partial update
        LinkSystem partialUpdatedLinkSystem = new LinkSystem();
        partialUpdatedLinkSystem.setId(linkSystem.getId());

        partialUpdatedLinkSystem.name(UPDATED_NAME);

        restLinkSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkSystem))
            )
            .andExpect(status().isOk());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
        LinkSystem testLinkSystem = linkSystemList.get(linkSystemList.size() - 1);
        assertThat(testLinkSystem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateLinkSystemWithPatch() throws Exception {
        // Initialize the database
        linkSystemRepository.saveAndFlush(linkSystem);

        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();

        // Update the linkSystem using partial update
        LinkSystem partialUpdatedLinkSystem = new LinkSystem();
        partialUpdatedLinkSystem.setId(linkSystem.getId());

        partialUpdatedLinkSystem.name(UPDATED_NAME);

        restLinkSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkSystem))
            )
            .andExpect(status().isOk());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
        LinkSystem testLinkSystem = linkSystemList.get(linkSystemList.size() - 1);
        assertThat(testLinkSystem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingLinkSystem() throws Exception {
        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();
        linkSystem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, linkSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLinkSystem() throws Exception {
        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();
        linkSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLinkSystem() throws Exception {
        int databaseSizeBeforeUpdate = linkSystemRepository.findAll().size();
        linkSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkSystemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(linkSystem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkSystem in the database
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLinkSystem() throws Exception {
        // Initialize the database
        linkSystemRepository.saveAndFlush(linkSystem);

        int databaseSizeBeforeDelete = linkSystemRepository.findAll().size();

        // Delete the linkSystem
        restLinkSystemMockMvc
            .perform(delete(ENTITY_API_URL_ID, linkSystem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinkSystem> linkSystemList = linkSystemRepository.findAll();
        assertThat(linkSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
