package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkflowTemplate.
 */
@Entity
@Table(name = "workflow_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkflowTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_id")
    private String formId;

    @Column(name = "workflow_id")
    private String workflowId;

    @Column(name = "workflow_name")
    private String workflowName;

    @Column(name = "workflow_type_id")
    private String workflowTypeId;

    @Column(name = "workflow_type_name")
    private String workflowTypeName;

    @Column(name = "dd_group_template_id")
    private String ddGroupTemplateId;

    @Column(name = "dd_card_template_id")
    private String ddCardTemplateId;

    @Column(name = "dd_card_call_back_key")
    private String ddCardCallBackKey;

    @Column(name = "e_mobile_create_page_url")
    private String eMobileCreatePageUrl;

    @Column(name = "chatid_field")
    private String chatidField;

    @Column(name = "source_field")
    private String sourceField;

    @Column(name = "comments_field")
    private String commentsField;

    @OneToMany(mappedBy = "workflowTemplate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "privateCardData", "operationResults", "workflowInstance", "conversation", "workflowTemplate" },
        allowSetters = true
    )
    private Set<PublicCardData> publicCardData = new HashSet<>();

    @OneToMany(mappedBy = "workflowTemplate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflowTemplate" }, allowSetters = true)
    private Set<FormField> formFields = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "workflowTemplates" }, allowSetters = true)
    private LinkSystem linkSystem;

    @OneToMany(mappedBy = "workflowTemplate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "approvers", "workflowTemplate", "creator", "publicCardData" }, allowSetters = true)
    private Set<WorkflowInstance> workflowInstances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowTemplate id(Long id) {
        this.id = id;
        return this;
    }

    public String getFormId() {
        return this.formId;
    }

    public WorkflowTemplate formId(String formId) {
        this.formId = formId;
        return this;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getWorkflowId() {
        return this.workflowId;
    }

    public WorkflowTemplate workflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return this.workflowName;
    }

    public WorkflowTemplate workflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowTypeId() {
        return this.workflowTypeId;
    }

    public WorkflowTemplate workflowTypeId(String workflowTypeId) {
        this.workflowTypeId = workflowTypeId;
        return this;
    }

    public void setWorkflowTypeId(String workflowTypeId) {
        this.workflowTypeId = workflowTypeId;
    }

    public String getWorkflowTypeName() {
        return this.workflowTypeName;
    }

    public WorkflowTemplate workflowTypeName(String workflowTypeName) {
        this.workflowTypeName = workflowTypeName;
        return this;
    }

    public void setWorkflowTypeName(String workflowTypeName) {
        this.workflowTypeName = workflowTypeName;
    }

    public String getDdGroupTemplateId() {
        return this.ddGroupTemplateId;
    }

    public WorkflowTemplate ddGroupTemplateId(String ddGroupTemplateId) {
        this.ddGroupTemplateId = ddGroupTemplateId;
        return this;
    }

    public void setDdGroupTemplateId(String ddGroupTemplateId) {
        this.ddGroupTemplateId = ddGroupTemplateId;
    }

    public String getDdCardTemplateId() {
        return this.ddCardTemplateId;
    }

    public WorkflowTemplate ddCardTemplateId(String ddCardTemplateId) {
        this.ddCardTemplateId = ddCardTemplateId;
        return this;
    }

    public void setDdCardTemplateId(String ddCardTemplateId) {
        this.ddCardTemplateId = ddCardTemplateId;
    }

    public String getDdCardCallBackKey() {
        return this.ddCardCallBackKey;
    }

    public WorkflowTemplate ddCardCallBackKey(String ddCardCallBackKey) {
        this.ddCardCallBackKey = ddCardCallBackKey;
        return this;
    }

    public void setDdCardCallBackKey(String ddCardCallBackKey) {
        this.ddCardCallBackKey = ddCardCallBackKey;
    }

    public String geteMobileCreatePageUrl() {
        return this.eMobileCreatePageUrl;
    }

    public WorkflowTemplate eMobileCreatePageUrl(String eMobileCreatePageUrl) {
        this.eMobileCreatePageUrl = eMobileCreatePageUrl;
        return this;
    }

    public void seteMobileCreatePageUrl(String eMobileCreatePageUrl) {
        this.eMobileCreatePageUrl = eMobileCreatePageUrl;
    }

    public String getChatidField() {
        return this.chatidField;
    }

    public WorkflowTemplate chatidField(String chatidField) {
        this.chatidField = chatidField;
        return this;
    }

    public void setChatidField(String chatidField) {
        this.chatidField = chatidField;
    }

    public String getSourceField() {
        return this.sourceField;
    }

    public WorkflowTemplate sourceField(String sourceField) {
        this.sourceField = sourceField;
        return this;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public String getCommentsField() {
        return this.commentsField;
    }

    public WorkflowTemplate commentsField(String commentsField) {
        this.commentsField = commentsField;
        return this;
    }

    public void setCommentsField(String commentsField) {
        this.commentsField = commentsField;
    }

    public Set<PublicCardData> getPublicCardData() {
        return this.publicCardData;
    }

    public WorkflowTemplate publicCardData(Set<PublicCardData> publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public WorkflowTemplate addPublicCardData(PublicCardData publicCardData) {
        this.publicCardData.add(publicCardData);
        publicCardData.setWorkflowTemplate(this);
        return this;
    }

    public WorkflowTemplate removePublicCardData(PublicCardData publicCardData) {
        this.publicCardData.remove(publicCardData);
        publicCardData.setWorkflowTemplate(null);
        return this;
    }

    public void setPublicCardData(Set<PublicCardData> publicCardData) {
        if (this.publicCardData != null) {
            this.publicCardData.forEach(i -> i.setWorkflowTemplate(null));
        }
        if (publicCardData != null) {
            publicCardData.forEach(i -> i.setWorkflowTemplate(this));
        }
        this.publicCardData = publicCardData;
    }

    public Set<FormField> getFormFields() {
        return this.formFields;
    }

    public WorkflowTemplate formFields(Set<FormField> formFields) {
        this.setFormFields(formFields);
        return this;
    }

    public WorkflowTemplate addFormField(FormField formField) {
        this.formFields.add(formField);
        formField.setWorkflowTemplate(this);
        return this;
    }

    public WorkflowTemplate removeFormField(FormField formField) {
        this.formFields.remove(formField);
        formField.setWorkflowTemplate(null);
        return this;
    }

    public void setFormFields(Set<FormField> formFields) {
        if (this.formFields != null) {
            this.formFields.forEach(i -> i.setWorkflowTemplate(null));
        }
        if (formFields != null) {
            formFields.forEach(i -> i.setWorkflowTemplate(this));
        }
        this.formFields = formFields;
    }

    public LinkSystem getLinkSystem() {
        return this.linkSystem;
    }

    public WorkflowTemplate linkSystem(LinkSystem linkSystem) {
        this.setLinkSystem(linkSystem);
        return this;
    }

    public void setLinkSystem(LinkSystem linkSystem) {
        this.linkSystem = linkSystem;
    }

    public Set<WorkflowInstance> getWorkflowInstances() {
        return this.workflowInstances;
    }

    public WorkflowTemplate workflowInstances(Set<WorkflowInstance> workflowInstances) {
        this.setWorkflowInstances(workflowInstances);
        return this;
    }

    public WorkflowTemplate addWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstances.add(workflowInstance);
        workflowInstance.setWorkflowTemplate(this);
        return this;
    }

    public WorkflowTemplate removeWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstances.remove(workflowInstance);
        workflowInstance.setWorkflowTemplate(null);
        return this;
    }

    public void setWorkflowInstances(Set<WorkflowInstance> workflowInstances) {
        if (this.workflowInstances != null) {
            this.workflowInstances.forEach(i -> i.setWorkflowTemplate(null));
        }
        if (workflowInstances != null) {
            workflowInstances.forEach(i -> i.setWorkflowTemplate(this));
        }
        this.workflowInstances = workflowInstances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkflowTemplate)) {
            return false;
        }
        return id != null && id.equals(((WorkflowTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkflowTemplate{" +
            "id=" + getId() +
            ", formId='" + getFormId() + "'" +
            ", workflowId='" + getWorkflowId() + "'" +
            ", workflowName='" + getWorkflowName() + "'" +
            ", workflowTypeId='" + getWorkflowTypeId() + "'" +
            ", workflowTypeName='" + getWorkflowTypeName() + "'" +
            ", ddGroupTemplateId='" + getDdGroupTemplateId() + "'" +
            ", ddCardTemplateId='" + getDdCardTemplateId() + "'" +
            ", ddCardCallBackKey='" + getDdCardCallBackKey() + "'" +
            ", eMobileCreatePageUrl='" + geteMobileCreatePageUrl() + "'" +
            ", chatidField='" + getChatidField() + "'" +
            ", sourceField='" + getSourceField() + "'" +
            ", commentsField='" + getCommentsField() + "'" +
            "}";
    }
}
