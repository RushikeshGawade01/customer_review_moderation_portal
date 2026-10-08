# Task 3 — Requirements, Architecture and Technology Setup
## Customer Review Moderation Portal (CRMP)

---

## 1. Software Requirements Specification (SRS) Summary

### 1.1 Functional Requirements

| ID | Requirement | Priority |
|----|------------|---------|
| FR-01 | Users can register and log in with role-based credentials | Must Have |
| FR-02 | Customers can submit reviews (product, rating 1–5, text up to 1000 chars) | Must Have |
| FR-03 | Reviews are created with PENDING status | Must Have |
| FR-04 | Moderators and Admins can view all reviews | Must Have |
| FR-05 | Reviews can be filtered by status and searched by keyword | Must Have |
| FR-06 | Moderators and Admins can change review status (PENDING/APPROVED/REJECTED/FLAGGED) | Must Have |
| FR-07 | Moderation note and moderator username are recorded with each decision | Must Have |
| FR-08 | Admin dashboard shows: total, pending, approved, rejected, flagged counts and approval rate | Must Have |
| FR-09 | Recent 5 reviews displayed on dashboard | Should Have |
| FR-10 | Logout clears session securely | Must Have |

### 1.2 Non-Functional Requirements

| ID | Requirement | Target |
|----|------------|--------|
| NFR-01 | Response time for page loads < 2 seconds | P95 |
| NFR-02 | Passwords stored with BCrypt hashing | Security |
| NFR-03 | CSRF tokens on all POST forms | Security |
| NFR-04 | Application builds from source with `mvn clean package` | Build |
| NFR-05 | WAR file deployable to Apache Tomcat 9 without modification | Deployment |
| NFR-06 | All role restrictions enforced server-side (not just UI) | Security |
| NFR-07 | H2 console accessible for local development debugging | Dev |
| NFR-08 | Logback structured logging to file and console | Observability |

---

## 2. Use-Case Diagram (Text Representation)

```
                      ┌─────────────────────────────────────────┐
                      │         CRMP System Boundary            │
                      │                                         │
  ┌──────────┐        │  ┌──────────────────────────────┐       │
  │ Customer │────────┼─►│  UC-01: Login / Logout        │       │
  └──────────┘        │  └──────────────────────────────┘       │
       │              │                                         │
       │              │  ┌──────────────────────────────┐       │
       ├──────────────┼─►│  UC-02: Submit Review         │       │
       │              │  └──────────────────────────────┘       │
       │              │                                         │
       │              │  ┌──────────────────────────────┐       │
       ├──────────────┼─►│  UC-03: View Review List      │       │
       │              │  └──────────────────────────────┘       │
       │              │                                         │
  ┌──────────┐        │  ┌──────────────────────────────┐       │
  │Moderator │────────┼─►│  UC-04: Search / Filter       │       │
  └──────────┘        │  └──────────────────────────────┘       │
       │              │                                         │
       │              │  ┌──────────────────────────────┐       │
       ├──────────────┼─►│  UC-05: View Review Detail    │       │
       │              │  └──────────────────────────────┘       │
       │              │                                         │
       │              │  ┌──────────────────────────────┐       │
       ├──────────────┼─►│  UC-06: Moderate Review       │       │
       │              │  └──────────────────────────────┘       │
       │              │                                         │
  ┌───────┐           │  ┌──────────────────────────────┐       │
  │ Admin │───────────┼─►│  UC-07: View Dashboard        │       │
  └───────┘           │  └──────────────────────────────┘       │
       │              │                                         │
       │              │  ┌──────────────────────────────┐       │
       └──────────────┼─►│  UC-08: Manage Users (future) │       │
                      │  └──────────────────────────────┘       │
                      └─────────────────────────────────────────┘
```

---

## 3. Application Architecture

### 3.1 Architecture Style: Layered MVC

