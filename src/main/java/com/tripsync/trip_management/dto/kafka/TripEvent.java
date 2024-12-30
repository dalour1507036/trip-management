package com.tripsync.trip_management.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripEvent {
    private Long id = 0L;
    private String fromAdress = "fromAddress";
    private String toAdress = "toAddress";
    private String eventType = "created";
}
