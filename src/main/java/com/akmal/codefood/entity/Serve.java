package com.akmal.codefood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "serve_history")
public class Serve {
    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator",
            strategy = "com.akmal.codefood.util.IDUtil")
    private String id;

    @ManyToOne
    @JoinColumn(name = "recipeId")
    @JsonIgnore
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private ApplicationUser user;

    @Column(name = "nServing")
    private Integer nServing;

    @Column(name = "nStepDone")
    private Integer nStepDone;

    @Column(name = "reaction")
    private String reaction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", length = 19)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", length = 19)
    private Date updatedAt;
}
