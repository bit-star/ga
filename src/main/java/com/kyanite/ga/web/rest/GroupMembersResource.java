package com.kyanite.ga.web.rest;

import com.kyanite.ga.domain.GroupMembers;
import com.kyanite.ga.repository.GroupMembersRepository;
import com.kyanite.ga.service.GroupMembersService;
import com.kyanite.ga.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kyanite.ga.domain.GroupMembers}.
 */
@RestController
@RequestMapping("/api")
public class GroupMembersResource {

    private final Logger log = LoggerFactory.getLogger(GroupMembersResource.class);

    private static final String ENTITY_NAME = "groupMembers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupMembersService groupMembersService;

    private final GroupMembersRepository groupMembersRepository;

    public GroupMembersResource(GroupMembersService groupMembersService, GroupMembersRepository groupMembersRepository) {
        this.groupMembersService = groupMembersService;
        this.groupMembersRepository = groupMembersRepository;
    }

    /**
     * {@code POST  /group-members} : Create a new groupMembers.
     *
     * @param groupMembers the groupMembers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupMembers, or with status {@code 400 (Bad Request)} if the groupMembers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-members")
    public ResponseEntity<GroupMembers> createGroupMembers(@RequestBody GroupMembers groupMembers) throws URISyntaxException {
        log.debug("REST request to save GroupMembers : {}", groupMembers);
        if (groupMembers.getId() != null) {
            throw new BadRequestAlertException("A new groupMembers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupMembers result = groupMembersService.save(groupMembers);
        return ResponseEntity
            .created(new URI("/api/group-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-members/:id} : Updates an existing groupMembers.
     *
     * @param id the id of the groupMembers to save.
     * @param groupMembers the groupMembers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupMembers,
     * or with status {@code 400 (Bad Request)} if the groupMembers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupMembers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-members/{id}")
    public ResponseEntity<GroupMembers> updateGroupMembers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupMembers groupMembers
    ) throws URISyntaxException {
        log.debug("REST request to update GroupMembers : {}, {}", id, groupMembers);
        if (groupMembers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupMembers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupMembersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroupMembers result = groupMembersService.save(groupMembers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupMembers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /group-members/:id} : Partial updates given fields of an existing groupMembers, field will ignore if it is null
     *
     * @param id the id of the groupMembers to save.
     * @param groupMembers the groupMembers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupMembers,
     * or with status {@code 400 (Bad Request)} if the groupMembers is not valid,
     * or with status {@code 404 (Not Found)} if the groupMembers is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupMembers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/group-members/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GroupMembers> partialUpdateGroupMembers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupMembers groupMembers
    ) throws URISyntaxException {
        log.debug("REST request to partial update GroupMembers partially : {}, {}", id, groupMembers);
        if (groupMembers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupMembers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupMembersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroupMembers> result = groupMembersService.partialUpdate(groupMembers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupMembers.getId().toString())
        );
    }

    /**
     * {@code GET  /group-members} : get all the groupMembers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupMembers in body.
     */
    @GetMapping("/group-members")
    public List<GroupMembers> getAllGroupMembers() {
        log.debug("REST request to get all GroupMembers");
        return groupMembersService.findAll();
    }

    /**
     * {@code GET  /group-members/:id} : get the "id" groupMembers.
     *
     * @param id the id of the groupMembers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupMembers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-members/{id}")
    public ResponseEntity<GroupMembers> getGroupMembers(@PathVariable Long id) {
        log.debug("REST request to get GroupMembers : {}", id);
        Optional<GroupMembers> groupMembers = groupMembersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupMembers);
    }

    /**
     * {@code DELETE  /group-members/:id} : delete the "id" groupMembers.
     *
     * @param id the id of the groupMembers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-members/{id}")
    public ResponseEntity<Void> deleteGroupMembers(@PathVariable Long id) {
        log.debug("REST request to delete GroupMembers : {}", id);
        groupMembersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
