package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConfirmCard.
 */
@Entity
@Table(name = "confirm_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfirmCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "finish")
    private String finish;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "link")
    private String link;

    @Column(name = "md_1")
    private String md1;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "privateCardData", "operationResults", "confirmCards", "alertCards", "workflowInstance", "conversation" },
        allowSetters = true
    )
    private PublicCardData publicCardData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConfirmCard id(Long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public ConfirmCard text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFinish() {
        return this.finish;
    }

    public ConfirmCard finish(String finish) {
        this.finish = finish;
        return this;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getUserId() {
        return this.userId;
    }

    public ConfirmCard userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLink() {
        return this.link;
    }

    public ConfirmCard link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMd1() {
        return this.md1;
    }

    public ConfirmCard md1(String md1) {
        this.md1 = md1;
        return this;
    }

    public void setMd1(String md1) {
        this.md1 = md1;
    }

    public PublicCardData getPublicCardData() {
        return this.publicCardData;
    }

    public ConfirmCard publicCardData(PublicCardData publicCardData) {
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
        if (!(o instanceof ConfirmCard)) {
            return false;
        }
        return id != null && id.equals(((ConfirmCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfirmCard{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", finish='" + getFinish() + "'" +
            ", userId='" + getUserId() + "'" +
            ", link='" + getLink() + "'" +
            ", md1='" + getMd1() + "'" +
            "}";
    }
}
