package com.greendrive.backend.service;

import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.Review;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.payload.ReviewDTO;
import com.greendrive.backend.repository.ReviewRepository;
import com.greendrive.backend.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewDTO addReview(Long vehicleId, ReviewDTO reviewDTO) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId, HttpStatus.NOT_FOUND));

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setVehicle(vehicle);

        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getReviewsForVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId, HttpStatus.NOT_FOUND));

        return reviewRepository.findByVehicleId(vehicleId).stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }
}
