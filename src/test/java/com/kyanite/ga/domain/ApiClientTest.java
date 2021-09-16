package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiClient.class);
        ApiClient apiClient1 = new ApiClient();
        apiClient1.setId(1L);
        ApiClient apiClient2 = new ApiClient();
        apiClient2.setId(apiClient1.getId());
        assertThat(apiClient1).isEqualTo(apiClient2);
        apiClient2.setId(2L);
        assertThat(apiClient1).isNotEqualTo(apiClient2);
        apiClient1.setId(null);
        assertThat(apiClient1).isNotEqualTo(apiClient2);
    }
}
