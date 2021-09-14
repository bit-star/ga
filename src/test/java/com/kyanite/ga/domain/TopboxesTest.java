package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TopboxesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Topboxes.class);
        Topboxes topboxes1 = new Topboxes();
        topboxes1.setId(1L);
        Topboxes topboxes2 = new Topboxes();
        topboxes2.setId(topboxes1.getId());
        assertThat(topboxes1).isEqualTo(topboxes2);
        topboxes2.setId(2L);
        assertThat(topboxes1).isNotEqualTo(topboxes2);
        topboxes1.setId(null);
        assertThat(topboxes1).isNotEqualTo(topboxes2);
    }
}
