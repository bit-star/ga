package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Conversation.
 */
@Entity
@Table(name = "conversation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "conversation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "privateCardData", "operationResults", "workflowInstance", "conversation" }, allowSetters = true)
    private Set<PublicCardData> publicCardData = new HashSet<>();

    @OneToMany(mappedBy = "conversation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "privateCardData", "approvers", "operationResults", "conversation", "createdInstances" },
        allowSetters = true
    )
    private Set<DdUser> ddUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Conversation id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Conversation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PublicCardData> getPublicCardData() {
        return this.publicCardData;
    }

    public Conversation publicCardData(Set<PublicCardData> publicCardData) {
        this.setPublicCardData(publicCardData);
        return this;
    }

    public Conversation addPublicCardData(PublicCardData publicCardData) {
        this.publicCardData.add(publicCardData);
        publicCardData.setConversation(this);
        return this;
    }

    public Conversation removePublicCardData(PublicCardData publicCardData) {
        this.publicCardData.remove(publicCardData);
        publicCardData.setConversation(null);
        return this;
    }

    public void setPublicCardData(Set<PublicCardData> publicCardData) {
        if (this.publicCardData != null) {
            this.publicCardData.forEach(i -> i.setConversation(null));
        }
        if (publicCardData != null) {
            publicCardData.forEach(i -> i.setConversation(this));
        }
        this.publicCardData = publicCardData;
    }

    public Set<DdUser> getDdUsers() {
        return this.ddUsers;
    }

    public Conversation ddUsers(Set<DdUser> ddUsers) {
        this.setDdUsers(ddUsers);
        return this;
    }

    public Conversation addDdUser(DdUser ddUser) {
        this.ddUsers.add(ddUser);
        ddUser.setConversation(this);
        return this;
    }

    public Conversation removeDdUser(DdUser ddUser) {
        this.ddUsers.remove(ddUser);
        ddUser.setConversation(null);
        return this;
    }

    public void setDdUsers(Set<DdUser> ddUsers) {
        if (this.ddUsers != null) {
            this.ddUsers.forEach(i -> i.setConversation(null));
        }
        if (ddUsers != null) {
            ddUsers.forEach(i -> i.setConversation(this));
        }
        this.ddUsers = ddUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conversation)) {
            return false;
        }
        return id != null && id.equals(((Conversation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conversation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
