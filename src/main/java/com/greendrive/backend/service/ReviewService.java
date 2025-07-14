package com.greendrive.backend.service;

import com.greendrive.backend.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {

    ReviewDTO addReview(Long vehicleId, ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewsForVehicle(Long vehicleId);
}