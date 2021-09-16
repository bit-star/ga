package com.kyanite.ga.domain;

import com.kyanite.ga.domain.enumeration.ApiDirection;
import com.kyanite.ga.domain.enumeration.HttpMethod;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApiInvokeLog.
 */
@Entity
@Table(name = "api_invoke_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApiInvokeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "api_name")
    private String apiName;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private HttpMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private ApiDirection direction;

    @Column(name = "http_status_code")
    private Integer httpStatusCode;

    @Column(name = "time")
    private Instant time;

    @Lob
    @Column(name = "reqeust_data")
    private String reqeustData;

    @Lob
    @Column(name = "response_data")
    private String responseData;

    @Column(name = "ok")
    private Boolean ok;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApiInvokeLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public ApiInvokeLog login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getApiName() {
        return this.apiName;
    }

    public ApiInvokeLog apiName(String apiName) {
        this.setApiName(apiName);
        return this;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public ApiInvokeLog method(HttpMethod method) {
        this.setMethod(method);
        return this;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public ApiDirection getDirection() {
        return this.direction;
    }

    public ApiInvokeLog direction(ApiDirection direction) {
        this.setDirection(direction);
        return this;
    }

    public void setDirection(ApiDirection direction) {
        this.direction = direction;
    }

    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public ApiInvokeLog httpStatusCode(Integer httpStatusCode) {
        this.setHttpStatusCode(httpStatusCode);
        return this;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Instant getTime() {
        return this.time;
    }

    public ApiInvokeLog time(Instant time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getReqeustData() {
        return this.reqeustData;
    }

    public ApiInvokeLog reqeustData(String reqeustData) {
        this.setReqeustData(reqeustData);
        return this;
    }

    public void setReqeustData(String reqeustData) {
        this.reqeustData = reqeustData;
    }

    public String getResponseData() {
        return this.responseData;
    }

    public ApiInvokeLog responseData(String responseData) {
        this.setResponseData(responseData);
        return this;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public Boolean getOk() {
        return this.ok;
    }

    public ApiInvokeLog ok(Boolean ok) {
        this.setOk(ok);
        return this;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiInvokeLog)) {
            return false;
        }
        return id != null && id.equals(((ApiInvokeLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiInvokeLog{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", apiName='" + getApiName() + "'" +
            ", method='" + getMethod() + "'" +
            ", direction='" + getDirection() + "'" +
            ", httpStatusCode=" + getHttpStatusCode() +
            ", time='" + getTime() + "'" +
            ", reqeustData='" + getReqeustData() + "'" +
            ", responseData='" + getResponseData() + "'" +
            ", ok='" + getOk() + "'" +
            "}";
    }
}
