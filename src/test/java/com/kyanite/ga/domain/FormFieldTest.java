package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormFieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormField.class);
        FormField formField1 = new FormField();
        formField1.setId(1L);
        FormField formField2 = new FormField();
        formField2.setId(formField1.getId());
        assertThat(formField1).isEqualTo(formField2);
        formField2.setId(2L);
        assertThat(formField1).isNotEqualTo(formField2);
        formField1.setId(null);
        assertThat(formField1).isNotEqualTo(formField2);
    }
}
