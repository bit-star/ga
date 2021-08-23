package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlertCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertCard.class);
        AlertCard alertCard1 = new AlertCard();
        alertCard1.setId(1L);
        AlertCard alertCard2 = new AlertCard();
        alertCard2.setId(alertCard1.getId());
        assertThat(alertCard1).isEqualTo(alertCard2);
        alertCard2.setId(2L);
        assertThat(alertCard1).isNotEqualTo(alertCard2);
        alertCard1.setId(null);
        assertThat(alertCard1).isNotEqualTo(alertCard2);
    }
}
