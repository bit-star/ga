package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkflowInstance.
 */
@Entity
@Table(name = "workflow_instance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkflowInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "form")
    private String form;

    @Column(name = "dd_card_id")
    private String ddCardId;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "workflowInstance")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "groupMembers", "privateCardData", "operationResults", "workflowInstance", "createdInstances" },
        allowSetters = true
    )
    private Set<DdUser> approvers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "formFields", "linkSystem", "workflowInstances" }, allowSetters = true)
    private WorkflowTemplate workflowTemplate;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "groupMembers", "privateCardData", "operationResults", "workflowInstance", "createdInstances" },
        allowSetters = true
    )
    private DdUser creator;

    @OneToMany(mappedBy = "workflowInstance")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "privateCardData", "operationResults", "workflowInstance", "conversation", "workflowTemplate" },
        allowSetters = true
    )
    private Set<PublicCardData> publicCardData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowInstance id(Long id) {
        this.id = id;
        return this;
    }

    public String getForm() {
        return this.form;
    }

    public WorkflowInstance form(String form) {
        this.form = form;
        return this;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDdCardId() {
        return this.ddCardId;
    }

    public WorkflowInstance ddCardId(String ddCardId) {
        this.ddCardId = ddCardId;
        return this;
    }

    public void setDdCardId(String ddCardId) {
        this.ddCardId = ddCardId;
    }

    public String getTitle() {
        return this.title;
    }

    public WorkflowInstance title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<DdUser> getApprovers() {
        return this.approvers;
    }

    public WorkflowInstance approvers(Set<DdUser> ddUsers) {
        this.setApprovers(ddUsers);
        return this;
    }

    public WorkflowInstance addApprover(DdUser ddUser) {
        this.approvers.add(ddUser);
        ddUser.setWorkflowInstance(this);
        return this;
    }

    public WorkflowInstance removeApprover(DdUser ddUser) {
        this.approvers.remove(ddUser);
        ddUser.setWorkflowInstance(null);
        return this;
    }

    public void setApprovers(Set<DdUser> ddUsers) {
        if (this.approvers != null) {
            this.approvers.forEach(i -> i.setWorkflowInstance(null));
        }
        if (ddUsers != null) {
            ddUsers.forEach(i -> i.setWorkflowInstance(this));
        }
        this.approvers = ddUsers;
    }

    public WorkflowTemplate getWorkflowTemplate() {
        return this.workflowTemplate;
    }

    public WorkflowInstance workflowTemplate(WorkflowTemplate workflowTemplate) {
        this.setWorkflowTemplate(workflowTemplate);
        return this;
    }

    public void setWorkflowTemplate(WorkflowTemplate workflowTemplate) {
        this.workflowTemplate = workflowTemplate;
    }

    public DdUser getCreator() {
        return this.creator;
    }

    public WorkflowInstance creator(DdUser ddUser) {
        this.setCreator(ddUser);
        return this;
    }

    public void setCreator(DdUser ddUser) {
        this.creator = ddUser;
    }

    public Set<PublicCardData> getPublicCardData() {
        return this.publicCardData;
    }

    public WorkflowInstance publicCardData(Set<PublicCardData> publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public WorkflowInstance addPublicCardData(PublicCardData publicCardData) {
        this.publicCardData.add(publicCardData);
        publicCardData.setWorkflowInstance(this);
        return this;
    }

    public WorkflowInstance removePublicCardData(PublicCardData publicCardData) {
        this.publicCardData.remove(publicCardData);
        publicCardData.setWorkflowInstance(null);
        return this;
    }

    public void setPublicCardData(Set<PublicCardData> publicCardData) {
        if (this.publicCardData != null) {
            this.publicCardData.forEach(i -> i.setWorkflowInstance(null));
        }
        if (publicCardData != null) {
            publicCardData.forEach(i -> i.setWorkflowInstance(this));
        }
        this.publicCardData = publicCardData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkflowInstance)) {
            return false;
        }
        return id != null && id.equals(((WorkflowInstance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkflowInstance{" +
            "id=" + getId() +
            ", form='" + getForm() + "'" +
            ", ddCardId='" + getDdCardId() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
