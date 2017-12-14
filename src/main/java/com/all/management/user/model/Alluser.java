package com.all.management.user.model;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.all.management.user.util.AlluserTransformer;

@Entity
@Table(schema = "public",name = "alluser")
public class Alluser {

	@Column(name = "login_id", length = 200)
    @NotNull
    private String loginId;

	@Column(name = "password_hash", length = 200)
    @NotNull
    private String passwordHash;

	@Column(name = "email", length = 200)
    @NotNull
    private String email;

	@Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar lastUpdateDate;

	@Column(name = "app_role", length = 50)
    private String appRole;

	@Column(name = "user_active")
    private Boolean userActive;

	public String getLoginId() {
        return loginId;
    }

	public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

	public String getPasswordHash() {
        return passwordHash;
    }

	public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

	public String getEmail() {
        return email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public Calendar getLastUpdateDate() {
        return lastUpdateDate;
    }

	public void setLastUpdateDate(Calendar lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

	public String getAppRole() {
        return appRole;
    }

	public void setAppRole(String appRole) {
        this.appRole = appRole;
    }

	public Boolean getUserActive() {
        return userActive;
    }

	public void setUserActive(Boolean userActive) {
        this.userActive = userActive;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class")
        .transform(new AlluserTransformer(), Alluser.class).serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static Alluser fromJsonToAlluser(String json) {
        return new JSONDeserializer<Alluser>()
        .use(null, Alluser.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Alluser> collection) {
        return new JSONSerializer().exclude("*.class")
        .transform(new AlluserTransformer(), Alluser.class).serialize(collection);
    }

	public static String toJsonArray(Collection<Alluser> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Alluser> fromJsonArrayToAllusers(String json) {
        return new JSONDeserializer<List<Alluser>>()
        .use("values", Alluser.class).deserialize(json);
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }
}
