<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRMP – Submit Review</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">📋 Customer Review Moderation Portal</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/reviews">Reviews</a>
    </div>
</nav>

<div class="container">
    <h1>✍️ Submit a Review</h1>

    <div class="form-card">
        <form action="${pageContext.request.contextPath}/reviews/submit" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="productName">Product / Service Name <span class="required">*</span></label>
                <input type="text" id="productName" name="productName" class="text-input"
                       placeholder="e.g. Wireless Headphones XZ200" required maxlength="200"/>
            </div>

            <div class="form-group">
                <label for="rating">Your Rating <span class="required">*</span></label>
                <div class="star-rating">
                    <input type="radio" id="star5" name="rating" value="5" required/>
                    <label for="star5" title="5 stars">★</label>
                    <input type="radio" id="star4" name="rating" value="4"/>
                    <label for="star4" title="4 stars">★</label>
                    <input type="radio" id="star3" name="rating" value="3"/>
                    <label for="star3" title="3 stars">★</label>
                    <input type="radio" id="star2" name="rating" value="2"/>
                    <label for="star2" title="2 stars">★</label>
                    <input type="radio" id="star1" name="rating" value="1"/>
                    <label for="star1" title="1 star">★</label>
                </div>
            </div>

            <div class="form-group">
                <label for="reviewText">Your Review <span class="required">*</span></label>
                <textarea id="reviewText" name="reviewText" class="textarea-input" rows="6"
                          placeholder="Share your experience with this product or service..."
                          required maxlength="1000"></textarea>
                <small class="hint">Maximum 1000 characters</small>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-primary">📤 Submit Review</button>
                <a href="${pageContext.request.contextPath}/reviews" class="btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

<footer class="footer">
    <p>&copy; 2025 Customer Review Moderation Portal | CRMP v1.0.0</p>
</footer>
</body>
</html>
