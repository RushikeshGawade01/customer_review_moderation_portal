package com.crmp.controller;

import com.crmp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles dashboard and home page requests.
 */
@Controller
@RequestMapping("/")
public class DashboardController {

    @Autowired
    private ReviewService reviewService;

    /** Home page — redirects to dashboard. */
    @GetMapping
    public String home() {
        return "redirect:/dashboard";
    }

    /**
     * Dashboard page with summary statistics.
     * GET /dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(reviewService.getDashboardStats());
        model.addAttribute("recentReviews", reviewService.getAllReviews()
                .stream().limit(5).toList());
        return "dashboard";
    }
}
