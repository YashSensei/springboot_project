package org.example.rideshare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRideRequest {

    @NotBlank(message = "Pickup location is required")
    @Size(min = 2, max = 200, message = "Pickup location must be between 2 and 200 characters")
    private String pickupLocation;

    @NotBlank(message = "Drop location is required")
    @Size(min = 2, max = 200, message = "Drop location must be between 2 and 200 characters")
    private String dropLocation;
}
