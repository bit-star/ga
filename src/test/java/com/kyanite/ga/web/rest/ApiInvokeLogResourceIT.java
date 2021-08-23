package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.ApiInvokeLog;
import com.kyanite.ga.domain.enumeration.ApiDirection;
import com.kyanite.ga.domain.enumeration.HttpMethod;
import com.kyanite.ga.repository.ApiInvokeLogRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ApiInvokeLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiInvokeLogResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_API_NAME = "AAAAAAAAAA";
    private static final String UPDATED_API_NAME = "BBBBBBBBBB";

    private static final HttpMethod DEFAULT_METHOD = HttpMethod.GET;
    private static final HttpMethod UPDATED_METHOD = HttpMethod.HEAD;

    private static final ApiDirection DEFAULT_DIRECTION = ApiDirection.Enter;
    private static final ApiDirection UPDATED_DIRECTION = ApiDirection.Out;

    private static final Integer DEFAULT_HTTP_STATUS_CODE = 1;
    private static final Integer UPDATED_HTTP_STATUS_CODE = 2;

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REQEUST_DATA = "AAAAAAAAAA";
    private static final String UPDATED_REQEUST_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/api-invoke-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApiInvokeLogRepository apiInvokeLogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiInvokeLogMockMvc;

    private ApiInvokeLog apiInvokeLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiInvokeLog createEntity(EntityManager em) {
        ApiInvokeLog apiInvokeLog = new ApiInvokeLog()
            .login(DEFAULT_LOGIN)
            .apiName(DEFAULT_API_NAME)
            .method(DEFAULT_METHOD)
            .direction(DEFAULT_DIRECTION)
            .httpStatusCode(DEFAULT_HTTP_STATUS_CODE)
            .time(DEFAULT_TIME)
            .reqeustData(DEFAULT_REQEUST_DATA)
            .responseData(DEFAULT_RESPONSE_DATA);
        return apiInvokeLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiInvokeLog createUpdatedEntity(EntityManager em) {
        ApiInvokeLog apiInvokeLog = new ApiInvokeLog()
            .login(UPDATED_LOGIN)
            .apiName(UPDATED_API_NAME)
            .method(UPDATED_METHOD)
            .direction(UPDATED_DIRECTION)
            .httpStatusCode(UPDATED_HTTP_STATUS_CODE)
            .time(UPDATED_TIME)
            .reqeustData(UPDATED_REQEUST_DATA)
            .responseData(UPDATED_RESPONSE_DATA);
        return apiInvokeLog;
    }

    @BeforeEach
    public void initTest() {
        apiInvokeLog = createEntity(em);
    }

    @Test
    @Transactional
    void createApiInvokeLog() throws Exception {
        int databaseSizeBeforeCreate = apiInvokeLogRepository.findAll().size();
        // Create the ApiInvokeLog
        restApiInvokeLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiInvokeLog)))
            .andExpect(status().isCreated());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeCreate + 1);
        ApiInvokeLog testApiInvokeLog = apiInvokeLogList.get(apiInvokeLogList.size() - 1);
        assertThat(testApiInvokeLog.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testApiInvokeLog.getApiName()).isEqualTo(DEFAULT_API_NAME);
        assertThat(testApiInvokeLog.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testApiInvokeLog.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testApiInvokeLog.getHttpStatusCode()).isEqualTo(DEFAULT_HTTP_STATUS_CODE);
        assertThat(testApiInvokeLog.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testApiInvokeLog.getReqeustData()).isEqualTo(DEFAULT_REQEUST_DATA);
        assertThat(testApiInvokeLog.getResponseData()).isEqualTo(DEFAULT_RESPONSE_DATA);
    }

    @Test
    @Transactional
    void createApiInvokeLogWithExistingId() throws Exception {
        // Create the ApiInvokeLog with an existing ID
        apiInvokeLog.setId(1L);

        int databaseSizeBeforeCreate = apiInvokeLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiInvokeLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiInvokeLog)))
            .andExpect(status().isBadRequest());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApiInvokeLogs() throws Exception {
        // Initialize the database
        apiInvokeLogRepository.saveAndFlush(apiInvokeLog);

        // Get all the apiInvokeLogList
        restApiInvokeLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiInvokeLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].apiName").value(hasItem(DEFAULT_API_NAME)))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION.toString())))
            .andExpect(jsonPath("$.[*].httpStatusCode").value(hasItem(DEFAULT_HTTP_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].reqeustData").value(hasItem(DEFAULT_REQEUST_DATA.toString())))
            .andExpect(jsonPath("$.[*].responseData").value(hasItem(DEFAULT_RESPONSE_DATA.toString())));
    }

    @Test
    @Transactional
    void getApiInvokeLog() throws Exception {
        // Initialize the database
        apiInvokeLogRepository.saveAndFlush(apiInvokeLog);

        // Get the apiInvokeLog
        restApiInvokeLogMockMvc
            .perform(get(ENTITY_API_URL_ID, apiInvokeLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiInvokeLog.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.apiName").value(DEFAULT_API_NAME))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION.toString()))
            .andExpect(jsonPath("$.httpStatusCode").value(DEFAULT_HTTP_STATUS_CODE))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.reqeustData").value(DEFAULT_REQEUST_DATA.toString()))
            .andExpect(jsonPath("$.responseData").value(DEFAULT_RESPONSE_DATA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApiInvokeLog() throws Exception {
        // Get the apiInvokeLog
        restApiInvokeLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApiInvokeLog() throws Exception {
        // Initialize the database
        apiInvokeLogRepository.saveAndFlush(apiInvokeLog);

        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();

        // Update the apiInvokeLog
        ApiInvokeLog updatedApiInvokeLog = apiInvokeLogRepository.findById(apiInvokeLog.getId()).get();
        // Disconnect from session so that the updates on updatedApiInvokeLog are not directly saved in db
        em.detach(updatedApiInvokeLog);
        updatedApiInvokeLog
            .login(UPDATED_LOGIN)
            .apiName(UPDATED_API_NAME)
            .method(UPDATED_METHOD)
            .direction(UPDATED_DIRECTION)
            .httpStatusCode(UPDATED_HTTP_STATUS_CODE)
            .time(UPDATED_TIME)
            .reqeustData(UPDATED_REQEUST_DATA)
            .responseData(UPDATED_RESPONSE_DATA);

        restApiInvokeLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApiInvokeLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApiInvokeLog))
            )
            .andExpect(status().isOk());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
        ApiInvokeLog testApiInvokeLog = apiInvokeLogList.get(apiInvokeLogList.size() - 1);
        assertThat(testApiInvokeLog.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testApiInvokeLog.getApiName()).isEqualTo(UPDATED_API_NAME);
        assertThat(testApiInvokeLog.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testApiInvokeLog.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testApiInvokeLog.getHttpStatusCode()).isEqualTo(UPDATED_HTTP_STATUS_CODE);
        assertThat(testApiInvokeLog.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testApiInvokeLog.getReqeustData()).isEqualTo(UPDATED_REQEUST_DATA);
        assertThat(testApiInvokeLog.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
    }

    @Test
    @Transactional
    void putNonExistingApiInvokeLog() throws Exception {
        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();
        apiInvokeLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiInvokeLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiInvokeLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiInvokeLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiInvokeLog() throws Exception {
        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();
        apiInvokeLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiInvokeLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apiInvokeLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiInvokeLog() throws Exception {
        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();
        apiInvokeLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiInvokeLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(apiInvokeLog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiInvokeLogWithPatch() throws Exception {
        // Initialize the database
        apiInvokeLogRepository.saveAndFlush(apiInvokeLog);

        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();

        // Update the apiInvokeLog using partial update
        ApiInvokeLog partialUpdatedApiInvokeLog = new ApiInvokeLog();
        partialUpdatedApiInvokeLog.setId(apiInvokeLog.getId());

        partialUpdatedApiInvokeLog
            .method(UPDATED_METHOD)
            .direction(UPDATED_DIRECTION)
            .time(UPDATED_TIME)
            .responseData(UPDATED_RESPONSE_DATA);

        restApiInvokeLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiInvokeLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiInvokeLog))
            )
            .andExpect(status().isOk());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
        ApiInvokeLog testApiInvokeLog = apiInvokeLogList.get(apiInvokeLogList.size() - 1);
        assertThat(testApiInvokeLog.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testApiInvokeLog.getApiName()).isEqualTo(DEFAULT_API_NAME);
        assertThat(testApiInvokeLog.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testApiInvokeLog.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testApiInvokeLog.getHttpStatusCode()).isEqualTo(DEFAULT_HTTP_STATUS_CODE);
        assertThat(testApiInvokeLog.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testApiInvokeLog.getReqeustData()).isEqualTo(DEFAULT_REQEUST_DATA);
        assertThat(testApiInvokeLog.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
    }

    @Test
    @Transactional
    void fullUpdateApiInvokeLogWithPatch() throws Exception {
        // Initialize the database
        apiInvokeLogRepository.saveAndFlush(apiInvokeLog);

        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();

        // Update the apiInvokeLog using partial update
        ApiInvokeLog partialUpdatedApiInvokeLog = new ApiInvokeLog();
        partialUpdatedApiInvokeLog.setId(apiInvokeLog.getId());

        partialUpdatedApiInvokeLog
            .login(UPDATED_LOGIN)
            .apiName(UPDATED_API_NAME)
            .method(UPDATED_METHOD)
            .direction(UPDATED_DIRECTION)
            .httpStatusCode(UPDATED_HTTP_STATUS_CODE)
            .time(UPDATED_TIME)
            .reqeustData(UPDATED_REQEUST_DATA)
            .responseData(UPDATED_RESPONSE_DATA);

        restApiInvokeLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiInvokeLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApiInvokeLog))
            )
            .andExpect(status().isOk());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
        ApiInvokeLog testApiInvokeLog = apiInvokeLogList.get(apiInvokeLogList.size() - 1);
        assertThat(testApiInvokeLog.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testApiInvokeLog.getApiName()).isEqualTo(UPDATED_API_NAME);
        assertThat(testApiInvokeLog.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testApiInvokeLog.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testApiInvokeLog.getHttpStatusCode()).isEqualTo(UPDATED_HTTP_STATUS_CODE);
        assertThat(testApiInvokeLog.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testApiInvokeLog.getReqeustData()).isEqualTo(UPDATED_REQEUST_DATA);
        assertThat(testApiInvokeLog.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingApiInvokeLog() throws Exception {
        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();
        apiInvokeLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiInvokeLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiInvokeLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiInvokeLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiInvokeLog() throws Exception {
        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();
        apiInvokeLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiInvokeLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apiInvokeLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiInvokeLog() throws Exception {
        int databaseSizeBeforeUpdate = apiInvokeLogRepository.findAll().size();
        apiInvokeLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiInvokeLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(apiInvokeLog))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApiInvokeLog in the database
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiInvokeLog() throws Exception {
        // Initialize the database
        apiInvokeLogRepository.saveAndFlush(apiInvokeLog);

        int databaseSizeBeforeDelete = apiInvokeLogRepository.findAll().size();

        // Delete the apiInvokeLog
        restApiInvokeLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiInvokeLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiInvokeLog> apiInvokeLogList = apiInvokeLogRepository.findAll();
        assertThat(apiInvokeLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
