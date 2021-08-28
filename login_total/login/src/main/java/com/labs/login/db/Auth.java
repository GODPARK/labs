package com.labs.login.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "login_auth")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@ToString(exclude = { "token", "authToken" })
public class Auth {
    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "token")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;

    @Column(name = "last_login_date")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date lastLoginDate;

    @Column(name = "last_logout_date")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date lastLogOutDate;

    @Column(name = "login_fail_count")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long loginFailCount;

    @Column(name = "status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int status;

    @Transient
    private String authToken;
}
