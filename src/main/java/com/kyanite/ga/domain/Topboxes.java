package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Topboxes.
 */
@Entity
@Table(name = "topboxes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Topboxes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "text")
    private String text;

    @Column(name = "link")
    private String link;

    @Column(name = "card_id")
    private String cardId;

    @Column(name = "auxiliary")
    private Boolean auxiliary;

    @Column(name = "open")
    private Boolean open;

    @JsonIgnoreProperties(
        value = { "topboxes", "privateCardData", "operationResults", "confirmCards", "alertCards", "workflowInstance", "conversation" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "topboxes")
    private PublicCardData publicCardData;

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "topboxes", "ddUsers" }, allowSetters = true)
    private Conversation conversation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Topboxes id(String id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public Topboxes text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return this.link;
    }

    public Topboxes link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCardId() {
        return this.cardId;
    }

    public Topboxes cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Boolean getAuxiliary() {
        return this.auxiliary;
    }

    public Topboxes auxiliary(Boolean auxiliary) {
        this.auxiliary = auxiliary;
        return this;
    }

    public void setAuxiliary(Boolean auxiliary) {
        this.auxiliary = auxiliary;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public Topboxes open(Boolean open) {
        this.open = open;
        return this;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public PublicCardData getPublicCardData() {
        return this.publicCardData;
    }

    public Topboxes publicCardData(PublicCardData publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public void setPublicCardData(PublicCardData publicCardData) {
        if (this.publicCardData != null) {
            this.publicCardData.setTopboxes(null);
        }
        if (publicCardData != null) {
            publicCardData.setTopboxes(this);
        }
        this.publicCardData = publicCardData;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public Topboxes conversation(Conversation conversation) {
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
        if (!(o instanceof Topboxes)) {
            return false;
        }
        return id != null && id.equals(((Topboxes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Topboxes{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", link='" + getLink() + "'" +
            ", cardId='" + getCardId() + "'" +
            ", auxiliary='" + getAuxiliary() + "'" +
            ", open='" + getOpen() + "'" +
            "}";
    }
}
