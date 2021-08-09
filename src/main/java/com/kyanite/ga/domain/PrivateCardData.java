package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "privateCardData", "operationResults", "workflowInstance", "conversation", "workflowTemplate" },
        allowSetters = true
    )
    private PublicCardData publicCardData;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "groupMembers", "privateCardData", "operationResults", "workflowInstance", "createdInstances" },
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

    public PrivateCardData id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PrivateCardData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee() {
        return this.fee;
    }

    public PrivateCardData fee(String fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getReason() {
        return this.reason;
    }

    public PrivateCardData reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getItemType() {
        return this.itemType;
    }

    public PrivateCardData itemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTypesOfFee() {
        return this.typesOfFee;
    }

    public PrivateCardData typesOfFee(String typesOfFee) {
        this.typesOfFee = typesOfFee;
        return this;
    }

    public void setTypesOfFee(String typesOfFee) {
        this.typesOfFee = typesOfFee;
    }

    public Boolean getAgree() {
        return this.agree;
    }

    public PrivateCardData agree(Boolean agree) {
        this.agree = agree;
        return this;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public PublicCardData getPublicCardData() {
        return this.publicCardData;
    }

    public PrivateCardData publicCardData(PublicCardData publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public void setPublicCardData(PublicCardData publicCardData) {
        this.publicCardData = publicCardData;
    }

    public DdUser getDdUser() {
        return this.ddUser;
    }

    public PrivateCardData ddUser(DdUser ddUser) {
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
            ", name='" + getName() + "'" +
            ", fee='" + getFee() + "'" +
            ", reason='" + getReason() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", typesOfFee='" + getTypesOfFee() + "'" +
            ", agree='" + getAgree() + "'" +
            "}";
    }
}
