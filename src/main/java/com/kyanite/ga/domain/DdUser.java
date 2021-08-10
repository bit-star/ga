package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DdUser.
 */
@Entity
@Table(name = "dd_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DdUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unionid")
    private String unionid;

    @Column(name = "remark")
    private String remark;

    @Column(name = "userid")
    private String userid;

    @Column(name = "is_leader_in_depts")
    private String isLeaderInDepts;

    @Column(name = "is_boss")
    private Boolean isBoss;

    @Column(name = "hired_date", precision = 21, scale = 2)
    private BigDecimal hiredDate;

    @Column(name = "is_senior")
    private Boolean isSenior;

    @Column(name = "tel")
    private String tel;

    @Column(name = "department")
    private String department;

    @Column(name = "work_place")
    private String workPlace;

    @Lob
    @Column(name = "order_in_depts")
    private String orderInDepts;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "errmsg")
    private String errmsg;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_hide")
    private Boolean isHide;

    @Column(name = "jobnumber")
    private String jobnumber;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "extattr")
    private String extattr;

    @Column(name = "state_code")
    private String stateCode;

    @Lob
    @Column(name = "position")
    private String position;

    @Column(name = "roles")
    private String roles;

    @OneToMany(mappedBy = "ddUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicCardData", "ddUser" }, allowSetters = true)
    private Set<PrivateCardData> privateCardData = new HashSet<>();

    @OneToMany(mappedBy = "ddUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "workflowInstance", "ddUser" }, allowSetters = true)
    private Set<Approver> approvers = new HashSet<>();

    @OneToMany(mappedBy = "ddUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ddUser", "publicCardData" }, allowSetters = true)
    private Set<OperationResults> operationResults = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "ddUsers" }, allowSetters = true)
    private Conversation conversation;

    @OneToMany(mappedBy = "creator")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "approvers", "workflowTemplate", "creator", "publicCardData" }, allowSetters = true)
    private Set<WorkflowInstance> createdInstances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DdUser id(Long id) {
        this.id = id;
        return this;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public DdUser unionid(String unionid) {
        this.unionid = unionid;
        return this;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return this.remark;
    }

    public DdUser remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserid() {
        return this.userid;
    }

    public DdUser userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIsLeaderInDepts() {
        return this.isLeaderInDepts;
    }

    public DdUser isLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
        return this;
    }

    public void setIsLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
    }

    public Boolean getIsBoss() {
        return this.isBoss;
    }

    public DdUser isBoss(Boolean isBoss) {
        this.isBoss = isBoss;
        return this;
    }

    public void setIsBoss(Boolean isBoss) {
        this.isBoss = isBoss;
    }

    public BigDecimal getHiredDate() {
        return this.hiredDate;
    }

    public DdUser hiredDate(BigDecimal hiredDate) {
        this.hiredDate = hiredDate;
        return this;
    }

    public void setHiredDate(BigDecimal hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Boolean getIsSenior() {
        return this.isSenior;
    }

    public DdUser isSenior(Boolean isSenior) {
        this.isSenior = isSenior;
        return this;
    }

    public void setIsSenior(Boolean isSenior) {
        this.isSenior = isSenior;
    }

    public String getTel() {
        return this.tel;
    }

    public DdUser tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDepartment() {
        return this.department;
    }

    public DdUser department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkPlace() {
        return this.workPlace;
    }

    public DdUser workPlace(String workPlace) {
        this.workPlace = workPlace;
        return this;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getOrderInDepts() {
        return this.orderInDepts;
    }

    public DdUser orderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
        return this;
    }

    public void setOrderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
    }

    public String getMobile() {
        return this.mobile;
    }

    public DdUser mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public DdUser errmsg(String errmsg) {
        this.errmsg = errmsg;
        return this;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Boolean getActive() {
        return this.active;
    }

    public DdUser active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public DdUser avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public DdUser isAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsHide() {
        return this.isHide;
    }

    public DdUser isHide(Boolean isHide) {
        this.isHide = isHide;
        return this;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public String getJobnumber() {
        return this.jobnumber;
    }

    public DdUser jobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
        return this;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getName() {
        return this.name;
    }

    public DdUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtattr() {
        return this.extattr;
    }

    public DdUser extattr(String extattr) {
        this.extattr = extattr;
        return this;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public DdUser stateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPosition() {
        return this.position;
    }

    public DdUser position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoles() {
        return this.roles;
    }

    public DdUser roles(String roles) {
        this.roles = roles;
        return this;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<PrivateCardData> getPrivateCardData() {
        return this.privateCardData;
    }

    public DdUser privateCardData(Set<PrivateCardData> privateCardData) {
        this.setPrivateCardData(privateCardData);
        return this;
    }

    public DdUser addPrivateCardData(PrivateCardData privateCardData) {
        this.privateCardData.add(privateCardData);
        privateCardData.setDdUser(this);
        return this;
    }

    public DdUser removePrivateCardData(PrivateCardData privateCardData) {
        this.privateCardData.remove(privateCardData);
        privateCardData.setDdUser(null);
        return this;
    }

    public void setPrivateCardData(Set<PrivateCardData> privateCardData) {
        if (this.privateCardData != null) {
            this.privateCardData.forEach(i -> i.setDdUser(null));
        }
        if (privateCardData != null) {
            privateCardData.forEach(i -> i.setDdUser(this));
        }
        this.privateCardData = privateCardData;
    }

    public Set<Approver> getApprovers() {
        return this.approvers;
    }

    public DdUser approvers(Set<Approver> approvers) {
        this.setApprovers(approvers);
        return this;
    }

    public DdUser addApprover(Approver approver) {
        this.approvers.add(approver);
        approver.setDdUser(this);
        return this;
    }

    public DdUser removeApprover(Approver approver) {
        this.approvers.remove(approver);
        approver.setDdUser(null);
        return this;
    }

    public void setApprovers(Set<Approver> approvers) {
        if (this.approvers != null) {
            this.approvers.forEach(i -> i.setDdUser(null));
        }
        if (approvers != null) {
            approvers.forEach(i -> i.setDdUser(this));
        }
        this.approvers = approvers;
    }

    public Set<OperationResults> getOperationResults() {
        return this.operationResults;
    }

    public DdUser operationResults(Set<OperationResults> operationResults) {
        this.setOperationResults(operationResults);
        return this;
    }

    public DdUser addOperationResults(OperationResults operationResults) {
        this.operationResults.add(operationResults);
        operationResults.setDdUser(this);
        return this;
    }

    public DdUser removeOperationResults(OperationResults operationResults) {
        this.operationResults.remove(operationResults);
        operationResults.setDdUser(null);
        return this;
    }

    public void setOperationResults(Set<OperationResults> operationResults) {
        if (this.operationResults != null) {
            this.operationResults.forEach(i -> i.setDdUser(null));
        }
        if (operationResults != null) {
            operationResults.forEach(i -> i.setDdUser(this));
        }
        this.operationResults = operationResults;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public DdUser conversation(Conversation conversation) {
        this.setConversation(conversation);
        return this;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Set<WorkflowInstance> getCreatedInstances() {
        return this.createdInstances;
    }

    public DdUser createdInstances(Set<WorkflowInstance> workflowInstances) {
        this.setCreatedInstances(workflowInstances);
        return this;
    }

    public DdUser addCreatedInstance(WorkflowInstance workflowInstance) {
        this.createdInstances.add(workflowInstance);
        workflowInstance.setCreator(this);
        return this;
    }

    public DdUser removeCreatedInstance(WorkflowInstance workflowInstance) {
        this.createdInstances.remove(workflowInstance);
        workflowInstance.setCreator(null);
        return this;
    }

    public void setCreatedInstances(Set<WorkflowInstance> workflowInstances) {
        if (this.createdInstances != null) {
            this.createdInstances.forEach(i -> i.setCreator(null));
        }
        if (workflowInstances != null) {
            workflowInstances.forEach(i -> i.setCreator(this));
        }
        this.createdInstances = workflowInstances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DdUser)) {
            return false;
        }
        return id != null && id.equals(((DdUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DdUser{" +
            "id=" + getId() +
            ", unionid='" + getUnionid() + "'" +
            ", remark='" + getRemark() + "'" +
            ", userid='" + getUserid() + "'" +
            ", isLeaderInDepts='" + getIsLeaderInDepts() + "'" +
            ", isBoss='" + getIsBoss() + "'" +
            ", hiredDate=" + getHiredDate() +
            ", isSenior='" + getIsSenior() + "'" +
            ", tel='" + getTel() + "'" +
            ", department='" + getDepartment() + "'" +
            ", workPlace='" + getWorkPlace() + "'" +
            ", orderInDepts='" + getOrderInDepts() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", errmsg='" + getErrmsg() + "'" +
            ", active='" + getActive() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", isAdmin='" + getIsAdmin() + "'" +
            ", isHide='" + getIsHide() + "'" +
            ", jobnumber='" + getJobnumber() + "'" +
            ", name='" + getName() + "'" +
            ", extattr='" + getExtattr() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            ", position='" + getPosition() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }
}
