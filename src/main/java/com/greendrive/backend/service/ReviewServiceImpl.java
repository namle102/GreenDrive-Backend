package com.greendrive.backend.service;

import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.Review;
import com.greendrive.backend.model.User;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.dto.ReviewDTO;
import com.greendrive.backend.repository.ReviewRepository;
import com.greendrive.backend.repository.UserRepository;
import com.greendrive.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReviewDTO addReview(Long vehicleId, ReviewDTO reviewDTO) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId, HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(reviewDTO.getUsername())
                .orElseThrow(() -> new APIException("User not found with username: " + reviewDTO.getUsername(), HttpStatus.NOT_FOUND));

        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setVehicle(vehicle);
        review.setUser(user);

        Review saved = reviewRepository.save(review);
        ReviewDTO dto = modelMapper.map(saved, ReviewDTO.class);
        dto.setUsername(user.getUsername()); // manually set it
        return dto;
    }

    @Override
    public List<ReviewDTO> getReviewsForVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId, HttpStatus.NOT_FOUND));

        return reviewRepository.findByVehicleId(vehicleId).stream()
                .map(review -> {
                    ReviewDTO dto = modelMapper.map(review, ReviewDTO.class);
                    dto.setUsername(review.getUser().getUsername()); // manually set username
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
