package com.tripsync.trip_management.service;

import com.tripsync.trip_management.entity.Trip;

public interface TripService {
    Trip createTrip(String tripId, String startLocation, String destinationLocation);

    Trip assignTransporter(String tripId, String transporterId);

    Trip startTrip(String tripId);

    Trip completeTrip(String tripId);
}
