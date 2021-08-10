package com.kyanite.ga.repository;

import com.kyanite.ga.domain.Conversation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Conversation entity.
 */
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
    @Query(
        value = "select distinct conversation from Conversation conversation left join fetch conversation.ddUsers",
        countQuery = "select count(distinct conversation) from Conversation conversation"
    )
    Page<Conversation> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct conversation from Conversation conversation left join fetch conversation.ddUsers")
    List<Conversation> findAllWithEagerRelationships();

    @Query("select conversation from Conversation conversation left join fetch conversation.ddUsers where conversation.id =:id")
    Optional<Conversation> findOneWithEagerRelationships(@Param("id") String id);
}
