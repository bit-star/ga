package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlertCard.
 */
@Entity
@Table(name = "alert_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlertCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "link")
    private String link;

    @Column(name = "md_1")
    private String md1;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "topboxes", "privateCardData", "operationResults", "confirmCards", "alertCards", "workflowInstance", "conversation" },
        allowSetters = true
    )
    private PublicCardData publicCardData;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AlertCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public AlertCard text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return this.userId;
    }

    public AlertCard userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLink() {
        return this.link;
    }

    public AlertCard link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMd1() {
        return this.md1;
    }

    public AlertCard md1(String md1) {
        this.setMd1(md1);
        return this;
    }

    public void setMd1(String md1) {
        this.md1 = md1;
    }

    public PublicCardData getPublicCardData() {
        return this.publicCardData;
    }

    public void setPublicCardData(PublicCardData publicCardData) {
        this.publicCardData = publicCardData;
    }

    public AlertCard publicCardData(PublicCardData publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlertCard)) {
            return false;
        }
        return id != null && id.equals(((AlertCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertCard{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", userId='" + getUserId() + "'" +
            ", link='" + getLink() + "'" +
            ", md1='" + getMd1() + "'" +
            "}";
    }
}
