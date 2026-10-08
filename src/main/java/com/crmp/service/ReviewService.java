package com.crmp.service;

import com.crmp.model.Review;
import com.crmp.model.ReviewStatus;
import com.crmp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Business logic layer for review management and moderation.
 */
@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /** Submit a new customer review. Status defaults to PENDING. */
    public void submitReview(Review review) {
        review.setStatus(ReviewStatus.PENDING);
        reviewRepository.save(review);
    }

    /** Retrieve a single review by ID. */
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    /** Retrieve all reviews. */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /** Filter reviews by status. */
    public List<Review> getReviewsByStatus(ReviewStatus status) {
        return reviewRepository.findByStatus(status);
    }

    /** Search reviews by keyword. */
    public List<Review> searchReviews(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return reviewRepository.findAll();
        }
        return reviewRepository.search(keyword.trim());
    }

    /**
     * Moderate a review — update its status and attach a moderation note.
     *
     * @param id            Review ID
     * @param newStatus     Target status (APPROVED / REJECTED / FLAGGED)
     * @param moderatedBy   Username of the moderator
     * @param note          Optional moderation comment
     */
    public void moderateReview(Long id, ReviewStatus newStatus, String moderatedBy, String note) {
        Review review = reviewRepository.findById(id);
        if (review == null) {
            throw new IllegalArgumentException("Review not found: " + id);
        }
        review.setStatus(newStatus);
        review.setModeratedBy(moderatedBy);
        review.setModerationNote(note);
        reviewRepository.update(review);
    }

    /**
     * Build dashboard summary statistics.
     *
     * @return Map containing totalReviews, pendingCount, approvedCount,
     *         rejectedCount, flaggedCount, approvalRate
     */
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        long total    = reviewRepository.countAll();
        long pending  = reviewRepository.countByStatus(ReviewStatus.PENDING);
        long approved = reviewRepository.countByStatus(ReviewStatus.APPROVED);
        long rejected = reviewRepository.countByStatus(ReviewStatus.REJECTED);
        long flagged  = reviewRepository.countByStatus(ReviewStatus.FLAGGED);

        stats.put("totalReviews", total);
        stats.put("pendingCount", pending);
        stats.put("approvedCount", approved);
        stats.put("rejectedCount", rejected);
        stats.put("flaggedCount", flagged);

        double approvalRate = total > 0 ? (double) approved / total * 100 : 0;
        stats.put("approvalRate", String.format("%.1f", approvalRate));

        return stats;
    }
}
