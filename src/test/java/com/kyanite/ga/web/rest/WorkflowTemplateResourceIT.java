package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.WorkflowTemplate;
import com.kyanite.ga.repository.WorkflowTemplateRepository;
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
 * Integration tests for the {@link WorkflowTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkflowTemplateResourceIT {

    private static final String DEFAULT_FORM_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_WORKFLOW_ID = "AAAAAAAAAA";
    private static final String UPDATED_WORKFLOW_ID = "BBBBBBBBBB";

    private static final String DEFAULT_WORKFLOW_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKFLOW_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WORKFLOW_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_WORKFLOW_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_WORKFLOW_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKFLOW_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DD_GROUP_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DD_GROUP_TEMPLATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DD_CARD_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DD_CARD_TEMPLATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DD_CARD_CALL_BACK_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DD_CARD_CALL_BACK_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_E_MOBILE_CREATE_PAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_E_MOBILE_CREATE_PAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CHATID_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_CHATID_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS_FIELD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/workflow-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkflowTemplateRepository workflowTemplateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkflowTemplateMockMvc;

    private WorkflowTemplate workflowTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkflowTemplate createEntity(EntityManager em) {
        WorkflowTemplate workflowTemplate = new WorkflowTemplate()
            .formId(DEFAULT_FORM_ID)
            .workflowId(DEFAULT_WORKFLOW_ID)
            .workflowName(DEFAULT_WORKFLOW_NAME)
            .workflowTypeId(DEFAULT_WORKFLOW_TYPE_ID)
            .workflowTypeName(DEFAULT_WORKFLOW_TYPE_NAME)
            .ddGroupTemplateId(DEFAULT_DD_GROUP_TEMPLATE_ID)
            .ddCardTemplateId(DEFAULT_DD_CARD_TEMPLATE_ID)
            .ddCardCallBackKey(DEFAULT_DD_CARD_CALL_BACK_KEY)
            .eMobileCreatePageUrl(DEFAULT_E_MOBILE_CREATE_PAGE_URL)
            .chatidField(DEFAULT_CHATID_FIELD)
            .sourceField(DEFAULT_SOURCE_FIELD)
            .commentsField(DEFAULT_COMMENTS_FIELD);
        return workflowTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkflowTemplate createUpdatedEntity(EntityManager em) {
        WorkflowTemplate workflowTemplate = new WorkflowTemplate()
            .formId(UPDATED_FORM_ID)
            .workflowId(UPDATED_WORKFLOW_ID)
            .workflowName(UPDATED_WORKFLOW_NAME)
            .workflowTypeId(UPDATED_WORKFLOW_TYPE_ID)
            .workflowTypeName(UPDATED_WORKFLOW_TYPE_NAME)
            .ddGroupTemplateId(UPDATED_DD_GROUP_TEMPLATE_ID)
            .ddCardTemplateId(UPDATED_DD_CARD_TEMPLATE_ID)
            .ddCardCallBackKey(UPDATED_DD_CARD_CALL_BACK_KEY)
            .eMobileCreatePageUrl(UPDATED_E_MOBILE_CREATE_PAGE_URL)
            .chatidField(UPDATED_CHATID_FIELD)
            .sourceField(UPDATED_SOURCE_FIELD)
            .commentsField(UPDATED_COMMENTS_FIELD);
        return workflowTemplate;
    }

    @BeforeEach
    public void initTest() {
        workflowTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkflowTemplate() throws Exception {
        int databaseSizeBeforeCreate = workflowTemplateRepository.findAll().size();
        // Create the WorkflowTemplate
        restWorkflowTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isCreated());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        WorkflowTemplate testWorkflowTemplate = workflowTemplateList.get(workflowTemplateList.size() - 1);
        assertThat(testWorkflowTemplate.getFormId()).isEqualTo(DEFAULT_FORM_ID);
        assertThat(testWorkflowTemplate.getWorkflowId()).isEqualTo(DEFAULT_WORKFLOW_ID);
        assertThat(testWorkflowTemplate.getWorkflowName()).isEqualTo(DEFAULT_WORKFLOW_NAME);
        assertThat(testWorkflowTemplate.getWorkflowTypeId()).isEqualTo(DEFAULT_WORKFLOW_TYPE_ID);
        assertThat(testWorkflowTemplate.getWorkflowTypeName()).isEqualTo(DEFAULT_WORKFLOW_TYPE_NAME);
        assertThat(testWorkflowTemplate.getDdGroupTemplateId()).isEqualTo(DEFAULT_DD_GROUP_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardTemplateId()).isEqualTo(DEFAULT_DD_CARD_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardCallBackKey()).isEqualTo(DEFAULT_DD_CARD_CALL_BACK_KEY);
        assertThat(testWorkflowTemplate.geteMobileCreatePageUrl()).isEqualTo(DEFAULT_E_MOBILE_CREATE_PAGE_URL);
        assertThat(testWorkflowTemplate.getChatidField()).isEqualTo(DEFAULT_CHATID_FIELD);
        assertThat(testWorkflowTemplate.getSourceField()).isEqualTo(DEFAULT_SOURCE_FIELD);
        assertThat(testWorkflowTemplate.getCommentsField()).isEqualTo(DEFAULT_COMMENTS_FIELD);
    }

    @Test
    @Transactional
    void createWorkflowTemplateWithExistingId() throws Exception {
        // Create the WorkflowTemplate with an existing ID
        workflowTemplate.setId(1L);

        int databaseSizeBeforeCreate = workflowTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkflowTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkflowTemplates() throws Exception {
        // Initialize the database
        workflowTemplateRepository.saveAndFlush(workflowTemplate);

        // Get all the workflowTemplateList
        restWorkflowTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workflowTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(DEFAULT_FORM_ID)))
            .andExpect(jsonPath("$.[*].workflowId").value(hasItem(DEFAULT_WORKFLOW_ID)))
            .andExpect(jsonPath("$.[*].workflowName").value(hasItem(DEFAULT_WORKFLOW_NAME)))
            .andExpect(jsonPath("$.[*].workflowTypeId").value(hasItem(DEFAULT_WORKFLOW_TYPE_ID)))
            .andExpect(jsonPath("$.[*].workflowTypeName").value(hasItem(DEFAULT_WORKFLOW_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].ddGroupTemplateId").value(hasItem(DEFAULT_DD_GROUP_TEMPLATE_ID)))
            .andExpect(jsonPath("$.[*].ddCardTemplateId").value(hasItem(DEFAULT_DD_CARD_TEMPLATE_ID)))
            .andExpect(jsonPath("$.[*].ddCardCallBackKey").value(hasItem(DEFAULT_DD_CARD_CALL_BACK_KEY)))
            .andExpect(jsonPath("$.[*].eMobileCreatePageUrl").value(hasItem(DEFAULT_E_MOBILE_CREATE_PAGE_URL)))
            .andExpect(jsonPath("$.[*].chatidField").value(hasItem(DEFAULT_CHATID_FIELD)))
            .andExpect(jsonPath("$.[*].sourceField").value(hasItem(DEFAULT_SOURCE_FIELD)))
            .andExpect(jsonPath("$.[*].commentsField").value(hasItem(DEFAULT_COMMENTS_FIELD)));
    }

    @Test
    @Transactional
    void getWorkflowTemplate() throws Exception {
        // Initialize the database
        workflowTemplateRepository.saveAndFlush(workflowTemplate);

        // Get the workflowTemplate
        restWorkflowTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, workflowTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workflowTemplate.getId().intValue()))
            .andExpect(jsonPath("$.formId").value(DEFAULT_FORM_ID))
            .andExpect(jsonPath("$.workflowId").value(DEFAULT_WORKFLOW_ID))
            .andExpect(jsonPath("$.workflowName").value(DEFAULT_WORKFLOW_NAME))
            .andExpect(jsonPath("$.workflowTypeId").value(DEFAULT_WORKFLOW_TYPE_ID))
            .andExpect(jsonPath("$.workflowTypeName").value(DEFAULT_WORKFLOW_TYPE_NAME))
            .andExpect(jsonPath("$.ddGroupTemplateId").value(DEFAULT_DD_GROUP_TEMPLATE_ID))
            .andExpect(jsonPath("$.ddCardTemplateId").value(DEFAULT_DD_CARD_TEMPLATE_ID))
            .andExpect(jsonPath("$.ddCardCallBackKey").value(DEFAULT_DD_CARD_CALL_BACK_KEY))
            .andExpect(jsonPath("$.eMobileCreatePageUrl").value(DEFAULT_E_MOBILE_CREATE_PAGE_URL))
            .andExpect(jsonPath("$.chatidField").value(DEFAULT_CHATID_FIELD))
            .andExpect(jsonPath("$.sourceField").value(DEFAULT_SOURCE_FIELD))
            .andExpect(jsonPath("$.commentsField").value(DEFAULT_COMMENTS_FIELD));
    }

    @Test
    @Transactional
    void getNonExistingWorkflowTemplate() throws Exception {
        // Get the workflowTemplate
        restWorkflowTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkflowTemplate() throws Exception {
        // Initialize the database
        workflowTemplateRepository.saveAndFlush(workflowTemplate);

        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();

        // Update the workflowTemplate
        WorkflowTemplate updatedWorkflowTemplate = workflowTemplateRepository.findById(workflowTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedWorkflowTemplate are not directly saved in db
        em.detach(updatedWorkflowTemplate);
        updatedWorkflowTemplate
            .formId(UPDATED_FORM_ID)
            .workflowId(UPDATED_WORKFLOW_ID)
            .workflowName(UPDATED_WORKFLOW_NAME)
            .workflowTypeId(UPDATED_WORKFLOW_TYPE_ID)
            .workflowTypeName(UPDATED_WORKFLOW_TYPE_NAME)
            .ddGroupTemplateId(UPDATED_DD_GROUP_TEMPLATE_ID)
            .ddCardTemplateId(UPDATED_DD_CARD_TEMPLATE_ID)
            .ddCardCallBackKey(UPDATED_DD_CARD_CALL_BACK_KEY)
            .eMobileCreatePageUrl(UPDATED_E_MOBILE_CREATE_PAGE_URL)
            .chatidField(UPDATED_CHATID_FIELD)
            .sourceField(UPDATED_SOURCE_FIELD)
            .commentsField(UPDATED_COMMENTS_FIELD);

        restWorkflowTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkflowTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkflowTemplate))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
        WorkflowTemplate testWorkflowTemplate = workflowTemplateList.get(workflowTemplateList.size() - 1);
        assertThat(testWorkflowTemplate.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testWorkflowTemplate.getWorkflowId()).isEqualTo(UPDATED_WORKFLOW_ID);
        assertThat(testWorkflowTemplate.getWorkflowName()).isEqualTo(UPDATED_WORKFLOW_NAME);
        assertThat(testWorkflowTemplate.getWorkflowTypeId()).isEqualTo(UPDATED_WORKFLOW_TYPE_ID);
        assertThat(testWorkflowTemplate.getWorkflowTypeName()).isEqualTo(UPDATED_WORKFLOW_TYPE_NAME);
        assertThat(testWorkflowTemplate.getDdGroupTemplateId()).isEqualTo(UPDATED_DD_GROUP_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardTemplateId()).isEqualTo(UPDATED_DD_CARD_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardCallBackKey()).isEqualTo(UPDATED_DD_CARD_CALL_BACK_KEY);
        assertThat(testWorkflowTemplate.geteMobileCreatePageUrl()).isEqualTo(UPDATED_E_MOBILE_CREATE_PAGE_URL);
        assertThat(testWorkflowTemplate.getChatidField()).isEqualTo(UPDATED_CHATID_FIELD);
        assertThat(testWorkflowTemplate.getSourceField()).isEqualTo(UPDATED_SOURCE_FIELD);
        assertThat(testWorkflowTemplate.getCommentsField()).isEqualTo(UPDATED_COMMENTS_FIELD);
    }

    @Test
    @Transactional
    void putNonExistingWorkflowTemplate() throws Exception {
        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();
        workflowTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workflowTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkflowTemplate() throws Exception {
        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();
        workflowTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkflowTemplate() throws Exception {
        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();
        workflowTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowTemplateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkflowTemplateWithPatch() throws Exception {
        // Initialize the database
        workflowTemplateRepository.saveAndFlush(workflowTemplate);

        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();

        // Update the workflowTemplate using partial update
        WorkflowTemplate partialUpdatedWorkflowTemplate = new WorkflowTemplate();
        partialUpdatedWorkflowTemplate.setId(workflowTemplate.getId());

        partialUpdatedWorkflowTemplate
            .formId(UPDATED_FORM_ID)
            .workflowId(UPDATED_WORKFLOW_ID)
            .workflowTypeId(UPDATED_WORKFLOW_TYPE_ID)
            .workflowTypeName(UPDATED_WORKFLOW_TYPE_NAME)
            .eMobileCreatePageUrl(UPDATED_E_MOBILE_CREATE_PAGE_URL)
            .chatidField(UPDATED_CHATID_FIELD)
            .sourceField(UPDATED_SOURCE_FIELD)
            .commentsField(UPDATED_COMMENTS_FIELD);

        restWorkflowTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflowTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflowTemplate))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
        WorkflowTemplate testWorkflowTemplate = workflowTemplateList.get(workflowTemplateList.size() - 1);
        assertThat(testWorkflowTemplate.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testWorkflowTemplate.getWorkflowId()).isEqualTo(UPDATED_WORKFLOW_ID);
        assertThat(testWorkflowTemplate.getWorkflowName()).isEqualTo(DEFAULT_WORKFLOW_NAME);
        assertThat(testWorkflowTemplate.getWorkflowTypeId()).isEqualTo(UPDATED_WORKFLOW_TYPE_ID);
        assertThat(testWorkflowTemplate.getWorkflowTypeName()).isEqualTo(UPDATED_WORKFLOW_TYPE_NAME);
        assertThat(testWorkflowTemplate.getDdGroupTemplateId()).isEqualTo(DEFAULT_DD_GROUP_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardTemplateId()).isEqualTo(DEFAULT_DD_CARD_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardCallBackKey()).isEqualTo(DEFAULT_DD_CARD_CALL_BACK_KEY);
        assertThat(testWorkflowTemplate.geteMobileCreatePageUrl()).isEqualTo(UPDATED_E_MOBILE_CREATE_PAGE_URL);
        assertThat(testWorkflowTemplate.getChatidField()).isEqualTo(UPDATED_CHATID_FIELD);
        assertThat(testWorkflowTemplate.getSourceField()).isEqualTo(UPDATED_SOURCE_FIELD);
        assertThat(testWorkflowTemplate.getCommentsField()).isEqualTo(UPDATED_COMMENTS_FIELD);
    }

    @Test
    @Transactional
    void fullUpdateWorkflowTemplateWithPatch() throws Exception {
        // Initialize the database
        workflowTemplateRepository.saveAndFlush(workflowTemplate);

        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();

        // Update the workflowTemplate using partial update
        WorkflowTemplate partialUpdatedWorkflowTemplate = new WorkflowTemplate();
        partialUpdatedWorkflowTemplate.setId(workflowTemplate.getId());

        partialUpdatedWorkflowTemplate
            .formId(UPDATED_FORM_ID)
            .workflowId(UPDATED_WORKFLOW_ID)
            .workflowName(UPDATED_WORKFLOW_NAME)
            .workflowTypeId(UPDATED_WORKFLOW_TYPE_ID)
            .workflowTypeName(UPDATED_WORKFLOW_TYPE_NAME)
            .ddGroupTemplateId(UPDATED_DD_GROUP_TEMPLATE_ID)
            .ddCardTemplateId(UPDATED_DD_CARD_TEMPLATE_ID)
            .ddCardCallBackKey(UPDATED_DD_CARD_CALL_BACK_KEY)
            .eMobileCreatePageUrl(UPDATED_E_MOBILE_CREATE_PAGE_URL)
            .chatidField(UPDATED_CHATID_FIELD)
            .sourceField(UPDATED_SOURCE_FIELD)
            .commentsField(UPDATED_COMMENTS_FIELD);

        restWorkflowTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkflowTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkflowTemplate))
            )
            .andExpect(status().isOk());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
        WorkflowTemplate testWorkflowTemplate = workflowTemplateList.get(workflowTemplateList.size() - 1);
        assertThat(testWorkflowTemplate.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testWorkflowTemplate.getWorkflowId()).isEqualTo(UPDATED_WORKFLOW_ID);
        assertThat(testWorkflowTemplate.getWorkflowName()).isEqualTo(UPDATED_WORKFLOW_NAME);
        assertThat(testWorkflowTemplate.getWorkflowTypeId()).isEqualTo(UPDATED_WORKFLOW_TYPE_ID);
        assertThat(testWorkflowTemplate.getWorkflowTypeName()).isEqualTo(UPDATED_WORKFLOW_TYPE_NAME);
        assertThat(testWorkflowTemplate.getDdGroupTemplateId()).isEqualTo(UPDATED_DD_GROUP_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardTemplateId()).isEqualTo(UPDATED_DD_CARD_TEMPLATE_ID);
        assertThat(testWorkflowTemplate.getDdCardCallBackKey()).isEqualTo(UPDATED_DD_CARD_CALL_BACK_KEY);
        assertThat(testWorkflowTemplate.geteMobileCreatePageUrl()).isEqualTo(UPDATED_E_MOBILE_CREATE_PAGE_URL);
        assertThat(testWorkflowTemplate.getChatidField()).isEqualTo(UPDATED_CHATID_FIELD);
        assertThat(testWorkflowTemplate.getSourceField()).isEqualTo(UPDATED_SOURCE_FIELD);
        assertThat(testWorkflowTemplate.getCommentsField()).isEqualTo(UPDATED_COMMENTS_FIELD);
    }

    @Test
    @Transactional
    void patchNonExistingWorkflowTemplate() throws Exception {
        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();
        workflowTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkflowTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workflowTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkflowTemplate() throws Exception {
        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();
        workflowTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkflowTemplate() throws Exception {
        int databaseSizeBeforeUpdate = workflowTemplateRepository.findAll().size();
        workflowTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkflowTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workflowTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkflowTemplate in the database
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkflowTemplate() throws Exception {
        // Initialize the database
        workflowTemplateRepository.saveAndFlush(workflowTemplate);

        int databaseSizeBeforeDelete = workflowTemplateRepository.findAll().size();

        // Delete the workflowTemplate
        restWorkflowTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, workflowTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkflowTemplate> workflowTemplateList = workflowTemplateRepository.findAll();
        assertThat(workflowTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
