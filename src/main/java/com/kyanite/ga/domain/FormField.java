package com.kyanite.ga.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormField.
 */
@Entity
@Table(name = "form_field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FormField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fieldname")
    private String fieldname;

    @Column(name = "value")
    private String value;

    @Column(name = "fielddbtype")
    private String fielddbtype;

    @Column(name = "lablename")
    private String lablename;

    @Column(name = "jhi_show")
    private Boolean show;

    @Column(name = "order_num")
    private Integer orderNum;

    @ManyToOne
    @JsonIgnoreProperties(value = { "formFields", "linkSystem", "workflowInstances" }, allowSetters = true)
    private WorkflowTemplate workflowTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormField id(Long id) {
        this.id = id;
        return this;
    }

    public String getFieldname() {
        return this.fieldname;
    }

    public FormField fieldname(String fieldname) {
        this.fieldname = fieldname;
        return this;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getValue() {
        return this.value;
    }

    public FormField value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFielddbtype() {
        return this.fielddbtype;
    }

    public FormField fielddbtype(String fielddbtype) {
        this.fielddbtype = fielddbtype;
        return this;
    }

    public void setFielddbtype(String fielddbtype) {
        this.fielddbtype = fielddbtype;
    }

    public String getLablename() {
        return this.lablename;
    }

    public FormField lablename(String lablename) {
        this.lablename = lablename;
        return this;
    }

    public void setLablename(String lablename) {
        this.lablename = lablename;
    }

    public Boolean getShow() {
        return this.show;
    }

    public FormField show(Boolean show) {
        this.show = show;
        return this;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public FormField orderNum(Integer orderNum) {
        this.orderNum = orderNum;
        return this;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public WorkflowTemplate getWorkflowTemplate() {
        return this.workflowTemplate;
    }

    public FormField workflowTemplate(WorkflowTemplate workflowTemplate) {
        this.setWorkflowTemplate(workflowTemplate);
        return this;
    }

    public void setWorkflowTemplate(WorkflowTemplate workflowTemplate) {
        this.workflowTemplate = workflowTemplate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormField)) {
            return false;
        }
        return id != null && id.equals(((FormField) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormField{" +
            "id=" + getId() +
            ", fieldname='" + getFieldname() + "'" +
            ", value='" + getValue() + "'" +
            ", fielddbtype='" + getFielddbtype() + "'" +
            ", lablename='" + getLablename() + "'" +
            ", show='" + getShow() + "'" +
            ", orderNum=" + getOrderNum() +
            "}";
    }
}
