package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.ga.domain.enumeration.GroupRole;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GroupMembers.
 */
@Entity
@Table(name = "group_members")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupMembers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_role")
    private GroupRole groupRole;

    @ManyToOne
    @JsonIgnoreProperties(value = { "publicCardData", "groupMembers" }, allowSetters = true)
    private Conversation conversation;

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

    public GroupMembers id(Long id) {
        this.id = id;
        return this;
    }

    public GroupRole getGroupRole() {
        return this.groupRole;
    }

    public GroupMembers groupRole(GroupRole groupRole) {
        this.groupRole = groupRole;
        return this;
    }

    public void setGroupRole(GroupRole groupRole) {
        this.groupRole = groupRole;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public GroupMembers conversation(Conversation conversation) {
        this.setConversation(conversation);
        return this;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public DdUser getDdUser() {
        return this.ddUser;
    }

    public GroupMembers ddUser(DdUser ddUser) {
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
        if (!(o instanceof GroupMembers)) {
            return false;
        }
        return id != null && id.equals(((GroupMembers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupMembers{" +
            "id=" + getId() +
            ", groupRole='" + getGroupRole() + "'" +
            "}";
    }
}
