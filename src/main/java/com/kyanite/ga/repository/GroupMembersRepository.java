package com.kyanite.ga.repository;

import com.kyanite.ga.domain.GroupMembers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GroupMembers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long> {}
