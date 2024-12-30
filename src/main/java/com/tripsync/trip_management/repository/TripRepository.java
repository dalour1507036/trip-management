package com.tripsync.trip_management.repository;

import com.tripsync.trip_management.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByTripId(String tripId);
}
