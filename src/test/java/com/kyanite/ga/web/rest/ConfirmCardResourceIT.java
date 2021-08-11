package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.ConfirmCard;
import com.kyanite.ga.repository.ConfirmCardRepository;
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
 * Integration tests for the {@link ConfirmCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfirmCardResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FINISH = "AAAAAAAAAA";
    private static final String UPDATED_FINISH = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/confirm-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConfirmCardRepository confirmCardRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfirmCardMockMvc;

    private ConfirmCard confirmCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfirmCard createEntity(EntityManager em) {
        ConfirmCard confirmCard = new ConfirmCard().text(DEFAULT_TEXT).finish(DEFAULT_FINISH).userId(DEFAULT_USER_ID);
        return confirmCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfirmCard createUpdatedEntity(EntityManager em) {
        ConfirmCard confirmCard = new ConfirmCard().text(UPDATED_TEXT).finish(UPDATED_FINISH).userId(UPDATED_USER_ID);
        return confirmCard;
    }

    @BeforeEach
    public void initTest() {
        confirmCard = createEntity(em);
    }

    @Test
    @Transactional
    void createConfirmCard() throws Exception {
        int databaseSizeBeforeCreate = confirmCardRepository.findAll().size();
        // Create the ConfirmCard
        restConfirmCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confirmCard)))
            .andExpect(status().isCreated());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeCreate + 1);
        ConfirmCard testConfirmCard = confirmCardList.get(confirmCardList.size() - 1);
        assertThat(testConfirmCard.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testConfirmCard.getFinish()).isEqualTo(DEFAULT_FINISH);
        assertThat(testConfirmCard.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    void createConfirmCardWithExistingId() throws Exception {
        // Create the ConfirmCard with an existing ID
        confirmCard.setId(1L);

        int databaseSizeBeforeCreate = confirmCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfirmCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confirmCard)))
            .andExpect(status().isBadRequest());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConfirmCards() throws Exception {
        // Initialize the database
        confirmCardRepository.saveAndFlush(confirmCard);

        // Get all the confirmCardList
        restConfirmCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confirmCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].finish").value(hasItem(DEFAULT_FINISH)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    void getConfirmCard() throws Exception {
        // Initialize the database
        confirmCardRepository.saveAndFlush(confirmCard);

        // Get the confirmCard
        restConfirmCardMockMvc
            .perform(get(ENTITY_API_URL_ID, confirmCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(confirmCard.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.finish").value(DEFAULT_FINISH))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    void getNonExistingConfirmCard() throws Exception {
        // Get the confirmCard
        restConfirmCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConfirmCard() throws Exception {
        // Initialize the database
        confirmCardRepository.saveAndFlush(confirmCard);

        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();

        // Update the confirmCard
        ConfirmCard updatedConfirmCard = confirmCardRepository.findById(confirmCard.getId()).get();
        // Disconnect from session so that the updates on updatedConfirmCard are not directly saved in db
        em.detach(updatedConfirmCard);
        updatedConfirmCard.text(UPDATED_TEXT).finish(UPDATED_FINISH).userId(UPDATED_USER_ID);

        restConfirmCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConfirmCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConfirmCard))
            )
            .andExpect(status().isOk());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
        ConfirmCard testConfirmCard = confirmCardList.get(confirmCardList.size() - 1);
        assertThat(testConfirmCard.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testConfirmCard.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testConfirmCard.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void putNonExistingConfirmCard() throws Exception {
        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();
        confirmCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfirmCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, confirmCard.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confirmCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfirmCard() throws Exception {
        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();
        confirmCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(confirmCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfirmCard() throws Exception {
        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();
        confirmCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confirmCard)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfirmCardWithPatch() throws Exception {
        // Initialize the database
        confirmCardRepository.saveAndFlush(confirmCard);

        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();

        // Update the confirmCard using partial update
        ConfirmCard partialUpdatedConfirmCard = new ConfirmCard();
        partialUpdatedConfirmCard.setId(confirmCard.getId());

        partialUpdatedConfirmCard.text(UPDATED_TEXT).finish(UPDATED_FINISH).userId(UPDATED_USER_ID);

        restConfirmCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfirmCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfirmCard))
            )
            .andExpect(status().isOk());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
        ConfirmCard testConfirmCard = confirmCardList.get(confirmCardList.size() - 1);
        assertThat(testConfirmCard.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testConfirmCard.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testConfirmCard.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void fullUpdateConfirmCardWithPatch() throws Exception {
        // Initialize the database
        confirmCardRepository.saveAndFlush(confirmCard);

        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();

        // Update the confirmCard using partial update
        ConfirmCard partialUpdatedConfirmCard = new ConfirmCard();
        partialUpdatedConfirmCard.setId(confirmCard.getId());

        partialUpdatedConfirmCard.text(UPDATED_TEXT).finish(UPDATED_FINISH).userId(UPDATED_USER_ID);

        restConfirmCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfirmCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfirmCard))
            )
            .andExpect(status().isOk());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
        ConfirmCard testConfirmCard = confirmCardList.get(confirmCardList.size() - 1);
        assertThat(testConfirmCard.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testConfirmCard.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testConfirmCard.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingConfirmCard() throws Exception {
        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();
        confirmCard.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfirmCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, confirmCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(confirmCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfirmCard() throws Exception {
        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();
        confirmCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(confirmCard))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfirmCard() throws Exception {
        int databaseSizeBeforeUpdate = confirmCardRepository.findAll().size();
        confirmCard.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmCardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(confirmCard))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfirmCard in the database
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfirmCard() throws Exception {
        // Initialize the database
        confirmCardRepository.saveAndFlush(confirmCard);

        int databaseSizeBeforeDelete = confirmCardRepository.findAll().size();

        // Delete the confirmCard
        restConfirmCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, confirmCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConfirmCard> confirmCardList = confirmCardRepository.findAll();
        assertThat(confirmCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
