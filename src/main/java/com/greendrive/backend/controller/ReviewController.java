package com.greendrive.backend.controller;

import com.greendrive.backend.dto.ReviewDTO;
import com.greendrive.backend.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles/{vehicleId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long vehicleId, @Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.addReview(vehicleId, reviewDTO));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(reviewService.getReviewsForVehicle(vehicleId));
    }
}
