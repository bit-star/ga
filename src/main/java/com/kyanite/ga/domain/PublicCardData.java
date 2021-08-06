package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PublicCardData.
 */
@Entity
@Table(name = "public_card_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PublicCardData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fee")
    private String fee;

    @Column(name = "reason")
    private String reason;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "types_of_fee")
    private String typesOfFee;

    @Column(name = "agree")
    private Boolean agree;

    @Column(name = "requestid")
    private Long requestid;

    @Column(name = "workflowid")
    private Long workflowid;

    @Column(name = "valid")
    private Boolean valid;

    @OneToMany(mappedBy = "publicCardData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicCardData", "ddUser" }, allowSetters = true)
    private Set<PrivateCardData> privateCardData = new HashSet<>();

    @OneToMany(mappedBy = "publicCardData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ddUser", "publicCardData" }, allowSetters = true)
    private Set<OperationResults> operationResults = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "groupMembers" }, allowSetters = true)
    private Conversation conversation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "formFields", "linkSystem", "workflowInstances" }, allowSetters = true)
    private WorkflowTemplate workflowTemplate;

    @OneToMany(mappedBy = "publicCardData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ddUsers", "workflowTemplate", "publicCardData" }, allowSetters = true)
    private Set<WorkflowInstance> workflowInstances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublicCardData id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PublicCardData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee() {
        return this.fee;
    }

    public PublicCardData fee(String fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getReason() {
        return this.reason;
    }

    public PublicCardData reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getItemType() {
        return this.itemType;
    }

    public PublicCardData itemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTypesOfFee() {
        return this.typesOfFee;
    }

    public PublicCardData typesOfFee(String typesOfFee) {
        this.typesOfFee = typesOfFee;
        return this;
    }

    public void setTypesOfFee(String typesOfFee) {
        this.typesOfFee = typesOfFee;
    }

    public Boolean getAgree() {
        return this.agree;
    }

    public PublicCardData agree(Boolean agree) {
        this.agree = agree;
        return this;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public Long getRequestid() {
        return this.requestid;
    }

    public PublicCardData requestid(Long requestid) {
        this.requestid = requestid;
        return this;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public Long getWorkflowid() {
        return this.workflowid;
    }

    public PublicCardData workflowid(Long workflowid) {
        this.workflowid = workflowid;
        return this;
    }

    public void setWorkflowid(Long workflowid) {
        this.workflowid = workflowid;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public PublicCardData valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Set<PrivateCardData> getPrivateCardData() {
        return this.privateCardData;
    }

    public PublicCardData privateCardData(Set<PrivateCardData> privateCardData) {
        this.setPrivateCardData(privateCardData);
        return this;
    }

    public PublicCardData addPrivateCardData(PrivateCardData privateCardData) {
        this.privateCardData.add(privateCardData);
        privateCardData.setPublicCardData(this);
        return this;
    }

    public PublicCardData removePrivateCardData(PrivateCardData privateCardData) {
        this.privateCardData.remove(privateCardData);
        privateCardData.setPublicCardData(null);
        return this;
    }

    public void setPrivateCardData(Set<PrivateCardData> privateCardData) {
        if (this.privateCardData != null) {
            this.privateCardData.forEach(i -> i.setPublicCardData(null));
        }
        if (privateCardData != null) {
            privateCardData.forEach(i -> i.setPublicCardData(this));
        }
        this.privateCardData = privateCardData;
    }

    public Set<OperationResults> getOperationResults() {
        return this.operationResults;
    }

    public PublicCardData operationResults(Set<OperationResults> operationResults) {
        this.setOperationResults(operationResults);
        return this;
    }

    public PublicCardData addOperationResults(OperationResults operationResults) {
        this.operationResults.add(operationResults);
        operationResults.setPublicCardData(this);
        return this;
    }

    public PublicCardData removeOperationResults(OperationResults operationResults) {
        this.operationResults.remove(operationResults);
        operationResults.setPublicCardData(null);
        return this;
    }

    public void setOperationResults(Set<OperationResults> operationResults) {
        if (this.operationResults != null) {
            this.operationResults.forEach(i -> i.setPublicCardData(null));
        }
        if (operationResults != null) {
            operationResults.forEach(i -> i.setPublicCardData(this));
        }
        this.operationResults = operationResults;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public PublicCardData conversation(Conversation conversation) {
        this.setConversation(conversation);
        return this;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public WorkflowTemplate getWorkflowTemplate() {
        return this.workflowTemplate;
    }

    public PublicCardData workflowTemplate(WorkflowTemplate workflowTemplate) {
        this.setWorkflowTemplate(workflowTemplate);
        return this;
    }

    public void setWorkflowTemplate(WorkflowTemplate workflowTemplate) {
        this.workflowTemplate = workflowTemplate;
    }

    public Set<WorkflowInstance> getWorkflowInstances() {
        return this.workflowInstances;
    }

    public PublicCardData workflowInstances(Set<WorkflowInstance> workflowInstances) {
        this.setWorkflowInstances(workflowInstances);
        return this;
    }

    public PublicCardData addWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstances.add(workflowInstance);
        workflowInstance.setPublicCardData(this);
        return this;
    }

    public PublicCardData removeWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstances.remove(workflowInstance);
        workflowInstance.setPublicCardData(null);
        return this;
    }

    public void setWorkflowInstances(Set<WorkflowInstance> workflowInstances) {
        if (this.workflowInstances != null) {
            this.workflowInstances.forEach(i -> i.setPublicCardData(null));
        }
        if (workflowInstances != null) {
            workflowInstances.forEach(i -> i.setPublicCardData(this));
        }
        this.workflowInstances = workflowInstances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicCardData)) {
            return false;
        }
        return id != null && id.equals(((PublicCardData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicCardData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fee='" + getFee() + "'" +
            ", reason='" + getReason() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", typesOfFee='" + getTypesOfFee() + "'" +
            ", agree='" + getAgree() + "'" +
            ", requestid=" + getRequestid() +
            ", workflowid=" + getWorkflowid() +
            ", valid='" + getValid() + "'" +
            "}";
    }
}
