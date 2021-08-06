package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinkSystemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkSystem.class);
        LinkSystem linkSystem1 = new LinkSystem();
        linkSystem1.setId(1L);
        LinkSystem linkSystem2 = new LinkSystem();
        linkSystem2.setId(linkSystem1.getId());
        assertThat(linkSystem1).isEqualTo(linkSystem2);
        linkSystem2.setId(2L);
        assertThat(linkSystem1).isNotEqualTo(linkSystem2);
        linkSystem1.setId(null);
        assertThat(linkSystem1).isNotEqualTo(linkSystem2);
    }
}
