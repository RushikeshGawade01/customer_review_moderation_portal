# Customer Review Moderation Portal (CRMP)

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/RushikeshGawade01/customer_review_moderation_portal)
[![Java](https://img.shields.io/badge/Java-11-blue)](https://www.oracle.com/java/)
[![Spring MVC](https://img.shields.io/badge/Spring%20MVC-5.3-green)](https://spring.io/)
[![Maven](https://img.shields.io/badge/Maven-3.9-red)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## 📋 Overview

The **Customer Review Moderation Portal (CRMP)** is a full-stack Java web application that enables businesses to efficiently moderate, manage, and analyse customer reviews. It provides a role-based workflow where customers submit reviews, moderators triage them, and admins oversee the complete review lifecycle — all through a clean, intuitive interface.

---

## 🎯 Problem Statement

Online platforms face an increasing volume of customer reviews. Without a structured moderation system:
- Spam and abusive content damages brand reputation.
- Moderators lack a centralised tool for triage.
- There is no audit trail or status workflow.
- Reporting and analytics are manual and error-prone.

**CRMP** solves these by providing a dedicated, Git-backed, DevOps-enabled portal for end-to-end review moderation.

---

## 👥 Target Users / Stakeholders

| Role | Responsibility |
|------|---------------|
| Customer | Submits product/service reviews |
| Moderator | Reviews, approves, flags, or rejects submissions |
| Admin | Manages users, configures rules, views dashboards |
| Product Manager | Monitors KPIs and review quality metrics |

---

## 🚀 Features (MVP Scope)

- ✅ **Create Review** – Customers submit reviews with ratings and comments
- ✅ **View Reviews** – List and detail pages with filters
- ✅ **Update Review Status** – Moderator workflow (Pending → Approved / Rejected / Flagged)
- ✅ **Search Reviews** – Full-text and filtered search by status, date, keyword
- ✅ **Role-Based Access** – CUSTOMER, MODERATOR, ADMIN roles via Spring Security
- ✅ **Summary Dashboard** – Key metrics: total reviews, pending count, approval rate

---

## 🛠️ Technology Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 11 |
| Web Framework | Spring MVC 5.3 |
| Security | Spring Security 5.8 |
| ORM | Hibernate 5.6 |
| Database | H2 (in-memory, dev) |
| View Layer | JSP + JSTL |
| Build Tool | Apache Maven 3.9 |
| Deployment | Apache Tomcat 9 |
| Version Control | Git + GitHub |
| CI/CD | Jenkins (Phase 2) |
| Containerisation | Docker (Phase 3) |
| Config Management | Ansible (Phase 4) |

---

## 📁 Project Structure

```
customer_review_moderation_portal/
├── src/
│   ├── main/
│   │   ├── java/com/crmp/
│   │   │   ├── config/          # Spring & Security configuration
│   │   │   ├── controller/      # MVC Controllers
│   │   │   ├── model/           # Entity classes
│   │   │   ├── repository/      # DAO layer (Hibernate)
│   │   │   └── service/         # Business logic layer
│   │   ├── resources/           # application.properties, logback.xml
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── views/       # JSP templates
│   │       └── static/          # CSS, JS assets
│   └── test/
│       └── java/com/crmp/       # JUnit 5 test classes
├── docs/                        # Documentation artifacts
├── .github/
│   ├── ISSUE_TEMPLATE/          # Bug report & feature request templates
│   └── workflows/               # GitHub Actions (future CI)
├── pom.xml                      # Maven build descriptor
├── .gitignore
└── README.md
```

---

## ⚡ Quick Start — Local Development

### Prerequisites
- Java 11+
- Apache Maven 3.6+
- Apache Tomcat 9.x

### 1. Clone the Repository
```bash
git clone https://github.com/RushikeshGawade01/customer_review_moderation_portal.git
cd customer_review_moderation_portal
```

### 2. Build the Project
```bash
mvn clean package -DskipTests
```

### 3. Deploy to Tomcat
Copy the generated WAR to Tomcat's `webapps/` directory:
```bash
cp target/crmp.war $TOMCAT_HOME/webapps/
$TOMCAT_HOME/bin/startup.sh   # Linux/Mac
# or
%TOMCAT_HOME%\bin\startup.bat  # Windows
```

### 4. Access the Application
```
http://localhost:8080/crmp
```

### Default Credentials (Development)
| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| moderator | mod123 | MODERATOR |
| customer | cust123 | CUSTOMER |

---

## 🌿 Branch Naming Convention

| Pattern | Purpose | Example |
|---------|---------|---------|
| `main` | Production-ready code | — |
| `develop` | Integration branch | — |
| `feature/<name>` | New feature development | `feature/review-submission` |
| `bugfix/<name>` | Bug fixes | `bugfix/login-redirect` |
| `hotfix/<name>` | Critical production fixes | `hotfix/security-patch` |
| `release/<version>` | Release preparation | `release/v1.0.0` |

---

## 📋 Issue Templates

- 🐛 [Bug Report](.github/ISSUE_TEMPLATE/bug_report.md)
- ✨ [Feature Request](.github/ISSUE_TEMPLATE/feature_request.md)

---

## 📖 Documentation

| Document | Location |
|----------|---------|
| Problem Statement & Scope | [docs/01-problem-statement.md](docs/01-problem-statement.md) |
| Agile Plan & DevOps Workflow | [docs/02-agile-devops-plan.md](docs/02-agile-devops-plan.md) |
| SRS & Architecture | [docs/03-srs-architecture.md](docs/03-srs-architecture.md) |
| Git & GitHub Setup | [docs/04-git-github-setup.md](docs/04-git-github-setup.md) |

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -m "feat: add your feature"`
4. Push to branch: `git push origin feature/your-feature`
5. Open a Pull Request targeting `develop`

---

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Rushikesh Gawade**  
GitHub: [@RushikeshGawade01](https://github.com/RushikeshGawade01)
