package com.tripsync.trip_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String tripId;

    @Column(nullable = false)
    private String status; // CREATED, BOOKED, RUNNING, COMPLETED

    @Column(nullable = false)
    private String startLocation;

    @Column(nullable = false)
    private String destinationLocation;

    private String transporterId;
}

