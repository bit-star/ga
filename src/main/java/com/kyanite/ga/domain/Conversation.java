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
    @JsonIgnoreProperties(
        value = { "privateCardData", "operationResults", "conversation", "workflowTemplate", "workflowInstances" },
        allowSetters = true
    )
    private Set<PublicCardData> publicCardData = new HashSet<>();

    @OneToMany(mappedBy = "conversation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "conversation", "ddUser" }, allowSetters = true)
    private Set<GroupMembers> groupMembers = new HashSet<>();

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

    public Set<GroupMembers> getGroupMembers() {
        return this.groupMembers;
    }

    public Conversation groupMembers(Set<GroupMembers> groupMembers) {
        this.setGroupMembers(groupMembers);
        return this;
    }

    public Conversation addGroupMembers(GroupMembers groupMembers) {
        this.groupMembers.add(groupMembers);
        groupMembers.setConversation(this);
        return this;
    }

    public Conversation removeGroupMembers(GroupMembers groupMembers) {
        this.groupMembers.remove(groupMembers);
        groupMembers.setConversation(null);
        return this;
    }

    public void setGroupMembers(Set<GroupMembers> groupMembers) {
        if (this.groupMembers != null) {
            this.groupMembers.forEach(i -> i.setConversation(null));
        }
        if (groupMembers != null) {
            groupMembers.forEach(i -> i.setConversation(this));
        }
        this.groupMembers = groupMembers;
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
