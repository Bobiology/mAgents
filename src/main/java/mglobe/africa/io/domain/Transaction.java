package mglobe.africa.io.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import mglobe.africa.io.domain.enumeration.TransType;

/**
 * A Transaction.
 */
@Document(collection = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("trans_reference")
    private String transReference;

    @Field("trans_amount")
    private Long transAmount;

    @Field("currency")
    private Integer currency;

    @Field("trans_type")
    private TransType transType;

    @Field("trans_status")
    private String transStatus;

    @Field("cust_id")
    private String custID;

    @Field("trans_date")
    private Instant transDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransReference() {
        return transReference;
    }

    public Transaction transReference(String transReference) {
        this.transReference = transReference;
        return this;
    }

    public void setTransReference(String transReference) {
        this.transReference = transReference;
    }

    public Long getTransAmount() {
        return transAmount;
    }

    public Transaction transAmount(Long transAmount) {
        this.transAmount = transAmount;
        return this;
    }

    public void setTransAmount(Long transAmount) {
        this.transAmount = transAmount;
    }

    public Integer getCurrency() {
        return currency;
    }

    public Transaction currency(Integer currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public TransType getTransType() {
        return transType;
    }

    public Transaction transType(TransType transType) {
        this.transType = transType;
        return this;
    }

    public void setTransType(TransType transType) {
        this.transType = transType;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public Transaction transStatus(String transStatus) {
        this.transStatus = transStatus;
        return this;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getCustID() {
        return custID;
    }

    public Transaction custID(String custID) {
        this.custID = custID;
        return this;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public Instant getTransDate() {
        return transDate;
    }

    public Transaction transDate(Instant transDate) {
        this.transDate = transDate;
        return this;
    }

    public void setTransDate(Instant transDate) {
        this.transDate = transDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", transReference='" + getTransReference() + "'" +
            ", transAmount=" + getTransAmount() +
            ", currency=" + getCurrency() +
            ", transType='" + getTransType() + "'" +
            ", transStatus='" + getTransStatus() + "'" +
            ", custID='" + getCustID() + "'" +
            ", transDate='" + getTransDate() + "'" +
            "}";
    }
}
