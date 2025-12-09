package org.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rides")
public class Ride {

    @Id
    private String id;

    private String userId;      // Passenger ID (FK to User)

    private String driverId;    // Driver ID (FK to User), nullable

    private String pickupLocation;

    private String dropLocation;

    private String status;      // REQUESTED, ACCEPTED, COMPLETED

    private Date createdAt;

    // Status constants
    public static final String STATUS_REQUESTED = "REQUESTED";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_COMPLETED = "COMPLETED";
}
