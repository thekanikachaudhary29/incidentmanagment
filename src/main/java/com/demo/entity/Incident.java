package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String incidentId;

    @Enumerated(EnumType.STRING)
    private OrganizationType organizatonType;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority; //High, Medium, Low

    @Enumerated(EnumType.STRING)
    private IncidentStatus incidentStatus; //Open, In progress, Closed

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User reporter;

    private Date reportedAt;

    private Long reporterId;
}