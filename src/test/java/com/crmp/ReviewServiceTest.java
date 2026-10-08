package com.crmp.service;

import com.crmp.model.Review;
import com.crmp.model.ReviewStatus;
import com.crmp.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReviewService business logic.
 */
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("submitReview() should set status to PENDING before saving")
    void testSubmitReviewSetsPendingStatus() {
        Review review = new Review();
        review.setProductName("Test Product");
        review.setCustomerName("John Doe");
        review.setReviewText("Great product!");
        review.setRating(5);

        reviewService.submitReview(review);

        assertEquals(ReviewStatus.PENDING, review.getStatus(),
                "Status should be PENDING after submission");
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    @DisplayName("moderateReview() should update status and moderation metadata")
    void testModerateReviewUpdatesStatus() {
        Review review = new Review();
        review.setStatus(ReviewStatus.PENDING);
        when(reviewRepository.findById(1L)).thenReturn(review);

        reviewService.moderateReview(1L, ReviewStatus.APPROVED, "mod1", "Looks genuine");

        assertEquals(ReviewStatus.APPROVED, review.getStatus());
        assertEquals("mod1", review.getModeratedBy());
        assertEquals("Looks genuine", review.getModerationNote());
        verify(reviewRepository, times(1)).update(review);
    }

    @Test
    @DisplayName("moderateReview() should throw exception for non-existent review")
    void testModerateNonExistentReviewThrows() {
        when(reviewRepository.findById(999L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> reviewService.moderateReview(999L, ReviewStatus.APPROVED, "mod1", "note"));
    }

    @Test
    @DisplayName("getDashboardStats() should calculate approval rate correctly")
    void testDashboardStats() {
        when(reviewRepository.countAll()).thenReturn(10L);
        when(reviewRepository.countByStatus(ReviewStatus.APPROVED)).thenReturn(6L);
        when(reviewRepository.countByStatus(ReviewStatus.PENDING)).thenReturn(2L);
        when(reviewRepository.countByStatus(ReviewStatus.REJECTED)).thenReturn(1L);
        when(reviewRepository.countByStatus(ReviewStatus.FLAGGED)).thenReturn(1L);

        Map<String, Object> stats = reviewService.getDashboardStats();

        assertEquals(10L, stats.get("totalReviews"));
        assertEquals(6L, stats.get("approvedCount"));
        assertEquals("60.0", stats.get("approvalRate"));
    }

    @Test
    @DisplayName("searchReviews() should return all reviews for blank keyword")
    void testSearchWithBlankKeywordReturnsAll() {
        List<Review> allReviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findAll()).thenReturn(allReviews);

        List<Review> result = reviewService.searchReviews("  ");

        assertEquals(2, result.size());
        verify(reviewRepository, never()).search(anyString());
    }
}
