<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRMP – All Reviews</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">📋 Customer Review Moderation Portal</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/reviews" class="active">Reviews</a>
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
    <h1>📝 Reviews</h1>

    <!-- Search & Filter Bar -->
    <div class="search-bar">
        <form method="get" action="${pageContext.request.contextPath}/reviews">
            <input type="text" name="keyword" placeholder="Search by product, customer, or keyword..."
                   value="${keyword}" class="search-input"/>
            <button type="submit" class="btn-primary">🔍 Search</button>
        </form>

        <form method="get" action="${pageContext.request.contextPath}/reviews" class="filter-form">
            <select name="status" class="select-input" onchange="this.form.submit()">
                <option value="">All Statuses</option>
                <c:forEach var="s" items="${statuses}">
                    <option value="${s}" ${selectedStatus == s.name() ? 'selected' : ''}>${s}</option>
                </c:forEach>
            </select>
        </form>

        <a href="${pageContext.request.contextPath}/reviews" class="btn-secondary">Clear</a>
    </div>

    <!-- Reviews Table -->
    <c:choose>
        <c:when test="${empty reviews}">
            <div class="empty-state">
                <p>No reviews found matching your criteria.</p>
                <a href="${pageContext.request.contextPath}/reviews/new" class="btn-primary">Submit a Review</a>
            </div>
        </c:when>
        <c:otherwise>
            <p class="count-label">Showing ${reviews.size()} review(s)</p>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Product</th>
                        <th>Customer</th>
                        <th>Rating</th>
                        <th>Review Excerpt</th>
                        <th>Status</th>
                        <th>Submitted</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="review" items="${reviews}">
                    <tr>
                        <td>${review.id}</td>
                        <td>${review.productName}</td>
                        <td>${review.customerName}</td>
                        <td>
                            <c:forEach begin="1" end="${review.rating}" var="i">⭐</c:forEach>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${review.reviewText.length() > 60}">
                                    ${review.reviewText.substring(0,60)}…
                                </c:when>
                                <c:otherwise>${review.reviewText}</c:otherwise>
                            </c:choose>
                        </td>
                        <td><span class="badge badge-${review.status.name().toLowerCase()}">${review.status}</span></td>
                        <td>${review.createdAt}</td>
                        <td><a href="${pageContext.request.contextPath}/reviews/${review.id}" class="btn-sm">View</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<footer class="footer">
    <p>&copy; 2025 Customer Review Moderation Portal | CRMP v1.0.0</p>
</footer>
</body>
</html>
