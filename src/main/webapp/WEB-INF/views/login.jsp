<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRMP – Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body class="login-page">
<div class="login-container">
    <div class="login-card">
        <div class="login-logo">📋</div>
        <h1 class="login-title">Customer Review<br/>Moderation Portal</h1>
        <p class="login-subtitle">Sign in to continue</p>

        <c:if test="${param.error != null}">
            <div class="alert alert-error">Invalid username or password. Please try again.</div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-info">You have been logged out successfully.</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" class="text-input"
                       placeholder="Enter your username" required autofocus/>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="text-input"
                       placeholder="Enter your password" required/>
            </div>

            <button type="submit" class="btn-primary btn-full">🔐 Sign In</button>
        </form>

        <div class="dev-hint">
            <small>
                <strong>Dev credentials:</strong><br/>
                admin / admin123 (Admin) |
                moderator / mod123 (Moderator) |
                customer / cust123 (Customer)
            </small>
        </div>
    </div>
</div>

<footer class="footer">
    <p>&copy; 2025 Customer Review Moderation Portal | CRMP v1.0.0</p>
</footer>
</body>
</html>
