package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.ga.domain.enumeration.PublicDataCardStatus;
import com.kyanite.ga.domain.enumeration.WorkflowInstanceStatus;
import java.io.Serializable;
import java.time.Instant;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "requestid")
    private Long requestid;

    @Column(name = "workflowid")
    private Long workflowid;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "finish")
    private String finish;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PublicDataCardStatus status;

    @Lob
    @Column(name = "variables")
    private String variables;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "link")
    private String link;

    @Column(name = "update_link")
    private String updateLink;

    @Column(name = "agree_num")
    private Long agreeNum;

    @Column(name = "refuse_num")
    private Long refuseNum;

    @Lob
    @Column(name = "sys_full_json_obj_json")
    private String sysFullJsonObjJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "oa_status")
    private WorkflowInstanceStatus oaStatus;

    @JsonIgnoreProperties(value = { "publicCardData", "conversation" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Topboxes topboxes;

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

    @OneToMany(mappedBy = "publicCardData")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicCardData" }, allowSetters = true)
    private Set<AlertCard> alertCards = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "approvers", "workflowTemplate", "creator", "publicCardData" }, allowSetters = true)
    private WorkflowInstance workflowInstance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "topboxes", "ddUsers" }, allowSetters = true)
    private Conversation conversation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PublicCardData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public PublicCardData title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRequestid() {
        return this.requestid;
    }

    public PublicCardData requestid(Long requestid) {
        this.setRequestid(requestid);
        return this;
    }

    public void setRequestid(Long requestid) {
        this.requestid = requestid;
    }

    public Long getWorkflowid() {
        return this.workflowid;
    }

    public PublicCardData workflowid(Long workflowid) {
        this.setWorkflowid(workflowid);
        return this;
    }

    public void setWorkflowid(Long workflowid) {
        this.workflowid = workflowid;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public PublicCardData valid(Boolean valid) {
        this.setValid(valid);
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getFinish() {
        return this.finish;
    }

    public PublicCardData finish(String finish) {
        this.setFinish(finish);
        return this;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public PublicDataCardStatus getStatus() {
        return this.status;
    }

    public PublicCardData status(PublicDataCardStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PublicDataCardStatus status) {
        this.status = status;
    }

    public String getVariables() {
        return this.variables;
    }

    public PublicCardData variables(String variables) {
        this.setVariables(variables);
        return this;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public PublicCardData createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getLink() {
        return this.link;
    }

    public PublicCardData link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUpdateLink() {
        return this.updateLink;
    }

    public PublicCardData updateLink(String updateLink) {
        this.setUpdateLink(updateLink);
        return this;
    }

    public void setUpdateLink(String updateLink) {
        this.updateLink = updateLink;
    }

    public Long getAgreeNum() {
        return this.agreeNum;
    }

    public PublicCardData agreeNum(Long agreeNum) {
        this.setAgreeNum(agreeNum);
        return this;
    }

    public void setAgreeNum(Long agreeNum) {
        this.agreeNum = agreeNum;
    }

    public Long getRefuseNum() {
        return this.refuseNum;
    }

    public PublicCardData refuseNum(Long refuseNum) {
        this.setRefuseNum(refuseNum);
        return this;
    }

    public void setRefuseNum(Long refuseNum) {
        this.refuseNum = refuseNum;
    }

    public String getSysFullJsonObjJson() {
        return this.sysFullJsonObjJson;
    }

    public PublicCardData sysFullJsonObjJson(String sysFullJsonObjJson) {
        this.setSysFullJsonObjJson(sysFullJsonObjJson);
        return this;
    }

    public void setSysFullJsonObjJson(String sysFullJsonObjJson) {
        this.sysFullJsonObjJson = sysFullJsonObjJson;
    }

    public WorkflowInstanceStatus getOaStatus() {
        return this.oaStatus;
    }

    public PublicCardData oaStatus(WorkflowInstanceStatus oaStatus) {
        this.setOaStatus(oaStatus);
        return this;
    }

    public void setOaStatus(WorkflowInstanceStatus oaStatus) {
        this.oaStatus = oaStatus;
    }

    public Topboxes getTopboxes() {
        return this.topboxes;
    }

    public void setTopboxes(Topboxes topboxes) {
        this.topboxes = topboxes;
    }

    public PublicCardData topboxes(Topboxes topboxes) {
        this.setTopboxes(topboxes);
        return this;
    }

    public Set<PrivateCardData> getPrivateCardData() {
        return this.privateCardData;
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

    public Set<OperationResults> getOperationResults() {
        return this.operationResults;
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

    public Set<ConfirmCard> getConfirmCards() {
        return this.confirmCards;
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

    public Set<AlertCard> getAlertCards() {
        return this.alertCards;
    }

    public void setAlertCards(Set<AlertCard> alertCards) {
        if (this.alertCards != null) {
            this.alertCards.forEach(i -> i.setPublicCardData(null));
        }
        if (alertCards != null) {
            alertCards.forEach(i -> i.setPublicCardData(this));
        }
        this.alertCards = alertCards;
    }

    public PublicCardData alertCards(Set<AlertCard> alertCards) {
        this.setAlertCards(alertCards);
        return this;
    }

    public PublicCardData addAlertCard(AlertCard alertCard) {
        this.alertCards.add(alertCard);
        alertCard.setPublicCardData(this);
        return this;
    }

    public PublicCardData removeAlertCard(AlertCard alertCard) {
        this.alertCards.remove(alertCard);
        alertCard.setPublicCardData(null);
        return this;
    }

    public WorkflowInstance getWorkflowInstance() {
        return this.workflowInstance;
    }

    public void setWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    public PublicCardData workflowInstance(WorkflowInstance workflowInstance) {
        this.setWorkflowInstance(workflowInstance);
        return this;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public PublicCardData conversation(Conversation conversation) {
        this.setConversation(conversation);
        return this;
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
            ", title='" + getTitle() + "'" +
            ", requestid=" + getRequestid() +
            ", workflowid=" + getWorkflowid() +
            ", valid='" + getValid() + "'" +
            ", finish='" + getFinish() + "'" +
            ", status='" + getStatus() + "'" +
            ", variables='" + getVariables() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", link='" + getLink() + "'" +
            ", updateLink='" + getUpdateLink() + "'" +
            ", agreeNum=" + getAgreeNum() +
            ", refuseNum=" + getRefuseNum() +
            ", sysFullJsonObjJson='" + getSysFullJsonObjJson() + "'" +
            ", oaStatus='" + getOaStatus() + "'" +
            "}";
    }
}
