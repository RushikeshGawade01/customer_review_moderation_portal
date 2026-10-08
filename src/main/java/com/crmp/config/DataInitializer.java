package com.crmp.config;

import com.crmp.model.Review;
import com.crmp.model.ReviewStatus;
import com.crmp.model.User;
import com.crmp.model.UserRole;
import com.crmp.repository.ReviewRepository;
import com.crmp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Seeds the H2 in-memory database with default users and sample reviews
 * when the Spring ApplicationContext starts.
 *
 * DEV ONLY — remove or guard with an env-profile for production.
 */
@Component
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired private UserRepository userRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void seed() {
        log.info("Seeding development data...");

        // ── Users ──────────────────────────────────────
        createUser("admin",     "admin123",  "Admin User",    "admin@crmp.dev",     UserRole.ROLE_ADMIN);
        createUser("moderator", "mod123",    "Jane Moderator","mod@crmp.dev",       UserRole.ROLE_MODERATOR);
        createUser("customer",  "cust123",   "John Customer", "customer@crmp.dev",  UserRole.ROLE_CUSTOMER);

        // ── Sample Reviews ─────────────────────────────
        createReview("Wireless Headphones XZ200", "John Customer",
                "These headphones have exceptional noise cancellation. Battery life is impressive!", 5, ReviewStatus.PENDING);

        createReview("Smart Watch Pro", "Alice Johnson",
                "Good build quality but the GPS is inconsistent in dense urban areas.", 3, ReviewStatus.APPROVED);

        createReview("Coffee Maker Deluxe", "Bob Smith",
                "Broke after two months. Very disappointed with the quality.", 1, ReviewStatus.REJECTED);

        createReview("Running Shoes V3", "Carol White",
                "Perfect fit and very comfortable for long runs. Highly recommend!", 5, ReviewStatus.APPROVED);

        createReview("Bluetooth Speaker Mini", "Dave Brown",
                "Sound quality is decent for the price but lacks bass depth.", 3, ReviewStatus.FLAGGED);

        log.info("Development data seeded successfully.");
    }

    private void createUser(String username, String rawPassword, String fullName,
                            String email, UserRole role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRole(role);
        user.setEnabled(true);
        userRepository.save(user);
        log.debug("Created user: {} ({})", username, role);
    }

    private void createReview(String product, String customer, String text,
                              int rating, ReviewStatus status) {
        Review review = new Review();
        review.setProductName(product);
        review.setCustomerName(customer);
        review.setReviewText(text);
        review.setRating(rating);
        review.setStatus(status);
        if (status != ReviewStatus.PENDING) {
            review.setModeratedBy("admin");
            review.setModerationNote("Seeded during data initialisation.");
        }
        reviewRepository.save(review);
    }
}