```
┌─────────────────────────────────────────────────────────┐
│                    BROWSER (Client)                     │
│              JSP + JSTL + CSS + HTML5                   │
└────────────────────────┬────────────────────────────────┘
                         │ HTTP
┌────────────────────────▼────────────────────────────────┐
│              APACHE TOMCAT 9 (Servlet Container)        │
│   ┌──────────────────────────────────────────────────┐  │
│   │           PRESENTATION LAYER                     │  │
│   │  DispatcherServlet → Controllers → JSP Views     │  │
│   │  DashboardController  /dashboard                 │  │
│   │  ReviewController     /reviews/**                │  │
│   └──────────────────────────────────────────────────┘  │
│   ┌──────────────────────────────────────────────────┐  │
│   │           SECURITY LAYER                         │  │
│   │  Spring Security Filter Chain                    │  │
│   │  BCrypt Authentication + Role-Based AuthZ        │  │
│   └──────────────────────────────────────────────────┘  │
│   ┌──────────────────────────────────────────────────┐  │
│   │           BUSINESS LOGIC LAYER (Services)        │  │
│   │  ReviewService — moderation workflow & stats     │  │
│   │  CustomUserDetailsService — auth bridge          │  │
│   └──────────────────────────────────────────────────┘  │
│   ┌──────────────────────────────────────────────────┐  │
│   │           DATA ACCESS LAYER (Repositories)       │  │
│   │  ReviewRepository (Hibernate HQL)                │  │
│   │  UserRepository   (Hibernate HQL)                │  │
│   └──────────────────────────────────────────────────┘  │
│   ┌──────────────────────────────────────────────────┐  │
│   │           DATABASE LAYER                         │  │
│   │  H2 In-Memory (Dev)  →  MySQL/PostgreSQL (Prod)  │  │
│   │  Tables: REVIEWS, USERS                          │  │
│   └──────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### 3.2 Component Diagram

```
  com.crmp
  ├── config/
  │   ├── AppConfig.java           (Spring MVC + Hibernate setup)
  │   ├── SecurityConfig.java      (Spring Security rules)
  │   ├── WebAppInitializer.java   (Servlet 3.0 bootstrap)
  │   ├── SecurityWebAppInitializer.java
  │   └── DataInitializer.java     (Dev seed data)
  ├── model/
  │   ├── Review.java              (@Entity — reviews table)
  │   ├── ReviewStatus.java        (PENDING/APPROVED/REJECTED/FLAGGED)
  │   ├── User.java                (@Entity — users table)
  │   └── UserRole.java            (ROLE_CUSTOMER/MODERATOR/ADMIN)
  ├── repository/
  │   ├── ReviewRepository.java    (Hibernate DAO — reviews)
  │   └── UserRepository.java      (Hibernate DAO — users)
  ├── service/
  │   ├── ReviewService.java       (business logic + dashboard stats)
  │   └── CustomUserDetailsService.java (Spring Security bridge)
  └── controller/
      ├── DashboardController.java  (GET /, GET /dashboard)
      └── ReviewController.java     (GET/POST /reviews/**)
```

---

## 4. Data Model

### 4.1 REVIEWS Table

| Column | Type | Constraints | Description |
|--------|------|------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Unique review identifier |
| product_name | VARCHAR(200) | NOT NULL | Product or service name |
| customer_name | VARCHAR(100) | NOT NULL | Author/submitter username |
| review_text | VARCHAR(1000) | NOT NULL | Full review content |
| rating | INT | NOT NULL, 1–5 | Star rating |
| status | VARCHAR(20) | NOT NULL | PENDING/APPROVED/REJECTED/FLAGGED |
| created_at | TIMESTAMP | NOT NULL | Submission timestamp |
| updated_at | TIMESTAMP | NULL | Last modification timestamp |
| moderated_by | VARCHAR(100) | NULL | Moderator's username |
| moderation_note | VARCHAR(500) | NULL | Decision justification |

### 4.2 USERS Table

| Column | Type | Constraints | Description |
|--------|------|------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Unique user identifier |
| username | VARCHAR(100) | NOT NULL, UNIQUE | Login identifier |
| password | VARCHAR(255) | NOT NULL | BCrypt-hashed password |
| full_name | VARCHAR(100) | NOT NULL | Display name |
| email | VARCHAR(150) | NOT NULL, UNIQUE | Email address |
| role | VARCHAR(30) | NOT NULL | ROLE_CUSTOMER/MODERATOR/ADMIN |
| enabled | BOOLEAN | NOT NULL, DEFAULT TRUE | Account active flag |

### 4.3 Entity-Relationship (ER) Summary

```
USER (1) ─────────────── (*) REVIEW
  id                          id
  username  ─── [submitted by] ─── customer_name (string ref, MVP)
  role      ─── [moderated by] ─── moderated_by  (string ref, MVP)
```
> **Note:** Full FK relationships deferred to Phase 2 (MySQL migration). MVP uses denormalised string references for simplicity.

---

## 5. API / URL Mapping

| HTTP Method | URL | Controller | Access Role |
|-------------|-----|-----------|------------|
| GET | `/` | DashboardController | AUTH |
| GET | `/dashboard` | DashboardController | MOD, ADMIN |
| GET | `/login` | Spring Security | ALL |
| POST | `/login` | Spring Security | ALL |
| POST | `/logout` | Spring Security | AUTH |
| GET | `/reviews` | ReviewController | AUTH |
| GET | `/reviews?status=PENDING` | ReviewController | AUTH |
| GET | `/reviews?keyword=xyz` | ReviewController | AUTH |
| GET | `/reviews/new` | ReviewController | CUSTOMER+ |
| POST | `/reviews/submit` | ReviewController | CUSTOMER+ |
| GET | `/reviews/{id}` | ReviewController | AUTH |
| POST | `/reviews/{id}/moderate` | ReviewController | MOD, ADMIN |

---

## 6. Technology Stack Selection

| Component | Choice | Rationale |
|-----------|--------|-----------|
| Language | Java 11 | LTS release; widely used in enterprise; compatible with all toolchain |
| Web Framework | Spring MVC 5.3 | Industry standard; powerful; no XML config needed |
| Security | Spring Security 5.8 | Mature RBAC, CSRF, BCrypt; integrates natively with Spring MVC |
| ORM | Hibernate 5.6 | Mature JPA provider; HQL for complex queries |
| Database (Dev) | H2 In-Memory | Zero-setup; resets on restart; ideal for MVP & tests |
| View Layer | JSP + JSTL | Standard; included in Tomcat; no additional server needed |
| Build Tool | **Apache Maven 3.9** | Industry standard; dependency management; lifecycle phases |
| Application Server | **Apache Tomcat 9** | Target deployment; WAR-based; well-supported |
| Testing | JUnit 5 + Mockito | Modern test framework; mocking for isolated unit tests |
| Logging | Logback | Fast; flexible; native SLF4J integration |
| Version Control | Git + GitHub | Collaboration, history, CI hooks |

---

## 7. Local Development Setup

### Step 1 — Prerequisites
```bash
# Verify installations
java -version    # Should be Java 11+
mvn --version    # Should be Maven 3.6+
git --version    # Should be Git 2.x
```

### Step 2 — Clone Repository
```bash
git clone https://github.com/RushikeshGawade01/customer_review_moderation_portal.git
cd customer_review_moderation_portal
```

### Step 3 — Build
```bash
mvn clean package -DskipTests
# Output: target/crmp.war
```

### Step 4 — Deploy to Tomcat
```bash
# Option A: Copy WAR
copy target\crmp.war %TOMCAT_HOME%\webapps\

# Option B: Maven Tomcat plugin (after configuring %TOMCAT_HOME%\conf\tomcat-users.xml)
mvn tomcat7:deploy
```

### Step 5 — Access Application
```
http://localhost:8080/crmp
```

### Step 6 — H2 Console (Dev Only)
```
http://localhost:8080/crmp/h2-console
JDBC URL: jdbc:h2:mem:crmpdb
Username: sa
Password: (blank)
```

---

*Document prepared: October 2026 | Author: Rushikesh Gawade*
