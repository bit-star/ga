package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LinkSystem.
 */
@Entity
@Table(name = "link_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LinkSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "linkSystem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "publicCardData", "formFields", "linkSystem", "workflowInstances" }, allowSetters = true)
    private Set<WorkflowTemplate> workflowTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LinkSystem id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public LinkSystem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WorkflowTemplate> getWorkflowTemplates() {
        return this.workflowTemplates;
    }

    public LinkSystem workflowTemplates(Set<WorkflowTemplate> workflowTemplates) {
        this.setWorkflowTemplates(workflowTemplates);
        return this;
    }

    public LinkSystem addWorkflowTemplate(WorkflowTemplate workflowTemplate) {
        this.workflowTemplates.add(workflowTemplate);
        workflowTemplate.setLinkSystem(this);
        return this;
    }

    public LinkSystem removeWorkflowTemplate(WorkflowTemplate workflowTemplate) {
        this.workflowTemplates.remove(workflowTemplate);
        workflowTemplate.setLinkSystem(null);
        return this;
    }

    public void setWorkflowTemplates(Set<WorkflowTemplate> workflowTemplates) {
        if (this.workflowTemplates != null) {
            this.workflowTemplates.forEach(i -> i.setLinkSystem(null));
        }
        if (workflowTemplates != null) {
            workflowTemplates.forEach(i -> i.setLinkSystem(this));
        }
        this.workflowTemplates = workflowTemplates;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkSystem)) {
            return false;
        }
        return id != null && id.equals(((LinkSystem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkSystem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
