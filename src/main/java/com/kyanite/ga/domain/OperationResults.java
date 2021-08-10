package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.ga.domain.enumeration.OperationType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OperationResults.
 */
@Entity
@Table(name = "operation_results")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperationResults implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "time")
    private Instant time;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "privateCardData", "approvers", "operationResults", "conversation", "createdInstances" },
        allowSetters = true
    )
    private DdUser ddUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "privateCardData", "operationResults", "workflowInstance", "conversation" }, allowSetters = true)
    private PublicCardData publicCardData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationResults id(Long id) {
        this.id = id;
        return this;
    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public OperationResults operationType(OperationType operationType) {
        this.operationType = operationType;
        return this;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Instant getTime() {
        return this.time;
    }

    public OperationResults time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getText() {
        return this.text;
    }

    public OperationResults text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DdUser getDdUser() {
        return this.ddUser;
    }

    public OperationResults ddUser(DdUser ddUser) {
        this.setDdUser(ddUser);
        return this;
    }

    public void setDdUser(DdUser ddUser) {
        this.ddUser = ddUser;
    }

    public PublicCardData getPublicCardData() {
        return this.publicCardData;
    }

    public OperationResults publicCardData(PublicCardData publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public void setPublicCardData(PublicCardData publicCardData) {
        this.publicCardData = publicCardData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationResults)) {
            return false;
        }
        return id != null && id.equals(((OperationResults) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationResults{" +
            "id=" + getId() +
            ", operationType='" + getOperationType() + "'" +
            ", time='" + getTime() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
