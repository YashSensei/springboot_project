package org.example.rideshare.controller;

import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final RideService rideService;

    public UserController(RideService rideService) {
        this.rideService = rideService;
    }

    // Get user's own rides (USER only)
    @GetMapping("/rides")
    public ResponseEntity<List<RideResponse>> getUserRides() {
        List<RideResponse> rides = rideService.getUserRides();
        return ResponseEntity.ok(rides);
    }
}
