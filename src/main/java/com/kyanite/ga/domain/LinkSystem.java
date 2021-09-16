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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "linkSystem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "formFields", "linkSystem", "workflowInstances" }, allowSetters = true)
    private Set<WorkflowTemplate> workflowTemplates = new HashSet<>();

    @OneToMany(mappedBy = "linkSystem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "linkSystem" }, allowSetters = true)
    private Set<ApiClient> apiClients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LinkSystem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LinkSystem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WorkflowTemplate> getWorkflowTemplates() {
        return this.workflowTemplates;
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

    public Set<ApiClient> getApiClients() {
        return this.apiClients;
    }

    public void setApiClients(Set<ApiClient> apiClients) {
        if (this.apiClients != null) {
            this.apiClients.forEach(i -> i.setLinkSystem(null));
        }
        if (apiClients != null) {
            apiClients.forEach(i -> i.setLinkSystem(this));
        }
        this.apiClients = apiClients;
    }

    public LinkSystem apiClients(Set<ApiClient> apiClients) {
        this.setApiClients(apiClients);
        return this;
    }

    public LinkSystem addApiClient(ApiClient apiClient) {
        this.apiClients.add(apiClient);
        apiClient.setLinkSystem(this);
        return this;
    }

    public LinkSystem removeApiClient(ApiClient apiClient) {
        this.apiClients.remove(apiClient);
        apiClient.setLinkSystem(null);
        return this;
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
