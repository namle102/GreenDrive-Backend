package com.greendrive.backend.controller;

import com.greendrive.backend.dto.ReviewDTO;
import com.greendrive.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles/{vehicleId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long vehicleId, @Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(vehicleId, reviewDTO));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long vehicleId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewsForVehicle(vehicleId));
    }
}
