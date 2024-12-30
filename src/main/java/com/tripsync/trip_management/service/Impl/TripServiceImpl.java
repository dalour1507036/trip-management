package com.tripsync.trip_management.service.Impl;

import com.tripsync.trip_management.entity.Trip;
import com.tripsync.trip_management.kafka.TripEventProducer;
import com.tripsync.trip_management.repository.TripRepository;
import com.tripsync.trip_management.service.TripService;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {
    TripRepository tripRepository;
    TripEventProducer tripEventProducer;

    public TripServiceImpl(TripRepository tripRepository, TripEventProducer tripEventProducer) {
        this.tripRepository = tripRepository;
        this.tripEventProducer = tripEventProducer;
    }

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Trip createTrip(String tripId, String startLocation, String destinationLocation) {
        Trip trip = new Trip();
        trip.setTripId(tripId);
        trip.setStatus("CREATED");
        trip.setStartLocation(startLocation);
        trip.setDestinationLocation(destinationLocation);

        trip = tripRepository.save(trip);

        tripEventProducer.sendMessage("created_event", trip);
        return trip;
    }

    @Override
    public Trip assignTransporter(String tripId, String transporterId) {
        Trip trip = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        if (!"CREATED".equals(trip.getStatus())) {
            throw new IllegalStateException("Cannot assign transporter to a non-CREATED trip");
        }

        trip.setTransporterId(transporterId);
        trip.setStatus("BOOKED");

        trip = tripRepository.save(trip);
        tripEventProducer.sendMessage("booked_event", trip);
        return trip;
    }

    @Override
    public Trip startTrip(String tripId) {
        Trip trip = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        if (!"BOOKED".equals(trip.getStatus())) {
            throw new IllegalStateException("Cannot start a non-BOOKED trip");
        }

        trip.setStatus("RUNNING");

        trip = tripRepository.save(trip);
        tripEventProducer.sendMessage("started_event", trip);
        return trip;
    }

    @Override
    public Trip completeTrip(String tripId) {
        Trip trip = tripRepository.findByTripId(tripId)
            .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        if (!"RUNNING".equals(trip.getStatus())) {
            throw new IllegalStateException("Cannot complete a non-RUNNING trip");
        }

        trip.setStatus("COMPLETED");

        trip = tripRepository.save(trip);
        tripEventProducer.sendMessage("completed_event", trip);
        return trip;
    }
}
