package org.example.rideshare.service;

import org.example.rideshare.config.CustomAuthenticationDetails;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.exception.UnauthorizedException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.repository.RideRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    // Get current user's ID from security context
    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getDetails() instanceof CustomAuthenticationDetails) {
            return ((CustomAuthenticationDetails) auth.getDetails()).getUserId();
        }
        throw new UnauthorizedException("User not authenticated");
    }

    // Get current user's role from security context
    private String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getDetails() instanceof CustomAuthenticationDetails) {
            return ((CustomAuthenticationDetails) auth.getDetails()).getRole();
        }
        throw new UnauthorizedException("User not authenticated");
    }

    // Create a new ride (Passenger only)
    public RideResponse createRide(CreateRideRequest request) {
        String userId = getCurrentUserId();
        String role = getCurrentUserRole();

        if (!"ROLE_USER".equals(role)) {
            throw new BadRequestException("Only passengers (ROLE_USER) can request rides");
        }

        Ride ride = new Ride();
        ride.setUserId(userId);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus(Ride.STATUS_REQUESTED);
        ride.setCreatedAt(new Date());

        Ride savedRide = rideRepository.save(ride);
        return RideResponse.fromRide(savedRide);
    }

    // Get all pending ride requests (Driver only)
    public List<RideResponse> getPendingRides() {
        List<Ride> rides = rideRepository.findByStatusOrderByCreatedAtDesc(Ride.STATUS_REQUESTED);
        return rides.stream()
                .map(RideResponse::fromRide)
                .collect(Collectors.toList());
    }

    // Accept a ride (Driver only)
    public RideResponse acceptRide(String rideId) {
        String driverId = getCurrentUserId();

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with id: " + rideId));

        if (!Ride.STATUS_REQUESTED.equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED status. Current status: " + ride.getStatus());
        }

        ride.setDriverId(driverId);
        ride.setStatus(Ride.STATUS_ACCEPTED);

        Ride updatedRide = rideRepository.save(ride);
        return RideResponse.fromRide(updatedRide);
    }

    // Complete a ride (Driver or Passenger)
    public RideResponse completeRide(String rideId) {
        String userId = getCurrentUserId();

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with id: " + rideId));

        if (!Ride.STATUS_ACCEPTED.equals(ride.getStatus())) {
            throw new BadRequestException("Ride must be in ACCEPTED status to complete. Current status: " + ride.getStatus());
        }

        // Verify the user is either the passenger or the driver
        boolean isPassenger = userId.equals(ride.getUserId());
        boolean isDriver = userId.equals(ride.getDriverId());

        if (!isPassenger && !isDriver) {
            throw new UnauthorizedException("You are not authorized to complete this ride");
        }

        ride.setStatus(Ride.STATUS_COMPLETED);

        Ride updatedRide = rideRepository.save(ride);
        return RideResponse.fromRide(updatedRide);
    }

    // Get user's own rides (Passenger)
    public List<RideResponse> getUserRides() {
        String userId = getCurrentUserId();
        List<Ride> rides = rideRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return rides.stream()
                .map(RideResponse::fromRide)
                .collect(Collectors.toList());
    }

    // Get a specific ride by ID
    public RideResponse getRideById(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with id: " + rideId));
        return RideResponse.fromRide(ride);
    }
}
