<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRMP – Review #${review.id}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">📋 Customer Review Moderation Portal</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/reviews">Reviews</a>
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn-link">Logout (<sec:authentication property="name"/>)</button>
        </form>
    </div>
</nav>

<div class="container">
    <a href="${pageContext.request.contextPath}/reviews" class="back-link">← Back to Reviews</a>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <div class="review-detail">
        <div class="review-header">
            <h1>${review.productName}</h1>
            <span class="badge badge-${review.status.name().toLowerCase()} badge-lg">${review.status}</span>
        </div>

        <div class="review-meta">
            <span>👤 ${review.customerName}</span>
            <span>
                <c:forEach begin="1" end="${review.rating}" var="i">⭐</c:forEach>
                (${review.rating}/5)
            </span>
            <span>🗓 ${review.createdAt}</span>
            <c:if test="${not empty review.updatedAt}">
                <span>✏️ Updated: ${review.updatedAt}</span>
            </c:if>
        </div>

        <div class="review-body">
            <p>${review.reviewText}</p>
        </div>

        <c:if test="${not empty review.moderatedBy}">
            <div class="moderation-info">
                <h3>Moderation Details</h3>
                <p><strong>Moderated by:</strong> ${review.moderatedBy}</p>
                <c:if test="${not empty review.moderationNote}">
                    <p><strong>Note:</strong> ${review.moderationNote}</p>
                </c:if>
            </div>
        </c:if>
    </div>

    <!-- Moderation Panel (MODERATOR & ADMIN only) -->
    <sec:authorize access="hasAnyRole('MODERATOR','ADMIN')">
        <div class="moderation-panel">
            <h2>🔧 Moderation Actions</h2>
            <form action="${pageContext.request.contextPath}/reviews/${review.id}/moderate" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group">
                    <label for="status">Update Status:</label>
                    <select name="status" id="status" class="select-input" required>
                        <c:forEach var="s" items="${statuses}">
                            <option value="${s}" ${review.status == s ? 'selected' : ''}>${s}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="note">Moderation Note (optional):</label>
                    <textarea name="note" id="note" class="textarea-input" rows="3"
                              placeholder="Add a note for this decision...">${review.moderationNote}</textarea>
                </div>

                <button type="submit" class="btn-primary">💾 Save Decision</button>
            </form>
        </div>
    </sec:authorize>
</div>

<footer class="footer">
    <p>&copy; 2025 Customer Review Moderation Portal | CRMP v1.0.0</p>
</footer>
</body>
</html>
