package com.kyanite.ga.service;

import com.kyanite.ga.domain.Conversation;
import com.kyanite.ga.repository.ConversationRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        return conversationRepository.findAll();
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
        return conversationRepository.findById(id);
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
