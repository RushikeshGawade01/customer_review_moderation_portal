package com.crmp.controller;

import com.crmp.model.Review;
import com.crmp.model.ReviewStatus;
import com.crmp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles all review-related HTTP requests.
 * URL prefix: /reviews
 */
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // ─────────────────────────────────────────────────
    // LIST ALL REVIEWS
    // GET /reviews
    // ─────────────────────────────────────────────────
    @GetMapping
    public String listReviews(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("reviews", reviewService.searchReviews(keyword));
            model.addAttribute("keyword", keyword);
        } else if (status != null && !status.isBlank()) {
            model.addAttribute("reviews", reviewService.getReviewsByStatus(ReviewStatus.valueOf(status)));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("reviews", reviewService.getAllReviews());
        }
        model.addAttribute("statuses", ReviewStatus.values());
        return "reviews/list";
    }

    // ─────────────────────────────────────────────────
    // VIEW SINGLE REVIEW
    // GET /reviews/{id}
    // ─────────────────────────────────────────────────
    @GetMapping("/{id}")
    public String viewReview(@PathVariable Long id, Model model) {
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return "redirect:/reviews";
        }
        model.addAttribute("review", review);
        model.addAttribute("statuses", ReviewStatus.values());
        return "reviews/detail";
    }

    // ─────────────────────────────────────────────────
    // SUBMIT NEW REVIEW FORM
    // GET /reviews/new
    // ─────────────────────────────────────────────────
    @GetMapping("/new")
    public String newReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "reviews/form";
    }

    // ─────────────────────────────────────────────────
    // SUBMIT NEW REVIEW
    // POST /reviews/submit
    // ─────────────────────────────────────────────────
    @PostMapping("/submit")
    public String submitReview(@ModelAttribute Review review,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        if (authentication != null) {
            review.setCustomerName(authentication.getName());
        }
        reviewService.submitReview(review);
        redirectAttributes.addFlashAttribute("successMessage",
                "Your review has been submitted and is pending moderation. Thank you!");
        return "redirect:/reviews";
    }

    // ─────────────────────────────────────────────────
    // MODERATE REVIEW (MODERATOR / ADMIN)
    // POST /reviews/{id}/moderate
    // ─────────────────────────────────────────────────
    @PostMapping("/{id}/moderate")
    public String moderateReview(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String note,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        reviewService.moderateReview(
                id,
                ReviewStatus.valueOf(status),
                authentication.getName(),
                note);

        redirectAttributes.addFlashAttribute("successMessage",
                "Review status updated to: " + status);
        return "redirect:/reviews/" + id;
    }
}
