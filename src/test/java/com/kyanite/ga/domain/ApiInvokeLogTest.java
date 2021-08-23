package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiInvokeLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiInvokeLog.class);
        ApiInvokeLog apiInvokeLog1 = new ApiInvokeLog();
        apiInvokeLog1.setId(1L);
        ApiInvokeLog apiInvokeLog2 = new ApiInvokeLog();
        apiInvokeLog2.setId(apiInvokeLog1.getId());
        assertThat(apiInvokeLog1).isEqualTo(apiInvokeLog2);
        apiInvokeLog2.setId(2L);
        assertThat(apiInvokeLog1).isNotEqualTo(apiInvokeLog2);
        apiInvokeLog1.setId(null);
        assertThat(apiInvokeLog1).isNotEqualTo(apiInvokeLog2);
    }
}
