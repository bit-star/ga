package com.kyanite.ga.service;

import com.kyanite.ga.domain.GroupMembers;
import com.kyanite.ga.repository.GroupMembersRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GroupMembers}.
 */
@Service
@Transactional
public class GroupMembersService {

    private final Logger log = LoggerFactory.getLogger(GroupMembersService.class);

    private final GroupMembersRepository groupMembersRepository;

    public GroupMembersService(GroupMembersRepository groupMembersRepository) {
        this.groupMembersRepository = groupMembersRepository;
    }

    /**
     * Save a groupMembers.
     *
     * @param groupMembers the entity to save.
     * @return the persisted entity.
     */
    public GroupMembers save(GroupMembers groupMembers) {
        log.debug("Request to save GroupMembers : {}", groupMembers);
        return groupMembersRepository.save(groupMembers);
    }

    /**
     * Partially update a groupMembers.
     *
     * @param groupMembers the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GroupMembers> partialUpdate(GroupMembers groupMembers) {
        log.debug("Request to partially update GroupMembers : {}", groupMembers);

        return groupMembersRepository
            .findById(groupMembers.getId())
            .map(
                existingGroupMembers -> {
                    if (groupMembers.getGroupRole() != null) {
                        existingGroupMembers.setGroupRole(groupMembers.getGroupRole());
                    }

                    return existingGroupMembers;
                }
            )
            .map(groupMembersRepository::save);
    }

    /**
     * Get all the groupMembers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GroupMembers> findAll() {
        log.debug("Request to get all GroupMembers");
        return groupMembersRepository.findAll();
    }

    /**
     * Get one groupMembers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GroupMembers> findOne(Long id) {
        log.debug("Request to get GroupMembers : {}", id);
        return groupMembersRepository.findById(id);
    }

    /**
     * Delete the groupMembers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupMembers : {}", id);
        groupMembersRepository.deleteById(id);
    }
}
