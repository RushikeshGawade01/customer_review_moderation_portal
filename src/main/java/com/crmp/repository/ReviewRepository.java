package com.crmp.repository;

import com.crmp.model.Review;
import com.crmp.model.ReviewStatus;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object for Review entities using Hibernate.
 */
@Repository
@Transactional
public class ReviewRepository {

    @Autowired
    private SessionFactory sessionFactory;

    /** Persist a new review. */
    public void save(Review review) {
        sessionFactory.getCurrentSession().save(review);
    }

    /** Update an existing review. */
    public void update(Review review) {
        sessionFactory.getCurrentSession().update(review);
    }

    /** Find a review by its primary key. */
    public Review findById(Long id) {
        return sessionFactory.getCurrentSession().get(Review.class, id);
    }

    /** Retrieve all reviews ordered by creation date descending. */
    public List<Review> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Review r ORDER BY r.createdAt DESC", Review.class)
                .list();
    }

    /** Retrieve reviews filtered by status. */
    public List<Review> findByStatus(ReviewStatus status) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Review r WHERE r.status = :status ORDER BY r.createdAt DESC", Review.class)
                .setParameter("status", status)
                .list();
    }

    /** Full-text search across productName, customerName, and reviewText. */
    public List<Review> search(String keyword) {
        String pattern = "%" + keyword.toLowerCase() + "%";
        return sessionFactory.getCurrentSession()
                .createQuery(
                    "FROM Review r WHERE LOWER(r.productName) LIKE :kw " +
                    "OR LOWER(r.customerName) LIKE :kw OR LOWER(r.reviewText) LIKE :kw " +
                    "ORDER BY r.createdAt DESC", Review.class)
                .setParameter("kw", pattern)
                .list();
    }

    /** Count all reviews. */
    public long countAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(r) FROM Review r", Long.class)
                .uniqueResult();
    }

    /** Count reviews by status. */
    public long countByStatus(ReviewStatus status) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(r) FROM Review r WHERE r.status = :status", Long.class)
                .setParameter("status", status)
                .uniqueResult();
    }
}
