<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRMP – Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">📋 Customer Review Moderation Portal</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/reviews">Reviews</a>
        <sec:authorize access="hasAnyRole('CUSTOMER','MODERATOR','ADMIN')">
            <a href="${pageContext.request.contextPath}/reviews/new">Submit Review</a>
        </sec:authorize>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn-link">Logout (<sec:authentication property="name"/>)</button>
        </form>
    </div>
</nav>

<div class="container">
    <h1>📊 Dashboard</h1>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <!-- Statistics Cards -->
    <div class="stats-grid">
        <div class="stat-card total">
            <div class="stat-number">${totalReviews}</div>
            <div class="stat-label">Total Reviews</div>
        </div>
        <div class="stat-card pending">
            <div class="stat-number">${pendingCount}</div>
            <div class="stat-label">Pending</div>
        </div>
        <div class="stat-card approved">
            <div class="stat-number">${approvedCount}</div>
            <div class="stat-label">Approved</div>
        </div>
        <div class="stat-card rejected">
            <div class="stat-number">${rejectedCount}</div>
            <div class="stat-label">Rejected</div>
        </div>
        <div class="stat-card flagged">
            <div class="stat-number">${flaggedCount}</div>
            <div class="stat-label">Flagged</div>
        </div>
        <div class="stat-card rate">
            <div class="stat-number">${approvalRate}%</div>
            <div class="stat-label">Approval Rate</div>
        </div>
    </div>

    <!-- Recent Reviews -->
    <section class="recent-reviews">
        <h2>Recent Reviews</h2>
        <c:choose>
            <c:when test="${empty recentReviews}">
                <p class="no-data">No reviews yet. <a href="${pageContext.request.contextPath}/reviews/new">Submit the first one!</a></p>
            </c:when>
            <c:otherwise>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Product</th>
                            <th>Customer</th>
                            <th>Rating</th>
                            <th>Status</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="review" items="${recentReviews}">
                        <tr>
                            <td>${review.id}</td>
                            <td>${review.productName}</td>
                            <td>${review.customerName}</td>
                            <td>
                                <c:forEach begin="1" end="${review.rating}" var="i">⭐</c:forEach>
                            </td>
                            <td><span class="badge badge-${review.status.name().toLowerCase()}">${review.status}</span></td>
                            <td>${review.createdAt}</td>
                            <td><a href="${pageContext.request.contextPath}/reviews/${review.id}" class="btn-sm">View</a></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <p><a href="${pageContext.request.contextPath}/reviews" class="btn-secondary">View All Reviews →</a></p>
            </c:otherwise>
        </c:choose>
    </section>
</div>

<footer class="footer">
    <p>&copy; 2025 Customer Review Moderation Portal | CRMP v1.0.0</p>
</footer>
</body>
</html>
