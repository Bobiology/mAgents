package mglobe.africa.io.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Users.
 */
@Document(collection = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_id")
    private String userID;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    @Field("roles")
    private String roles;

    @Field("date_assigned")
    private Instant dateAssigned;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public Users userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public Users username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Users password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public Users roles(String roles) {
        this.roles = roles;
        return this;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Instant getDateAssigned() {
        return dateAssigned;
    }

    public Users dateAssigned(Instant dateAssigned) {
        this.dateAssigned = dateAssigned;
        return this;
    }

    public void setDateAssigned(Instant dateAssigned) {
        this.dateAssigned = dateAssigned;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        return id != null && id.equals(((Users) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", userID='" + getUserID() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", roles='" + getRoles() + "'" +
            ", dateAssigned='" + getDateAssigned() + "'" +
            "}";
    }
}
