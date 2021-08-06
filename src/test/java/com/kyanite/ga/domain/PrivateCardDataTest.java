package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrivateCardDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrivateCardData.class);
        PrivateCardData privateCardData1 = new PrivateCardData();
        privateCardData1.setId(1L);
        PrivateCardData privateCardData2 = new PrivateCardData();
        privateCardData2.setId(privateCardData1.getId());
        assertThat(privateCardData1).isEqualTo(privateCardData2);
        privateCardData2.setId(2L);
        assertThat(privateCardData1).isNotEqualTo(privateCardData2);
        privateCardData1.setId(null);
        assertThat(privateCardData1).isNotEqualTo(privateCardData2);
    }
}
