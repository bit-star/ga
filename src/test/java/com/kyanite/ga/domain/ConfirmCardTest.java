package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfirmCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfirmCard.class);
        ConfirmCard confirmCard1 = new ConfirmCard();
        confirmCard1.setId(1L);
        ConfirmCard confirmCard2 = new ConfirmCard();
        confirmCard2.setId(confirmCard1.getId());
        assertThat(confirmCard1).isEqualTo(confirmCard2);
        confirmCard2.setId(2L);
        assertThat(confirmCard1).isNotEqualTo(confirmCard2);
        confirmCard1.setId(null);
        assertThat(confirmCard1).isNotEqualTo(confirmCard2);
    }
}
