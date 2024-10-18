package com.eventmanagement.events.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at", nullable = false)
    private Date modifiedAt;

    @Column(nullable = false)
    private String type;

    @Column
    @Lob
    private byte[] eventFile;

    @Column
    @Lob
    private byte[] attendeeList;

    @Column
    private String webLink;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        modifiedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = new Date();
    }

}
