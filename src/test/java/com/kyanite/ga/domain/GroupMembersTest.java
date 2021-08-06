package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupMembersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMembers.class);
        GroupMembers groupMembers1 = new GroupMembers();
        groupMembers1.setId(1L);
        GroupMembers groupMembers2 = new GroupMembers();
        groupMembers2.setId(groupMembers1.getId());
        assertThat(groupMembers1).isEqualTo(groupMembers2);
        groupMembers2.setId(2L);
        assertThat(groupMembers1).isNotEqualTo(groupMembers2);
        groupMembers1.setId(null);
        assertThat(groupMembers1).isNotEqualTo(groupMembers2);
    }
}
