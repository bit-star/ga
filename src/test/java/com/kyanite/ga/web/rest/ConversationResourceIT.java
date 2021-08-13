package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.Conversation;
import com.kyanite.ga.repository.ConversationRepository;
import com.kyanite.ga.service.ConversationService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ConversationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConversationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CHATID = "AAAAAAAAAA";
    private static final String UPDATED_CHATID = "BBBBBBBBBB";

    private static final String DEFAULT_OPEN_CONVERSATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_CONVERSATION_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONVERSATION_TAG = 1;
    private static final Integer UPDATED_CONVERSATION_TAG = 2;

    private static final String DEFAULT_USERIDLIST = "AAAAAAAAAA";
    private static final String UPDATED_USERIDLIST = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_SHOW_HISTORY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SHOW_HISTORY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCHABLE = "AAAAAAAAAA";
    private static final String UPDATED_SEARCHABLE = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CHAT_BANNED_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHAT_BANNED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MENTION_ALL_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_MENTION_ALL_AUTHORITY = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGEMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MANAGEMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OFFICIAL_GROUP = false;
    private static final Boolean UPDATED_OFFICIAL_GROUP = true;

    private static final Boolean DEFAULT_ENABLE_SCENEGROUP = false;
    private static final Boolean UPDATED_ENABLE_SCENEGROUP = true;

    private static final String DEFAULT_GROUP_URL = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/conversations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConversationRepository conversationRepository;

    @Mock
    private ConversationRepository conversationRepositoryMock;

    @Mock
    private ConversationService conversationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConversationMockMvc;

    private Conversation conversation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversation createEntity(EntityManager em) {
        Conversation conversation = new Conversation()
            .name(DEFAULT_NAME)
            .title(DEFAULT_TITLE)
            .owner(DEFAULT_OWNER)
            .ownerUserId(DEFAULT_OWNER_USER_ID)
            .chatid(DEFAULT_CHATID)
            .openConversationId(DEFAULT_OPEN_CONVERSATION_ID)
            .conversationTag(DEFAULT_CONVERSATION_TAG)
            .useridlist(DEFAULT_USERIDLIST)
            .uuid(DEFAULT_UUID)
            .icon(DEFAULT_ICON)
            .showHistoryType(DEFAULT_SHOW_HISTORY_TYPE)
            .searchable(DEFAULT_SEARCHABLE)
            .validationType(DEFAULT_VALIDATION_TYPE)
            .chatBannedType(DEFAULT_CHAT_BANNED_TYPE)
            .mentionAllAuthority(DEFAULT_MENTION_ALL_AUTHORITY)
            .managementType(DEFAULT_MANAGEMENT_TYPE)
            .templateId(DEFAULT_TEMPLATE_ID)
            .officialGroup(DEFAULT_OFFICIAL_GROUP)
            .enableScenegroup(DEFAULT_ENABLE_SCENEGROUP)
            .groupUrl(DEFAULT_GROUP_URL)
            .time(DEFAULT_TIME);
        return conversation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversation createUpdatedEntity(EntityManager em) {
        Conversation conversation = new Conversation()
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .owner(UPDATED_OWNER)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .chatid(UPDATED_CHATID)
            .openConversationId(UPDATED_OPEN_CONVERSATION_ID)
            .conversationTag(UPDATED_CONVERSATION_TAG)
            .useridlist(UPDATED_USERIDLIST)
            .uuid(UPDATED_UUID)
            .icon(UPDATED_ICON)
            .showHistoryType(UPDATED_SHOW_HISTORY_TYPE)
            .searchable(UPDATED_SEARCHABLE)
            .validationType(UPDATED_VALIDATION_TYPE)
            .chatBannedType(UPDATED_CHAT_BANNED_TYPE)
            .mentionAllAuthority(UPDATED_MENTION_ALL_AUTHORITY)
            .managementType(UPDATED_MANAGEMENT_TYPE)
            .templateId(UPDATED_TEMPLATE_ID)
            .officialGroup(UPDATED_OFFICIAL_GROUP)
            .enableScenegroup(UPDATED_ENABLE_SCENEGROUP)
            .groupUrl(UPDATED_GROUP_URL)
            .time(UPDATED_TIME);
        return conversation;
    }

    @BeforeEach
    public void initTest() {
        conversation = createEntity(em);
    }

    @Test
    @Transactional
    void createConversation() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();
        // Create the Conversation
        restConversationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isCreated());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate + 1);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConversation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testConversation.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testConversation.getOwnerUserId()).isEqualTo(DEFAULT_OWNER_USER_ID);
        assertThat(testConversation.getChatid()).isEqualTo(DEFAULT_CHATID);
        assertThat(testConversation.getOpenConversationId()).isEqualTo(DEFAULT_OPEN_CONVERSATION_ID);
        assertThat(testConversation.getConversationTag()).isEqualTo(DEFAULT_CONVERSATION_TAG);
        assertThat(testConversation.getUseridlist()).isEqualTo(DEFAULT_USERIDLIST);
        assertThat(testConversation.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConversation.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testConversation.getShowHistoryType()).isEqualTo(DEFAULT_SHOW_HISTORY_TYPE);
        assertThat(testConversation.getSearchable()).isEqualTo(DEFAULT_SEARCHABLE);
        assertThat(testConversation.getValidationType()).isEqualTo(DEFAULT_VALIDATION_TYPE);
        assertThat(testConversation.getChatBannedType()).isEqualTo(DEFAULT_CHAT_BANNED_TYPE);
        assertThat(testConversation.getMentionAllAuthority()).isEqualTo(DEFAULT_MENTION_ALL_AUTHORITY);
        assertThat(testConversation.getManagementType()).isEqualTo(DEFAULT_MANAGEMENT_TYPE);
        assertThat(testConversation.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testConversation.getOfficialGroup()).isEqualTo(DEFAULT_OFFICIAL_GROUP);
        assertThat(testConversation.getEnableScenegroup()).isEqualTo(DEFAULT_ENABLE_SCENEGROUP);
        assertThat(testConversation.getGroupUrl()).isEqualTo(DEFAULT_GROUP_URL);
        assertThat(testConversation.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    void createConversationWithExistingId() throws Exception {
        // Create the Conversation with an existing ID
        conversation.setId("existing_id");

        int databaseSizeBeforeCreate = conversationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConversations() throws Exception {
        // Initialize the database
        conversation.setId(UUID.randomUUID().toString());
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList
        restConversationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversation.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].ownerUserId").value(hasItem(DEFAULT_OWNER_USER_ID)))
            .andExpect(jsonPath("$.[*].chatid").value(hasItem(DEFAULT_CHATID)))
            .andExpect(jsonPath("$.[*].openConversationId").value(hasItem(DEFAULT_OPEN_CONVERSATION_ID)))
            .andExpect(jsonPath("$.[*].conversationTag").value(hasItem(DEFAULT_CONVERSATION_TAG)))
            .andExpect(jsonPath("$.[*].useridlist").value(hasItem(DEFAULT_USERIDLIST.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].showHistoryType").value(hasItem(DEFAULT_SHOW_HISTORY_TYPE)))
            .andExpect(jsonPath("$.[*].searchable").value(hasItem(DEFAULT_SEARCHABLE)))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE)))
            .andExpect(jsonPath("$.[*].chatBannedType").value(hasItem(DEFAULT_CHAT_BANNED_TYPE)))
            .andExpect(jsonPath("$.[*].mentionAllAuthority").value(hasItem(DEFAULT_MENTION_ALL_AUTHORITY)))
            .andExpect(jsonPath("$.[*].managementType").value(hasItem(DEFAULT_MANAGEMENT_TYPE)))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID)))
            .andExpect(jsonPath("$.[*].officialGroup").value(hasItem(DEFAULT_OFFICIAL_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].enableScenegroup").value(hasItem(DEFAULT_ENABLE_SCENEGROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].groupUrl").value(hasItem(DEFAULT_GROUP_URL)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConversationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(conversationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConversationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(conversationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConversationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(conversationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConversationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(conversationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getConversation() throws Exception {
        // Initialize the database
        conversation.setId(UUID.randomUUID().toString());
        conversationRepository.saveAndFlush(conversation);

        // Get the conversation
        restConversationMockMvc
            .perform(get(ENTITY_API_URL_ID, conversation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conversation.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.ownerUserId").value(DEFAULT_OWNER_USER_ID))
            .andExpect(jsonPath("$.chatid").value(DEFAULT_CHATID))
            .andExpect(jsonPath("$.openConversationId").value(DEFAULT_OPEN_CONVERSATION_ID))
            .andExpect(jsonPath("$.conversationTag").value(DEFAULT_CONVERSATION_TAG))
            .andExpect(jsonPath("$.useridlist").value(DEFAULT_USERIDLIST.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.showHistoryType").value(DEFAULT_SHOW_HISTORY_TYPE))
            .andExpect(jsonPath("$.searchable").value(DEFAULT_SEARCHABLE))
            .andExpect(jsonPath("$.validationType").value(DEFAULT_VALIDATION_TYPE))
            .andExpect(jsonPath("$.chatBannedType").value(DEFAULT_CHAT_BANNED_TYPE))
            .andExpect(jsonPath("$.mentionAllAuthority").value(DEFAULT_MENTION_ALL_AUTHORITY))
            .andExpect(jsonPath("$.managementType").value(DEFAULT_MANAGEMENT_TYPE))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID))
            .andExpect(jsonPath("$.officialGroup").value(DEFAULT_OFFICIAL_GROUP.booleanValue()))
            .andExpect(jsonPath("$.enableScenegroup").value(DEFAULT_ENABLE_SCENEGROUP.booleanValue()))
            .andExpect(jsonPath("$.groupUrl").value(DEFAULT_GROUP_URL))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConversation() throws Exception {
        // Get the conversation
        restConversationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConversation() throws Exception {
        // Initialize the database
        conversation.setId(UUID.randomUUID().toString());
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Update the conversation
        Conversation updatedConversation = conversationRepository.findById(conversation.getId()).get();
        // Disconnect from session so that the updates on updatedConversation are not directly saved in db
        em.detach(updatedConversation);
        updatedConversation
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .owner(UPDATED_OWNER)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .chatid(UPDATED_CHATID)
            .openConversationId(UPDATED_OPEN_CONVERSATION_ID)
            .conversationTag(UPDATED_CONVERSATION_TAG)
            .useridlist(UPDATED_USERIDLIST)
            .uuid(UPDATED_UUID)
            .icon(UPDATED_ICON)
            .showHistoryType(UPDATED_SHOW_HISTORY_TYPE)
            .searchable(UPDATED_SEARCHABLE)
            .validationType(UPDATED_VALIDATION_TYPE)
            .chatBannedType(UPDATED_CHAT_BANNED_TYPE)
            .mentionAllAuthority(UPDATED_MENTION_ALL_AUTHORITY)
            .managementType(UPDATED_MANAGEMENT_TYPE)
            .templateId(UPDATED_TEMPLATE_ID)
            .officialGroup(UPDATED_OFFICIAL_GROUP)
            .enableScenegroup(UPDATED_ENABLE_SCENEGROUP)
            .groupUrl(UPDATED_GROUP_URL)
            .time(UPDATED_TIME);

        restConversationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConversation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConversation))
            )
            .andExpect(status().isOk());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConversation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConversation.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testConversation.getOwnerUserId()).isEqualTo(UPDATED_OWNER_USER_ID);
        assertThat(testConversation.getChatid()).isEqualTo(UPDATED_CHATID);
        assertThat(testConversation.getOpenConversationId()).isEqualTo(UPDATED_OPEN_CONVERSATION_ID);
        assertThat(testConversation.getConversationTag()).isEqualTo(UPDATED_CONVERSATION_TAG);
        assertThat(testConversation.getUseridlist()).isEqualTo(UPDATED_USERIDLIST);
        assertThat(testConversation.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConversation.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testConversation.getShowHistoryType()).isEqualTo(UPDATED_SHOW_HISTORY_TYPE);
        assertThat(testConversation.getSearchable()).isEqualTo(UPDATED_SEARCHABLE);
        assertThat(testConversation.getValidationType()).isEqualTo(UPDATED_VALIDATION_TYPE);
        assertThat(testConversation.getChatBannedType()).isEqualTo(UPDATED_CHAT_BANNED_TYPE);
        assertThat(testConversation.getMentionAllAuthority()).isEqualTo(UPDATED_MENTION_ALL_AUTHORITY);
        assertThat(testConversation.getManagementType()).isEqualTo(UPDATED_MANAGEMENT_TYPE);
        assertThat(testConversation.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testConversation.getOfficialGroup()).isEqualTo(UPDATED_OFFICIAL_GROUP);
        assertThat(testConversation.getEnableScenegroup()).isEqualTo(UPDATED_ENABLE_SCENEGROUP);
        assertThat(testConversation.getGroupUrl()).isEqualTo(UPDATED_GROUP_URL);
        assertThat(testConversation.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    void putNonExistingConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();
        conversation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conversation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conversation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();
        conversation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conversation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();
        conversation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConversationWithPatch() throws Exception {
        // Initialize the database
        conversation.setId(UUID.randomUUID().toString());
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Update the conversation using partial update
        Conversation partialUpdatedConversation = new Conversation();
        partialUpdatedConversation.setId(conversation.getId());

        partialUpdatedConversation
            .title(UPDATED_TITLE)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .showHistoryType(UPDATED_SHOW_HISTORY_TYPE)
            .validationType(UPDATED_VALIDATION_TYPE)
            .managementType(UPDATED_MANAGEMENT_TYPE)
            .templateId(UPDATED_TEMPLATE_ID);

        restConversationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConversation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConversation))
            )
            .andExpect(status().isOk());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConversation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConversation.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testConversation.getOwnerUserId()).isEqualTo(UPDATED_OWNER_USER_ID);
        assertThat(testConversation.getChatid()).isEqualTo(DEFAULT_CHATID);
        assertThat(testConversation.getOpenConversationId()).isEqualTo(DEFAULT_OPEN_CONVERSATION_ID);
        assertThat(testConversation.getConversationTag()).isEqualTo(DEFAULT_CONVERSATION_TAG);
        assertThat(testConversation.getUseridlist()).isEqualTo(DEFAULT_USERIDLIST);
        assertThat(testConversation.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConversation.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testConversation.getShowHistoryType()).isEqualTo(UPDATED_SHOW_HISTORY_TYPE);
        assertThat(testConversation.getSearchable()).isEqualTo(DEFAULT_SEARCHABLE);
        assertThat(testConversation.getValidationType()).isEqualTo(UPDATED_VALIDATION_TYPE);
        assertThat(testConversation.getChatBannedType()).isEqualTo(DEFAULT_CHAT_BANNED_TYPE);
        assertThat(testConversation.getMentionAllAuthority()).isEqualTo(DEFAULT_MENTION_ALL_AUTHORITY);
        assertThat(testConversation.getManagementType()).isEqualTo(UPDATED_MANAGEMENT_TYPE);
        assertThat(testConversation.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testConversation.getOfficialGroup()).isEqualTo(DEFAULT_OFFICIAL_GROUP);
        assertThat(testConversation.getEnableScenegroup()).isEqualTo(DEFAULT_ENABLE_SCENEGROUP);
        assertThat(testConversation.getGroupUrl()).isEqualTo(DEFAULT_GROUP_URL);
        assertThat(testConversation.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    void fullUpdateConversationWithPatch() throws Exception {
        // Initialize the database
        conversation.setId(UUID.randomUUID().toString());
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Update the conversation using partial update
        Conversation partialUpdatedConversation = new Conversation();
        partialUpdatedConversation.setId(conversation.getId());

        partialUpdatedConversation
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .owner(UPDATED_OWNER)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .chatid(UPDATED_CHATID)
            .openConversationId(UPDATED_OPEN_CONVERSATION_ID)
            .conversationTag(UPDATED_CONVERSATION_TAG)
            .useridlist(UPDATED_USERIDLIST)
            .uuid(UPDATED_UUID)
            .icon(UPDATED_ICON)
            .showHistoryType(UPDATED_SHOW_HISTORY_TYPE)
            .searchable(UPDATED_SEARCHABLE)
            .validationType(UPDATED_VALIDATION_TYPE)
            .chatBannedType(UPDATED_CHAT_BANNED_TYPE)
            .mentionAllAuthority(UPDATED_MENTION_ALL_AUTHORITY)
            .managementType(UPDATED_MANAGEMENT_TYPE)
            .templateId(UPDATED_TEMPLATE_ID)
            .officialGroup(UPDATED_OFFICIAL_GROUP)
            .enableScenegroup(UPDATED_ENABLE_SCENEGROUP)
            .groupUrl(UPDATED_GROUP_URL)
            .time(UPDATED_TIME);

        restConversationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConversation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConversation))
            )
            .andExpect(status().isOk());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConversation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testConversation.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testConversation.getOwnerUserId()).isEqualTo(UPDATED_OWNER_USER_ID);
        assertThat(testConversation.getChatid()).isEqualTo(UPDATED_CHATID);
        assertThat(testConversation.getOpenConversationId()).isEqualTo(UPDATED_OPEN_CONVERSATION_ID);
        assertThat(testConversation.getConversationTag()).isEqualTo(UPDATED_CONVERSATION_TAG);
        assertThat(testConversation.getUseridlist()).isEqualTo(UPDATED_USERIDLIST);
        assertThat(testConversation.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConversation.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testConversation.getShowHistoryType()).isEqualTo(UPDATED_SHOW_HISTORY_TYPE);
        assertThat(testConversation.getSearchable()).isEqualTo(UPDATED_SEARCHABLE);
        assertThat(testConversation.getValidationType()).isEqualTo(UPDATED_VALIDATION_TYPE);
        assertThat(testConversation.getChatBannedType()).isEqualTo(UPDATED_CHAT_BANNED_TYPE);
        assertThat(testConversation.getMentionAllAuthority()).isEqualTo(UPDATED_MENTION_ALL_AUTHORITY);
        assertThat(testConversation.getManagementType()).isEqualTo(UPDATED_MANAGEMENT_TYPE);
        assertThat(testConversation.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testConversation.getOfficialGroup()).isEqualTo(UPDATED_OFFICIAL_GROUP);
        assertThat(testConversation.getEnableScenegroup()).isEqualTo(UPDATED_ENABLE_SCENEGROUP);
        assertThat(testConversation.getGroupUrl()).isEqualTo(UPDATED_GROUP_URL);
        assertThat(testConversation.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();
        conversation.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conversation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conversation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();
        conversation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conversation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();
        conversation.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConversationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conversation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConversation() throws Exception {
        // Initialize the database
        conversation.setId(UUID.randomUUID().toString());
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeDelete = conversationRepository.findAll().size();

        // Delete the conversation
        restConversationMockMvc
            .perform(delete(ENTITY_API_URL_ID, conversation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
