package com.kyanite.ga.service;

import com.kyanite.ga.domain.Conversation;
import com.kyanite.ga.repository.ConversationRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Conversation}.
 */
@Service
@Transactional
public class ConversationService {

    private final Logger log = LoggerFactory.getLogger(ConversationService.class);

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    /**
     * Save a conversation.
     *
     * @param conversation the entity to save.
     * @return the persisted entity.
     */
    public Conversation save(Conversation conversation) {
        log.debug("Request to save Conversation : {}", conversation);
        return conversationRepository.save(conversation);
    }

    /**
     * Partially update a conversation.
     *
     * @param conversation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Conversation> partialUpdate(Conversation conversation) {
        log.debug("Request to partially update Conversation : {}", conversation);

        return conversationRepository
            .findById(conversation.getId())
            .map(
                existingConversation -> {
                    if (conversation.getName() != null) {
                        existingConversation.setName(conversation.getName());
                    }
                    if (conversation.getTitle() != null) {
                        existingConversation.setTitle(conversation.getTitle());
                    }
                    if (conversation.getOwner() != null) {
                        existingConversation.setOwner(conversation.getOwner());
                    }
                    if (conversation.getOwnerUserId() != null) {
                        existingConversation.setOwnerUserId(conversation.getOwnerUserId());
                    }
                    if (conversation.getChatid() != null) {
                        existingConversation.setChatid(conversation.getChatid());
                    }
                    if (conversation.getOpenConversationId() != null) {
                        existingConversation.setOpenConversationId(conversation.getOpenConversationId());
                    }
                    if (conversation.getConversationTag() != null) {
                        existingConversation.setConversationTag(conversation.getConversationTag());
                    }
                    if (conversation.getUseridlist() != null) {
                        existingConversation.setUseridlist(conversation.getUseridlist());
                    }
                    if (conversation.getUuid() != null) {
                        existingConversation.setUuid(conversation.getUuid());
                    }
                    if (conversation.getIcon() != null) {
                        existingConversation.setIcon(conversation.getIcon());
                    }
                    if (conversation.getShowHistoryType() != null) {
                        existingConversation.setShowHistoryType(conversation.getShowHistoryType());
                    }
                    if (conversation.getSearchable() != null) {
                        existingConversation.setSearchable(conversation.getSearchable());
                    }
                    if (conversation.getValidationType() != null) {
                        existingConversation.setValidationType(conversation.getValidationType());
                    }
                    if (conversation.getChatBannedType() != null) {
                        existingConversation.setChatBannedType(conversation.getChatBannedType());
                    }
                    if (conversation.getMentionAllAuthority() != null) {
                        existingConversation.setMentionAllAuthority(conversation.getMentionAllAuthority());
                    }
                    if (conversation.getManagementType() != null) {
                        existingConversation.setManagementType(conversation.getManagementType());
                    }
                    if (conversation.getTemplateId() != null) {
                        existingConversation.setTemplateId(conversation.getTemplateId());
                    }
                    if (conversation.getOfficialGroup() != null) {
                        existingConversation.setOfficialGroup(conversation.getOfficialGroup());
                    }
                    if (conversation.getEnableScenegroup() != null) {
                        existingConversation.setEnableScenegroup(conversation.getEnableScenegroup());
                    }
                    if (conversation.getGroupUrl() != null) {
                        existingConversation.setGroupUrl(conversation.getGroupUrl());
                    }
                    if (conversation.getTime() != null) {
                        existingConversation.setTime(conversation.getTime());
                    }

                    return existingConversation;
                }
            )
            .map(conversationRepository::save);
    }

    /**
     * Get all the conversations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Conversation> findAll() {
        log.debug("Request to get all Conversations");
        return conversationRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the conversations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Conversation> findAllWithEagerRelationships(Pageable pageable) {
        return conversationRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one conversation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Conversation> findOne(String id) {
        log.debug("Request to get Conversation : {}", id);
        return conversationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the conversation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Conversation : {}", id);
        conversationRepository.deleteById(id);
    }
}
