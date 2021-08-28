package com.labs.login.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "login_user")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = { "password", "passwordCheck" })
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID userId;

    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "user_account", nullable = false, unique = true)
    private String userAccount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "user_email", nullable = true)
    private String userEmail;

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "user_role", nullable = false)
    private int userRole;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "user_state", nullable = false)
    private int userState;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "create_date")
    private Date createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "update_date")
    private Date updateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "delete_date")
    private Date deleteDate;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordCheck;
}
