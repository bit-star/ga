package com.kyanite.ga.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.ga.IntegrationTest;
import com.kyanite.ga.domain.GroupMembers;
import com.kyanite.ga.domain.enumeration.GroupRole;
import com.kyanite.ga.repository.GroupMembersRepository;
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
 * Integration tests for the {@link GroupMembersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroupMembersResourceIT {

    private static final GroupRole DEFAULT_GROUP_ROLE = GroupRole.Ang;
    private static final GroupRole UPDATED_GROUP_ROLE = GroupRole.Refuse;

    private static final String ENTITY_API_URL = "/api/group-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupMembersRepository groupMembersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupMembersMockMvc;

    private GroupMembers groupMembers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMembers createEntity(EntityManager em) {
        GroupMembers groupMembers = new GroupMembers().groupRole(DEFAULT_GROUP_ROLE);
        return groupMembers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMembers createUpdatedEntity(EntityManager em) {
        GroupMembers groupMembers = new GroupMembers().groupRole(UPDATED_GROUP_ROLE);
        return groupMembers;
    }

    @BeforeEach
    public void initTest() {
        groupMembers = createEntity(em);
    }

    @Test
    @Transactional
    void createGroupMembers() throws Exception {
        int databaseSizeBeforeCreate = groupMembersRepository.findAll().size();
        // Create the GroupMembers
        restGroupMembersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isCreated());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeCreate + 1);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getGroupRole()).isEqualTo(DEFAULT_GROUP_ROLE);
    }

    @Test
    @Transactional
    void createGroupMembersWithExistingId() throws Exception {
        // Create the GroupMembers with an existing ID
        groupMembers.setId(1L);

        int databaseSizeBeforeCreate = groupMembersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMembersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList
        restGroupMembersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMembers.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupRole").value(hasItem(DEFAULT_GROUP_ROLE.toString())));
    }

    @Test
    @Transactional
    void getGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get the groupMembers
        restGroupMembersMockMvc
            .perform(get(ENTITY_API_URL_ID, groupMembers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupMembers.getId().intValue()))
            .andExpect(jsonPath("$.groupRole").value(DEFAULT_GROUP_ROLE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGroupMembers() throws Exception {
        // Get the groupMembers
        restGroupMembersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();

        // Update the groupMembers
        GroupMembers updatedGroupMembers = groupMembersRepository.findById(groupMembers.getId()).get();
        // Disconnect from session so that the updates on updatedGroupMembers are not directly saved in db
        em.detach(updatedGroupMembers);
        updatedGroupMembers.groupRole(UPDATED_GROUP_ROLE);

        restGroupMembersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGroupMembers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGroupMembers))
            )
            .andExpect(status().isOk());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getGroupRole()).isEqualTo(UPDATED_GROUP_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();
        groupMembers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMembersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupMembers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupMembers))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();
        groupMembers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMembersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupMembers))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();
        groupMembers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMembersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroupMembersWithPatch() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();

        // Update the groupMembers using partial update
        GroupMembers partialUpdatedGroupMembers = new GroupMembers();
        partialUpdatedGroupMembers.setId(groupMembers.getId());

        restGroupMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupMembers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupMembers))
            )
            .andExpect(status().isOk());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getGroupRole()).isEqualTo(DEFAULT_GROUP_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateGroupMembersWithPatch() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();

        // Update the groupMembers using partial update
        GroupMembers partialUpdatedGroupMembers = new GroupMembers();
        partialUpdatedGroupMembers.setId(groupMembers.getId());

        partialUpdatedGroupMembers.groupRole(UPDATED_GROUP_ROLE);

        restGroupMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupMembers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupMembers))
            )
            .andExpect(status().isOk());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getGroupRole()).isEqualTo(UPDATED_GROUP_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();
        groupMembers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupMembers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupMembers))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();
        groupMembers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMembersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupMembers))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();
        groupMembers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupMembersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(groupMembers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        int databaseSizeBeforeDelete = groupMembersRepository.findAll().size();

        // Delete the groupMembers
        restGroupMembersMockMvc
            .perform(delete(ENTITY_API_URL_ID, groupMembers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
