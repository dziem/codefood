package com.akmal.codefood.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "accountNonLocked", nullable=false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean accountNonLocked;

    @Column(name = "failedAttempt")
    private Integer failedAttempt;

    @Column(name = "lockTime")
    private Date lockTime;
}
