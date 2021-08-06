package com.kyanite.ga.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.ga.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkflowTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkflowTemplate.class);
        WorkflowTemplate workflowTemplate1 = new WorkflowTemplate();
        workflowTemplate1.setId(1L);
        WorkflowTemplate workflowTemplate2 = new WorkflowTemplate();
        workflowTemplate2.setId(workflowTemplate1.getId());
        assertThat(workflowTemplate1).isEqualTo(workflowTemplate2);
        workflowTemplate2.setId(2L);
        assertThat(workflowTemplate1).isNotEqualTo(workflowTemplate2);
        workflowTemplate1.setId(null);
        assertThat(workflowTemplate1).isNotEqualTo(workflowTemplate2);
    }
}
