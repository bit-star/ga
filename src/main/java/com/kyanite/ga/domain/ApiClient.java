package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApiClient.
 */
@Entity
@Table(name = "api_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApiClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "api_secret")
    private String apiSecret;

    @Column(name = "enable")
    private Boolean enable;

    @ManyToOne
    @JsonIgnoreProperties(value = { "workflowTemplates", "apiClients" }, allowSetters = true)
    private LinkSystem linkSystem;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApiClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ApiClient name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public ApiClient apiKey(String apiKey) {
        this.setApiKey(apiKey);
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return this.apiSecret;
    }

    public ApiClient apiSecret(String apiSecret) {
        this.setApiSecret(apiSecret);
        return this;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public ApiClient enable(Boolean enable) {
        this.setEnable(enable);
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public LinkSystem getLinkSystem() {
        return this.linkSystem;
    }

    public void setLinkSystem(LinkSystem linkSystem) {
        this.linkSystem = linkSystem;
    }

    public ApiClient linkSystem(LinkSystem linkSystem) {
        this.setLinkSystem(linkSystem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiClient)) {
            return false;
        }
        return id != null && id.equals(((ApiClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiClient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", apiSecret='" + getApiSecret() + "'" +
            ", enable='" + getEnable() + "'" +
            "}";
    }
}
