package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicCardDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicCardData.class);
        PublicCardData publicCardData1 = new PublicCardData();
        publicCardData1.setId(1L);
        PublicCardData publicCardData2 = new PublicCardData();
        publicCardData2.setId(publicCardData1.getId());
        assertThat(publicCardData1).isEqualTo(publicCardData2);
        publicCardData2.setId(2L);
        assertThat(publicCardData1).isNotEqualTo(publicCardData2);
        publicCardData1.setId(null);
        assertThat(publicCardData1).isNotEqualTo(publicCardData2);
    }
}
