package com.tripsync.trip_management.controller.api.v1;

import com.tripsync.trip_management.entity.Trip;
import com.tripsync.trip_management.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/create")
    public ResponseEntity<Trip> createTrip(
        @RequestParam String tripId,
        @RequestParam String startLocation,
        @RequestParam String destinationLocation
    ) {
        Trip trip = tripService.createTrip(tripId, startLocation, destinationLocation);
        return ResponseEntity.ok(trip);
    }

    @PostMapping("/{tripId}/assign-transporter")
    public ResponseEntity<Trip> assignTransporter(@PathVariable String tripId, @RequestParam String transporterId) {
        Trip trip = tripService.assignTransporter(tripId, transporterId);
        return ResponseEntity.ok(trip);
    }

    @PostMapping("/{tripId}/start")
    public ResponseEntity<Trip> startTrip(@PathVariable String tripId) {
        Trip trip = tripService.startTrip(tripId);
        return ResponseEntity.ok(trip);
    }

    @PostMapping("/{tripId}/complete")
    public ResponseEntity<Trip> completeTrip(@PathVariable String tripId) {
        Trip trip = tripService.completeTrip(tripId);
        return ResponseEntity.ok(trip);
    }
}
