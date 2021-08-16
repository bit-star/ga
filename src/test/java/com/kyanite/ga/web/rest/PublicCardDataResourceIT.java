package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.PublicCardData;
import com.kyanite.ga.domain.enumeration.PublicDataCardStatus;
import com.kyanite.ga.domain.enumeration.WorkflowInstanceStatus;
import com.kyanite.ga.repository.PublicCardDataRepository;
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
 * Integration tests for the {@link PublicCardDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicCardDataResourceIT {

    private static final Long DEFAULT_REQUESTID = 1L;
    private static final Long UPDATED_REQUESTID = 2L;

    private static final Long DEFAULT_WORKFLOWID = 1L;
    private static final Long UPDATED_WORKFLOWID = 2L;

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FEE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_FEE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPES_OF_FEE = "AAAAAAAAAA";
    private static final String UPDATED_TYPES_OF_FEE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AGREE = false;
    private static final Boolean UPDATED_AGREE = true;

    private static final String DEFAULT_FINISH = "AAAAAAAAAA";
    private static final String UPDATED_FINISH = "BBBBBBBBBB";

    private static final PublicDataCardStatus DEFAULT_STATUS = PublicDataCardStatus.Effect;
    private static final PublicDataCardStatus UPDATED_STATUS = PublicDataCardStatus.Invalid;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_AGREE_NUM = 1L;
    private static final Long UPDATED_AGREE_NUM = 2L;

    private static final Long DEFAULT_REFUSE_NUM = 1L;
    private static final Long UPDATED_REFUSE_NUM = 2L;

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final WorkflowInstanceStatus DEFAULT_OA_STATUS = WorkflowInstanceStatus.Launch;
    private static final WorkflowInstanceStatus UPDATED_OA_STATUS = WorkflowInstanceStatus.Refuse;

    private static final String ENTITY_API_URL = "/api/public-card-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublicCardDataRepository publicCardDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicCardDataMockMvc;

    private PublicCardData publicCardData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicCardData createEntity(EntityManager em) {
        PublicCardData publicCardData = new PublicCardData()
            .requestid(DEFAULT_REQUESTID)
            .workflowid(DEFAULT_WORKFLOWID)
            .valid(DEFAULT_VALID)
            .link(DEFAULT_LINK)
            .updateLink(DEFAULT_UPDATE_LINK)
            .name(DEFAULT_NAME)
            .feeValue(DEFAULT_FEE_VALUE)
            .reason(DEFAULT_REASON)
            .itemType(DEFAULT_ITEM_TYPE)
            .typesOfFee(DEFAULT_TYPES_OF_FEE)
            .agree(DEFAULT_AGREE)
            .finish(DEFAULT_FINISH)
            .status(DEFAULT_STATUS)
            .content(DEFAULT_CONTENT)
            .agreeNum(DEFAULT_AGREE_NUM)
            .refuseNum(DEFAULT_REFUSE_NUM)
            .time(DEFAULT_TIME)
            .oaStatus(DEFAULT_OA_STATUS);
        return publicCardData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicCardData createUpdatedEntity(EntityManager em) {
        PublicCardData publicCardData = new PublicCardData()
            .requestid(UPDATED_REQUESTID)
            .workflowid(UPDATED_WORKFLOWID)
            .valid(UPDATED_VALID)
            .link(UPDATED_LINK)
            .updateLink(UPDATED_UPDATE_LINK)
            .name(UPDATED_NAME)
            .feeValue(UPDATED_FEE_VALUE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE)
            .finish(UPDATED_FINISH)
            .status(UPDATED_STATUS)
            .content(UPDATED_CONTENT)
            .agreeNum(UPDATED_AGREE_NUM)
            .refuseNum(UPDATED_REFUSE_NUM)
            .time(UPDATED_TIME)
            .oaStatus(UPDATED_OA_STATUS);
        return publicCardData;
    }

    @BeforeEach
    public void initTest() {
        publicCardData = createEntity(em);
    }

    @Test
    @Transactional
    void createPublicCardData() throws Exception {
        int databaseSizeBeforeCreate = publicCardDataRepository.findAll().size();
        // Create the PublicCardData
        restPublicCardDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicCardData))
            )
            .andExpect(status().isCreated());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeCreate + 1);
        PublicCardData testPublicCardData = publicCardDataList.get(publicCardDataList.size() - 1);
        assertThat(testPublicCardData.getRequestid()).isEqualTo(DEFAULT_REQUESTID);
        assertThat(testPublicCardData.getWorkflowid()).isEqualTo(DEFAULT_WORKFLOWID);
        assertThat(testPublicCardData.getValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testPublicCardData.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testPublicCardData.getUpdateLink()).isEqualTo(DEFAULT_UPDATE_LINK);
        assertThat(testPublicCardData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublicCardData.getFeeValue()).isEqualTo(DEFAULT_FEE_VALUE);
        assertThat(testPublicCardData.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPublicCardData.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testPublicCardData.getTypesOfFee()).isEqualTo(DEFAULT_TYPES_OF_FEE);
        assertThat(testPublicCardData.getAgree()).isEqualTo(DEFAULT_AGREE);
        assertThat(testPublicCardData.getFinish()).isEqualTo(DEFAULT_FINISH);
        assertThat(testPublicCardData.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPublicCardData.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPublicCardData.getAgreeNum()).isEqualTo(DEFAULT_AGREE_NUM);
        assertThat(testPublicCardData.getRefuseNum()).isEqualTo(DEFAULT_REFUSE_NUM);
        assertThat(testPublicCardData.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testPublicCardData.getOaStatus()).isEqualTo(DEFAULT_OA_STATUS);
    }

    @Test
    @Transactional
    void createPublicCardDataWithExistingId() throws Exception {
        // Create the PublicCardData with an existing ID
        publicCardData.setId(1L);

        int databaseSizeBeforeCreate = publicCardDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicCardDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicCardData() throws Exception {
        // Initialize the database
        publicCardDataRepository.saveAndFlush(publicCardData);

        // Get all the publicCardDataList
        restPublicCardDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicCardData.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestid").value(hasItem(DEFAULT_REQUESTID.intValue())))
            .andExpect(jsonPath("$.[*].workflowid").value(hasItem(DEFAULT_WORKFLOWID.intValue())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].updateLink").value(hasItem(DEFAULT_UPDATE_LINK)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].feeValue").value(hasItem(DEFAULT_FEE_VALUE)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].typesOfFee").value(hasItem(DEFAULT_TYPES_OF_FEE)))
            .andExpect(jsonPath("$.[*].agree").value(hasItem(DEFAULT_AGREE.booleanValue())))
            .andExpect(jsonPath("$.[*].finish").value(hasItem(DEFAULT_FINISH)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].agreeNum").value(hasItem(DEFAULT_AGREE_NUM.intValue())))
            .andExpect(jsonPath("$.[*].refuseNum").value(hasItem(DEFAULT_REFUSE_NUM.intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].oaStatus").value(hasItem(DEFAULT_OA_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPublicCardData() throws Exception {
        // Initialize the database
        publicCardDataRepository.saveAndFlush(publicCardData);

        // Get the publicCardData
        restPublicCardDataMockMvc
            .perform(get(ENTITY_API_URL_ID, publicCardData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicCardData.getId().intValue()))
            .andExpect(jsonPath("$.requestid").value(DEFAULT_REQUESTID.intValue()))
            .andExpect(jsonPath("$.workflowid").value(DEFAULT_WORKFLOWID.intValue()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.updateLink").value(DEFAULT_UPDATE_LINK))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.feeValue").value(DEFAULT_FEE_VALUE))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE))
            .andExpect(jsonPath("$.typesOfFee").value(DEFAULT_TYPES_OF_FEE))
            .andExpect(jsonPath("$.agree").value(DEFAULT_AGREE.booleanValue()))
            .andExpect(jsonPath("$.finish").value(DEFAULT_FINISH))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.agreeNum").value(DEFAULT_AGREE_NUM.intValue()))
            .andExpect(jsonPath("$.refuseNum").value(DEFAULT_REFUSE_NUM.intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.oaStatus").value(DEFAULT_OA_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPublicCardData() throws Exception {
        // Get the publicCardData
        restPublicCardDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublicCardData() throws Exception {
        // Initialize the database
        publicCardDataRepository.saveAndFlush(publicCardData);

        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();

        // Update the publicCardData
        PublicCardData updatedPublicCardData = publicCardDataRepository.findById(publicCardData.getId()).get();
        // Disconnect from session so that the updates on updatedPublicCardData are not directly saved in db
        em.detach(updatedPublicCardData);
        updatedPublicCardData
            .requestid(UPDATED_REQUESTID)
            .workflowid(UPDATED_WORKFLOWID)
            .valid(UPDATED_VALID)
            .link(UPDATED_LINK)
            .updateLink(UPDATED_UPDATE_LINK)
            .name(UPDATED_NAME)
            .feeValue(UPDATED_FEE_VALUE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE)
            .finish(UPDATED_FINISH)
            .status(UPDATED_STATUS)
            .content(UPDATED_CONTENT)
            .agreeNum(UPDATED_AGREE_NUM)
            .refuseNum(UPDATED_REFUSE_NUM)
            .time(UPDATED_TIME)
            .oaStatus(UPDATED_OA_STATUS);

        restPublicCardDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPublicCardData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPublicCardData))
            )
            .andExpect(status().isOk());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
        PublicCardData testPublicCardData = publicCardDataList.get(publicCardDataList.size() - 1);
        assertThat(testPublicCardData.getRequestid()).isEqualTo(UPDATED_REQUESTID);
        assertThat(testPublicCardData.getWorkflowid()).isEqualTo(UPDATED_WORKFLOWID);
        assertThat(testPublicCardData.getValid()).isEqualTo(UPDATED_VALID);
        assertThat(testPublicCardData.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testPublicCardData.getUpdateLink()).isEqualTo(UPDATED_UPDATE_LINK);
        assertThat(testPublicCardData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicCardData.getFeeValue()).isEqualTo(UPDATED_FEE_VALUE);
        assertThat(testPublicCardData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPublicCardData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPublicCardData.getTypesOfFee()).isEqualTo(UPDATED_TYPES_OF_FEE);
        assertThat(testPublicCardData.getAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testPublicCardData.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testPublicCardData.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPublicCardData.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPublicCardData.getAgreeNum()).isEqualTo(UPDATED_AGREE_NUM);
        assertThat(testPublicCardData.getRefuseNum()).isEqualTo(UPDATED_REFUSE_NUM);
        assertThat(testPublicCardData.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testPublicCardData.getOaStatus()).isEqualTo(UPDATED_OA_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPublicCardData() throws Exception {
        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();
        publicCardData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicCardDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicCardData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicCardData() throws Exception {
        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();
        publicCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicCardDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publicCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicCardData() throws Exception {
        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();
        publicCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicCardDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publicCardData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicCardDataWithPatch() throws Exception {
        // Initialize the database
        publicCardDataRepository.saveAndFlush(publicCardData);

        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();

        // Update the publicCardData using partial update
        PublicCardData partialUpdatedPublicCardData = new PublicCardData();
        partialUpdatedPublicCardData.setId(publicCardData.getId());

        partialUpdatedPublicCardData
            .requestid(UPDATED_REQUESTID)
            .workflowid(UPDATED_WORKFLOWID)
            .link(UPDATED_LINK)
            .updateLink(UPDATED_UPDATE_LINK)
            .name(UPDATED_NAME)
            .feeValue(UPDATED_FEE_VALUE)
            .agree(UPDATED_AGREE)
            .status(UPDATED_STATUS)
            .content(UPDATED_CONTENT)
            .agreeNum(UPDATED_AGREE_NUM)
            .time(UPDATED_TIME)
            .oaStatus(UPDATED_OA_STATUS);

        restPublicCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicCardData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicCardData))
            )
            .andExpect(status().isOk());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
        PublicCardData testPublicCardData = publicCardDataList.get(publicCardDataList.size() - 1);
        assertThat(testPublicCardData.getRequestid()).isEqualTo(UPDATED_REQUESTID);
        assertThat(testPublicCardData.getWorkflowid()).isEqualTo(UPDATED_WORKFLOWID);
        assertThat(testPublicCardData.getValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testPublicCardData.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testPublicCardData.getUpdateLink()).isEqualTo(UPDATED_UPDATE_LINK);
        assertThat(testPublicCardData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicCardData.getFeeValue()).isEqualTo(UPDATED_FEE_VALUE);
        assertThat(testPublicCardData.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPublicCardData.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testPublicCardData.getTypesOfFee()).isEqualTo(DEFAULT_TYPES_OF_FEE);
        assertThat(testPublicCardData.getAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testPublicCardData.getFinish()).isEqualTo(DEFAULT_FINISH);
        assertThat(testPublicCardData.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPublicCardData.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPublicCardData.getAgreeNum()).isEqualTo(UPDATED_AGREE_NUM);
        assertThat(testPublicCardData.getRefuseNum()).isEqualTo(DEFAULT_REFUSE_NUM);
        assertThat(testPublicCardData.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testPublicCardData.getOaStatus()).isEqualTo(UPDATED_OA_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePublicCardDataWithPatch() throws Exception {
        // Initialize the database
        publicCardDataRepository.saveAndFlush(publicCardData);

        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();

        // Update the publicCardData using partial update
        PublicCardData partialUpdatedPublicCardData = new PublicCardData();
        partialUpdatedPublicCardData.setId(publicCardData.getId());

        partialUpdatedPublicCardData
            .requestid(UPDATED_REQUESTID)
            .workflowid(UPDATED_WORKFLOWID)
            .valid(UPDATED_VALID)
            .link(UPDATED_LINK)
            .updateLink(UPDATED_UPDATE_LINK)
            .name(UPDATED_NAME)
            .feeValue(UPDATED_FEE_VALUE)
            .reason(UPDATED_REASON)
            .itemType(UPDATED_ITEM_TYPE)
            .typesOfFee(UPDATED_TYPES_OF_FEE)
            .agree(UPDATED_AGREE)
            .finish(UPDATED_FINISH)
            .status(UPDATED_STATUS)
            .content(UPDATED_CONTENT)
            .agreeNum(UPDATED_AGREE_NUM)
            .refuseNum(UPDATED_REFUSE_NUM)
            .time(UPDATED_TIME)
            .oaStatus(UPDATED_OA_STATUS);

        restPublicCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicCardData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublicCardData))
            )
            .andExpect(status().isOk());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
        PublicCardData testPublicCardData = publicCardDataList.get(publicCardDataList.size() - 1);
        assertThat(testPublicCardData.getRequestid()).isEqualTo(UPDATED_REQUESTID);
        assertThat(testPublicCardData.getWorkflowid()).isEqualTo(UPDATED_WORKFLOWID);
        assertThat(testPublicCardData.getValid()).isEqualTo(UPDATED_VALID);
        assertThat(testPublicCardData.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testPublicCardData.getUpdateLink()).isEqualTo(UPDATED_UPDATE_LINK);
        assertThat(testPublicCardData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublicCardData.getFeeValue()).isEqualTo(UPDATED_FEE_VALUE);
        assertThat(testPublicCardData.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPublicCardData.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPublicCardData.getTypesOfFee()).isEqualTo(UPDATED_TYPES_OF_FEE);
        assertThat(testPublicCardData.getAgree()).isEqualTo(UPDATED_AGREE);
        assertThat(testPublicCardData.getFinish()).isEqualTo(UPDATED_FINISH);
        assertThat(testPublicCardData.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPublicCardData.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPublicCardData.getAgreeNum()).isEqualTo(UPDATED_AGREE_NUM);
        assertThat(testPublicCardData.getRefuseNum()).isEqualTo(UPDATED_REFUSE_NUM);
        assertThat(testPublicCardData.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testPublicCardData.getOaStatus()).isEqualTo(UPDATED_OA_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPublicCardData() throws Exception {
        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();
        publicCardData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicCardData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicCardData() throws Exception {
        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();
        publicCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publicCardData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicCardData() throws Exception {
        int databaseSizeBeforeUpdate = publicCardDataRepository.findAll().size();
        publicCardData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicCardDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(publicCardData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicCardData in the database
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicCardData() throws Exception {
        // Initialize the database
        publicCardDataRepository.saveAndFlush(publicCardData);

        int databaseSizeBeforeDelete = publicCardDataRepository.findAll().size();

        // Delete the publicCardData
        restPublicCardDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicCardData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicCardData> publicCardDataList = publicCardDataRepository.findAll();
        assertThat(publicCardDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
