package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PrivateCardData.
 */
@Entity
@Table(name = "private_card_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrivateCardData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "agree")
    private Boolean agree;

    @Column(name = "finish")
    private String finish;

    @Column(name = "authority")
    private String authority;

    @Column(name = "created_by_me")
    private String createdByMe;

    @Column(name = "update_time")
    private Instant updateTime;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "topboxes", "privateCardData", "operationResults", "confirmCards", "alertCards", "workflowInstance", "conversation" },
        allowSetters = true
    )
    private PublicCardData publicCardData;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "privateCardData", "approvers", "operationResults", "createdInstances", "conversations" },
        allowSetters = true
    )
    private DdUser ddUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PrivateCardData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAgree() {
        return this.agree;
    }

    public PrivateCardData agree(Boolean agree) {
        this.setAgree(agree);
        return this;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getFinish() {
        return this.finish;
    }

    public PrivateCardData finish(String finish) {
        this.setFinish(finish);
        return this;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getAuthority() {
        return this.authority;
    }

    public PrivateCardData authority(String authority) {
        this.setAuthority(authority);
        return this;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getCreatedByMe() {
        return this.createdByMe;
    }

    public PrivateCardData createdByMe(String createdByMe) {
        this.setCreatedByMe(createdByMe);
        return this;
    }

    public void setCreatedByMe(String createdByMe) {
        this.createdByMe = createdByMe;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public PrivateCardData updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public PublicCardData getPublicCardData() {
        return this.publicCardData;
    }

    public void setPublicCardData(PublicCardData publicCardData) {
        this.publicCardData = publicCardData;
    }

    public PrivateCardData publicCardData(PublicCardData publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public DdUser getDdUser() {
        return this.ddUser;
    }

    public void setDdUser(DdUser ddUser) {
        this.ddUser = ddUser;
    }

    public PrivateCardData ddUser(DdUser ddUser) {
        this.setDdUser(ddUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrivateCardData)) {
            return false;
        }
        return id != null && id.equals(((PrivateCardData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrivateCardData{" +
            "id=" + getId() +
            ", agree='" + getAgree() + "'" +
            ", finish='" + getFinish() + "'" +
            ", authority='" + getAuthority() + "'" +
            ", createdByMe='" + getCreatedByMe() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
