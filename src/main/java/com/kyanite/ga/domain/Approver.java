package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.ga.domain.enumeration.ApproverRole;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Approver.
 */
@Entity
@Table(name = "approver")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Approver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "approver_role")
    private ApproverRole approverRole;

    @ManyToOne
    @JsonIgnoreProperties(value = { "approvers", "workflowTemplate", "creator", "publicCardData" }, allowSetters = true)
    private WorkflowInstance workflowInstance;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "privateCardData", "approvers", "operationResults", "createdInstances", "conversations" },
        allowSetters = true
    )
    private DdUser ddUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Approver id(Long id) {
        this.id = id;
        return this;
    }

    public ApproverRole getApproverRole() {
        return this.approverRole;
    }

    public Approver approverRole(ApproverRole approverRole) {
        this.approverRole = approverRole;
        return this;
    }

    public void setApproverRole(ApproverRole approverRole) {
        this.approverRole = approverRole;
    }

    public WorkflowInstance getWorkflowInstance() {
        return this.workflowInstance;
    }

    public Approver workflowInstance(WorkflowInstance workflowInstance) {
        this.setWorkflowInstance(workflowInstance);
        return this;
    }

    public void setWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    public DdUser getDdUser() {
        return this.ddUser;
    }

    public Approver ddUser(DdUser ddUser) {
        this.setDdUser(ddUser);
        return this;
    }

    public void setDdUser(DdUser ddUser) {
        this.ddUser = ddUser;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Approver)) {
            return false;
        }
        return id != null && id.equals(((Approver) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Approver{" +
            "id=" + getId() +
            ", approverRole='" + getApproverRole() + "'" +
            "}";
    }
}