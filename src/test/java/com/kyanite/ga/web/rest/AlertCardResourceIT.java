package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.AlertCard;
import com.kyanite.ga.repository.AlertCardRepository;
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
 * Integration tests for the {@link AlertCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlertCardResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_MD_1 = "AAAAAAAAAA";
    private static final String UPDATED_MD_1 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/alert-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlertCardRepository alertCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertCardMockMvc;

    private AlertCard alertCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertCard createEntity(EntityManager em) {
        AlertCard alertCard = new AlertCard().text(DEFAULT_TEXT).userId(DEFAULT_USER_ID).link(DEFAULT_LINK).md1(DEFAULT_MD_1);
        return alertCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlertCard createUpdatedEntity(EntityManager em) {
        AlertCard alertCard = new AlertCard().text(UPDATED_TEXT).userId(UPDATED_USER_ID).link(UPDATED_LINK).md1(UPDATED_MD_1);
        return alertCard;
    }

    @BeforeEach
    public void initTest() {
        alertCard = createEntity(em);
    }

    @Test
    @Transactional
    void createAlertCard() throws Exception {
        int databaseSizeBeforeCreate = alertCardRepository.findAll().size();
        // Create the AlertCard
        restAlertCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alertCard)))
            .andExpect(status().isCreated());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeCreate + 1);
        AlertCard testAlertCard = alertCardList.get(alertCardList.size() - 1);
        assertThat(testAlertCard.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testAlertCard.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAlertCard.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testAlertCard.getMd1()).isEqualTo(DEFAULT_MD_1);
    }

    @Test
    @Transactional
    void createAlertCardWithExistingId() throws Exception {
        // Create the AlertCard with an existing ID
        alertCard.setId(1L);

        int databaseSizeBeforeCreate = alertCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alertCard)))
            .andExpect(status().isBadRequest());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAlertCards() throws Exception {
        // Initialize the database
        alertCardRepository.saveAndFlush(alertCard);

        // Get all the alertCardList
        restAlertCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].md1").value(hasItem(DEFAULT_MD_1)));
    }

    @Test
    @Transactional
    void getAlertCard() throws Exception {
        // Initialize the database
        alertCardRepository.saveAndFlush(alertCard);

        // Get the alertCard
        restAlertCardMockMvc
            .perform(get(ENTITY_API_URL_ID, alertCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertCard.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.md1").value(DEFAULT_MD_1));
    }

    @Test
    @Transactional
    void getNonExistingAlertCard() throws Exception {
        // Get the alertCard
        restAlertCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAlertCard() throws Exception {
        // Initialize the database
        alertCardRepository.saveAndFlush(alertCard);

        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();

        // Update the alertCard
        AlertCard updatedAlertCard = alertCardRepository.findById(alertCard.getId()).get();
        // Disconnect from session so that the updates on updatedAlertCard are not directly saved in db
        em.detach(updatedAlertCard);
        updatedAlertCard.text(UPDATED_TEXT).userId(UPDATED_USER_ID).link(UPDATED_LINK).md1(UPDATED_MD_1);

        restAlertCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAlertCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAlertCard))
            )
            .andExpect(status().isOk());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
        AlertCard testAlertCard = alertCardList.get(alertCardList.size() - 1);
        assertThat(testAlertCard.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testAlertCard.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAlertCard.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testAlertCard.getMd1()).isEqualTo(UPDATED_MD_1);
    }

    @Test
    @Transactional
    void putNonExistingAlertCard() throws Exception {
        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();
        alertCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alertCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlertCard() throws Exception {
        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();
        alertCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alertCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlertCard() throws Exception {
        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();
        alertCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alertCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlertCardWithPatch() throws Exception {
        // Initialize the database
        alertCardRepository.saveAndFlush(alertCard);

        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();

        // Update the alertCard using partial update
        AlertCard partialUpdatedAlertCard = new AlertCard();
        partialUpdatedAlertCard.setId(alertCard.getId());

        partialUpdatedAlertCard.text(UPDATED_TEXT);

        restAlertCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlertCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlertCard))
            )
            .andExpect(status().isOk());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
        AlertCard testAlertCard = alertCardList.get(alertCardList.size() - 1);
        assertThat(testAlertCard.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testAlertCard.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAlertCard.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testAlertCard.getMd1()).isEqualTo(DEFAULT_MD_1);
    }

    @Test
    @Transactional
    void fullUpdateAlertCardWithPatch() throws Exception {
        // Initialize the database
        alertCardRepository.saveAndFlush(alertCard);

        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();

        // Update the alertCard using partial update
        AlertCard partialUpdatedAlertCard = new AlertCard();
        partialUpdatedAlertCard.setId(alertCard.getId());

        partialUpdatedAlertCard.text(UPDATED_TEXT).userId(UPDATED_USER_ID).link(UPDATED_LINK).md1(UPDATED_MD_1);

        restAlertCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlertCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlertCard))
            )
            .andExpect(status().isOk());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
        AlertCard testAlertCard = alertCardList.get(alertCardList.size() - 1);
        assertThat(testAlertCard.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testAlertCard.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAlertCard.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testAlertCard.getMd1()).isEqualTo(UPDATED_MD_1);
    }

    @Test
    @Transactional
    void patchNonExistingAlertCard() throws Exception {
        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();
        alertCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alertCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlertCard() throws Exception {
        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();
        alertCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alertCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlertCard() throws Exception {
        int databaseSizeBeforeUpdate = alertCardRepository.findAll().size();
        alertCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertCardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alertCard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlertCard in the database
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlertCard() throws Exception {
        // Initialize the database
        alertCardRepository.saveAndFlush(alertCard);

        int databaseSizeBeforeDelete = alertCardRepository.findAll().size();

        // Delete the alertCard
        restAlertCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, alertCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlertCard> alertCardList = alertCardRepository.findAll();
        assertThat(alertCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
