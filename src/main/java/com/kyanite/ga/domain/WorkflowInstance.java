package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.ga.domain.enumeration.WorkflowInstanceStatus;
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
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "form")
    private String form;

    @Column(name = "dd_card_id")
    private String ddCardId;

    @Column(name = "title")
    private String title;

    @Column(name = "dd_card_template_id")
    private String ddCardTemplateId;

    @Column(name = "dd_card_call_back_key")
    private String ddCardCallBackKey;

    @Column(name = "request_id")
    private String requestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkflowInstanceStatus status;

    @OneToMany(mappedBy = "workflowInstance")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflowInstance", "ddUser" }, allowSetters = true)
    private Set<Approver> approvers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "formFields", "linkSystem", "workflowInstances" }, allowSetters = true)
    private WorkflowTemplate workflowTemplate;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "privateCardData", "approvers", "operationResults", "createdInstances", "conversations" },
        allowSetters = true
    )
    private DdUser creator;

    @OneToMany(mappedBy = "workflowInstance")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "topboxes", "privateCardData", "operationResults", "confirmCards", "alertCards", "workflowInstance", "conversation" },
        allowSetters = true
    )
    private Set<PublicCardData> publicCardData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkflowInstance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForm() {
        return this.form;
    }

    public WorkflowInstance form(String form) {
        this.setForm(form);
        return this;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDdCardId() {
        return this.ddCardId;
    }

    public WorkflowInstance ddCardId(String ddCardId) {
        this.setDdCardId(ddCardId);
        return this;
    }

    public void setDdCardId(String ddCardId) {
        this.ddCardId = ddCardId;
    }

    public String getTitle() {
        return this.title;
    }

    public WorkflowInstance title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDdCardTemplateId() {
        return this.ddCardTemplateId;
    }

    public WorkflowInstance ddCardTemplateId(String ddCardTemplateId) {
        this.setDdCardTemplateId(ddCardTemplateId);
        return this;
    }

    public void setDdCardTemplateId(String ddCardTemplateId) {
        this.ddCardTemplateId = ddCardTemplateId;
    }

    public String getDdCardCallBackKey() {
        return this.ddCardCallBackKey;
    }

    public WorkflowInstance ddCardCallBackKey(String ddCardCallBackKey) {
        this.setDdCardCallBackKey(ddCardCallBackKey);
        return this;
    }

    public void setDdCardCallBackKey(String ddCardCallBackKey) {
        this.ddCardCallBackKey = ddCardCallBackKey;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public WorkflowInstance requestId(String requestId) {
        this.setRequestId(requestId);
        return this;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public WorkflowInstanceStatus getStatus() {
        return this.status;
    }

    public WorkflowInstance status(WorkflowInstanceStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(WorkflowInstanceStatus status) {
        this.status = status;
    }

    public Set<Approver> getApprovers() {
        return this.approvers;
    }

    public void setApprovers(Set<Approver> approvers) {
        if (this.approvers != null) {
            this.approvers.forEach(i -> i.setWorkflowInstance(null));
        }
        if (approvers != null) {
            approvers.forEach(i -> i.setWorkflowInstance(this));
        }
        this.approvers = approvers;
    }

    public WorkflowInstance approvers(Set<Approver> approvers) {
        this.setApprovers(approvers);
        return this;
    }

    public WorkflowInstance addApprover(Approver approver) {
        this.approvers.add(approver);
        approver.setWorkflowInstance(this);
        return this;
    }

    public WorkflowInstance removeApprover(Approver approver) {
        this.approvers.remove(approver);
        approver.setWorkflowInstance(null);
        return this;
    }

    public WorkflowTemplate getWorkflowTemplate() {
        return this.workflowTemplate;
    }

    public void setWorkflowTemplate(WorkflowTemplate workflowTemplate) {
        this.workflowTemplate = workflowTemplate;
    }

    public WorkflowInstance workflowTemplate(WorkflowTemplate workflowTemplate) {
        this.setWorkflowTemplate(workflowTemplate);
        return this;
    }

    public DdUser getCreator() {
        return this.creator;
    }

    public void setCreator(DdUser ddUser) {
        this.creator = ddUser;
    }

    public WorkflowInstance creator(DdUser ddUser) {
        this.setCreator(ddUser);
        return this;
    }

    public Set<PublicCardData> getPublicCardData() {
        return this.publicCardData;
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
            ", ddCardTemplateId='" + getDdCardTemplateId() + "'" +
            ", ddCardCallBackKey='" + getDdCardCallBackKey() + "'" +
            ", requestId='" + getRequestId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
