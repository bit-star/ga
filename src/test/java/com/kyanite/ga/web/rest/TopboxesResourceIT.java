package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.Topboxes;
import com.kyanite.ga.repository.TopboxesRepository;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link TopboxesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TopboxesResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUXILIARY = false;
    private static final Boolean UPDATED_AUXILIARY = true;

    private static final Boolean DEFAULT_OPEN = false;
    private static final Boolean UPDATED_OPEN = true;

    private static final String ENTITY_API_URL = "/api/topboxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TopboxesRepository topboxesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopboxesMockMvc;

    private Topboxes topboxes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topboxes createEntity(EntityManager em) {
        Topboxes topboxes = new Topboxes()
            .text(DEFAULT_TEXT)
            .link(DEFAULT_LINK)
            .cardId(DEFAULT_CARD_ID)
            .auxiliary(DEFAULT_AUXILIARY)
            .open(DEFAULT_OPEN);
        return topboxes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Topboxes createUpdatedEntity(EntityManager em) {
        Topboxes topboxes = new Topboxes()
            .text(UPDATED_TEXT)
            .link(UPDATED_LINK)
            .cardId(UPDATED_CARD_ID)
            .auxiliary(UPDATED_AUXILIARY)
            .open(UPDATED_OPEN);
        return topboxes;
    }

    @BeforeEach
    public void initTest() {
        topboxes = createEntity(em);
    }

    @Test
    @Transactional
    void createTopboxes() throws Exception {
        int databaseSizeBeforeCreate = topboxesRepository.findAll().size();
        // Create the Topboxes
        restTopboxesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topboxes)))
            .andExpect(status().isCreated());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeCreate + 1);
        Topboxes testTopboxes = topboxesList.get(topboxesList.size() - 1);
        assertThat(testTopboxes.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testTopboxes.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testTopboxes.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testTopboxes.getAuxiliary()).isEqualTo(DEFAULT_AUXILIARY);
        assertThat(testTopboxes.getOpen()).isEqualTo(DEFAULT_OPEN);
    }

    @Test
    @Transactional
    void createTopboxesWithExistingId() throws Exception {
        // Create the Topboxes with an existing ID
        topboxesRepository.saveAndFlush(topboxes);

        int databaseSizeBeforeCreate = topboxesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopboxesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topboxes)))
            .andExpect(status().isBadRequest());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTopboxes() throws Exception {
        // Initialize the database
        topboxesRepository.saveAndFlush(topboxes);

        // Get all the topboxesList
        restTopboxesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topboxes.getId().toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID)))
            .andExpect(jsonPath("$.[*].auxiliary").value(hasItem(DEFAULT_AUXILIARY.booleanValue())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.booleanValue())));
    }

    @Test
    @Transactional
    void getTopboxes() throws Exception {
        // Initialize the database
        topboxesRepository.saveAndFlush(topboxes);

        // Get the topboxes
        restTopboxesMockMvc
            .perform(get(ENTITY_API_URL_ID, topboxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topboxes.getId().toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID))
            .andExpect(jsonPath("$.auxiliary").value(DEFAULT_AUXILIARY.booleanValue()))
            .andExpect(jsonPath("$.open").value(DEFAULT_OPEN.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTopboxes() throws Exception {
        // Get the topboxes
        restTopboxesMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTopboxes() throws Exception {
        // Initialize the database
        topboxesRepository.saveAndFlush(topboxes);

        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();

        // Update the topboxes
        Topboxes updatedTopboxes = topboxesRepository.findById(topboxes.getId()).get();
        // Disconnect from session so that the updates on updatedTopboxes are not directly saved in db
        em.detach(updatedTopboxes);
        updatedTopboxes.text(UPDATED_TEXT).link(UPDATED_LINK).cardId(UPDATED_CARD_ID).auxiliary(UPDATED_AUXILIARY).open(UPDATED_OPEN);

        restTopboxesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTopboxes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTopboxes))
            )
            .andExpect(status().isOk());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
        Topboxes testTopboxes = topboxesList.get(topboxesList.size() - 1);
        assertThat(testTopboxes.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testTopboxes.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testTopboxes.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testTopboxes.getAuxiliary()).isEqualTo(UPDATED_AUXILIARY);
        assertThat(testTopboxes.getOpen()).isEqualTo(UPDATED_OPEN);
    }

    @Test
    @Transactional
    void putNonExistingTopboxes() throws Exception {
        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();
        topboxes.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopboxesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, topboxes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topboxes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTopboxes() throws Exception {
        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();
        topboxes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopboxesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(topboxes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTopboxes() throws Exception {
        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();
        topboxes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopboxesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(topboxes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTopboxesWithPatch() throws Exception {
        // Initialize the database
        topboxesRepository.saveAndFlush(topboxes);

        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();

        // Update the topboxes using partial update
        Topboxes partialUpdatedTopboxes = new Topboxes();
        partialUpdatedTopboxes.setId(topboxes.getId());

        partialUpdatedTopboxes.open(UPDATED_OPEN);

        restTopboxesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopboxes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTopboxes))
            )
            .andExpect(status().isOk());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
        Topboxes testTopboxes = topboxesList.get(topboxesList.size() - 1);
        assertThat(testTopboxes.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testTopboxes.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testTopboxes.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testTopboxes.getAuxiliary()).isEqualTo(DEFAULT_AUXILIARY);
        assertThat(testTopboxes.getOpen()).isEqualTo(UPDATED_OPEN);
    }

    @Test
    @Transactional
    void fullUpdateTopboxesWithPatch() throws Exception {
        // Initialize the database
        topboxesRepository.saveAndFlush(topboxes);

        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();

        // Update the topboxes using partial update
        Topboxes partialUpdatedTopboxes = new Topboxes();
        partialUpdatedTopboxes.setId(topboxes.getId());

        partialUpdatedTopboxes
            .text(UPDATED_TEXT)
            .link(UPDATED_LINK)
            .cardId(UPDATED_CARD_ID)
            .auxiliary(UPDATED_AUXILIARY)
            .open(UPDATED_OPEN);

        restTopboxesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTopboxes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTopboxes))
            )
            .andExpect(status().isOk());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
        Topboxes testTopboxes = topboxesList.get(topboxesList.size() - 1);
        assertThat(testTopboxes.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testTopboxes.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testTopboxes.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testTopboxes.getAuxiliary()).isEqualTo(UPDATED_AUXILIARY);
        assertThat(testTopboxes.getOpen()).isEqualTo(UPDATED_OPEN);
    }

    @Test
    @Transactional
    void patchNonExistingTopboxes() throws Exception {
        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();
        topboxes.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopboxesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, topboxes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(topboxes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTopboxes() throws Exception {
        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();
        topboxes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopboxesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(topboxes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTopboxes() throws Exception {
        int databaseSizeBeforeUpdate = topboxesRepository.findAll().size();
        topboxes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTopboxesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(topboxes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Topboxes in the database
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTopboxes() throws Exception {
        // Initialize the database
        topboxesRepository.saveAndFlush(topboxes);

        int databaseSizeBeforeDelete = topboxesRepository.findAll().size();

        // Delete the topboxes
        restTopboxesMockMvc
            .perform(delete(ENTITY_API_URL_ID, topboxes.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Topboxes> topboxesList = topboxesRepository.findAll();
        assertThat(topboxesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
