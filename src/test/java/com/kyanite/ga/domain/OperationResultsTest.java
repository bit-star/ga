package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationResultsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationResults.class);
        OperationResults operationResults1 = new OperationResults();
        operationResults1.setId(1L);
        OperationResults operationResults2 = new OperationResults();
        operationResults2.setId(operationResults1.getId());
        assertThat(operationResults1).isEqualTo(operationResults2);
        operationResults2.setId(2L);
        assertThat(operationResults1).isNotEqualTo(operationResults2);
        operationResults1.setId(null);
        assertThat(operationResults1).isNotEqualTo(operationResults2);
    }
}
