package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.ga.domain.enumeration.PublicDataCardStatus;
import com.kyanite.ga.domain.enumeration.WorkflowInstanceStatus;
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

    @Column(name = "requestid")
    private Long requestid;

    @Column(name = "workflowid")
    private Long workflowid;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "link")
    private String link;

    @Column(name = "name")
    private String name;

    @Column(name = "fee_value")
    private String feeValue;

    @Column(name = "reason")
    private String reason;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "types_of_fee")
    private String typesOfFee;

    @Column(name = "agree")
    private Boolean agree;

    @Column(name = "finish")
    private String finish;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PublicDataCardStatus status;

    @Column(name = "content")
    private String content;

    @Column(name = "agree_num")
    private Long agreeNum;

    @Column(name = "refuse_num")
    private Long refuseNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "oa_status")
    private WorkflowInstanceStatus oaStatus;

    @OneToMany(mappedBy = "publicCardData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicCardData", "ddUser" }, allowSetters = true)
    private Set<PrivateCardData> privateCardData = new HashSet<>();

    @OneToMany(mappedBy = "publicCardData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ddUser", "publicCardData" }, allowSetters = true)
    private Set<OperationResults> operationResults = new HashSet<>();

    @OneToMany(mappedBy = "publicCardData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicCardData" }, allowSetters = true)
    private Set<ConfirmCard> confirmCards = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "approvers", "workflowTemplate", "creator", "publicCardData" }, allowSetters = true)
    private WorkflowInstance workflowInstance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "ddUsers" }, allowSetters = true)
    private Conversation conversation;

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

    public String getLink() {
        return this.link;
    }

    public PublicCardData link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getFeeValue() {
        return this.feeValue;
    }

    public PublicCardData feeValue(String feeValue) {
        this.feeValue = feeValue;
        return this;
    }

    public void setFeeValue(String feeValue) {
        this.feeValue = feeValue;
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

    public String getFinish() {
        return this.finish;
    }

    public PublicCardData finish(String finish) {
        this.finish = finish;
        return this;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public PublicDataCardStatus getStatus() {
        return this.status;
    }

    public PublicCardData status(PublicDataCardStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PublicDataCardStatus status) {
        this.status = status;
    }

    public String getContent() {
        return this.content;
    }

    public PublicCardData content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAgreeNum() {
        return this.agreeNum;
    }

    public PublicCardData agreeNum(Long agreeNum) {
        this.agreeNum = agreeNum;
        return this;
    }

    public void setAgreeNum(Long agreeNum) {
        this.agreeNum = agreeNum;
    }

    public Long getRefuseNum() {
        return this.refuseNum;
    }

    public PublicCardData refuseNum(Long refuseNum) {
        this.refuseNum = refuseNum;
        return this;
    }

    public void setRefuseNum(Long refuseNum) {
        this.refuseNum = refuseNum;
    }

    public WorkflowInstanceStatus getOaStatus() {
        return this.oaStatus;
    }

    public PublicCardData oaStatus(WorkflowInstanceStatus oaStatus) {
        this.oaStatus = oaStatus;
        return this;
    }

    public void setOaStatus(WorkflowInstanceStatus oaStatus) {
        this.oaStatus = oaStatus;
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

    public Set<ConfirmCard> getConfirmCards() {
        return this.confirmCards;
    }

    public PublicCardData confirmCards(Set<ConfirmCard> confirmCards) {
        this.setConfirmCards(confirmCards);
        return this;
    }

    public PublicCardData addConfirmCard(ConfirmCard confirmCard) {
        this.confirmCards.add(confirmCard);
        confirmCard.setPublicCardData(this);
        return this;
    }

    public PublicCardData removeConfirmCard(ConfirmCard confirmCard) {
        this.confirmCards.remove(confirmCard);
        confirmCard.setPublicCardData(null);
        return this;
    }

    public void setConfirmCards(Set<ConfirmCard> confirmCards) {
        if (this.confirmCards != null) {
            this.confirmCards.forEach(i -> i.setPublicCardData(null));
        }
        if (confirmCards != null) {
            confirmCards.forEach(i -> i.setPublicCardData(this));
        }
        this.confirmCards = confirmCards;
    }

    public WorkflowInstance getWorkflowInstance() {
        return this.workflowInstance;
    }

    public PublicCardData workflowInstance(WorkflowInstance workflowInstance) {
        this.setWorkflowInstance(workflowInstance);
        return this;
    }

    public void setWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstance = workflowInstance;
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
            ", requestid=" + getRequestid() +
            ", workflowid=" + getWorkflowid() +
            ", valid='" + getValid() + "'" +
            ", link='" + getLink() + "'" +
            ", name='" + getName() + "'" +
            ", feeValue='" + getFeeValue() + "'" +
            ", reason='" + getReason() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", typesOfFee='" + getTypesOfFee() + "'" +
            ", agree='" + getAgree() + "'" +
            ", finish='" + getFinish() + "'" +
            ", status='" + getStatus() + "'" +
            ", content='" + getContent() + "'" +
            ", agreeNum=" + getAgreeNum() +
            ", refuseNum=" + getRefuseNum() +
            ", oaStatus='" + getOaStatus() + "'" +
            "}";
    }
}
