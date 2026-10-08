# Task 4 — Git and GitHub Repository Initialization
## Customer Review Moderation Portal (CRMP)

---

## 1. Repository Details

| Property | Value |
|----------|-------|
| **Repository URL** | https://github.com/RushikeshGawade01/customer_review_moderation_portal |
| **Owner** | RushikeshGawade01 |
| **Visibility** | Public |
| **Default Branch** | `main` |
| **Integration Branch** | `develop` |
| **License** | MIT |

---

## 2. Folder Structure

```
customer_review_moderation_portal/
│
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md           # Structured bug report template
│   │   └── feature_request.md      # Feature request template
│   └── workflows/                  # GitHub Actions (future CI — Phase 7)
│
├── docs/
│   ├── 01-problem-statement.md     # Task 1 deliverable
│   ├── 02-agile-devops-plan.md     # Task 2 deliverable
│   ├── 03-srs-architecture.md      # Task 3 deliverable
│   └── 04-git-github-setup.md      # Task 4 deliverable (this document)
│
├── src/
│   ├── main/
│   │   ├── java/com/crmp/
│   │   │   ├── config/             # Spring + Security configuration classes
│   │   │   ├── controller/         # MVC Controllers
│   │   │   ├── model/              # JPA Entities + Enums
│   │   │   ├── repository/         # Hibernate DAOs
│   │   │   └── service/            # Business logic layer
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── logback.xml
│   │   └── webapp/
│   │       ├── WEB-INF/views/      # JSP templates
│   │       └── static/css/         # CSS stylesheets
│   └── test/
│       └── java/com/crmp/          # JUnit 5 test classes
│
├── .gitignore                      # Excludes target/, .idea/, *.class, etc.
├── LICENSE                         # MIT License
├── pom.xml                         # Maven build descriptor
└── README.md                       # Project documentation
```

---

## 3. Branch Naming Policy

All branches must follow the naming convention below. PRs into `develop` are required for all feature/bugfix work.

| Branch Pattern | Purpose | Example |
|----------------|---------|---------|
| `main` | Production-ready, tagged releases only | — |
| `develop` | Integration branch — all features merge here first | — |
| `feature/<short-name>` | New feature development | `feature/review-submission` |
| `feature/<task-id>-<name>` | Task-linked feature | `feature/T08-submission-form` |
| `bugfix/<issue-id>-<name>` | Bug fix from issue tracker | `bugfix/42-login-redirect` |
| `hotfix/<version>-<name>` | Critical production patch | `hotfix/v1.0.1-security-patch` |
| `release/<version>` | Release preparation and testing | `release/v1.0.0` |
| `chore/<name>` | Maintenance tasks (deps, CI, docs) | `chore/update-readme` |

**Rules:**
- Branch names are lowercase with hyphens — no spaces or underscores
- Feature branches are created from `develop`, never from `main`
- Hotfix branches are created from `main` and merged into both `main` and `develop`
- `main` branch is protected — no direct commits allowed

---

## 4. Commit Message Convention

Format: `<type>(<scope>): <short description>`

| Type | When to Use | Example |
|------|------------|---------|
| `feat` | New feature added | `feat(review): add submission form with star rating` |
| `fix` | Bug fix | `fix(security): correct CSRF token in login form` |
| `docs` | Documentation only | `docs: add Task 1 problem statement` |
| `style` | Formatting, CSS (no logic change) | `style(css): update badge colours for status states` |
| `refactor` | Code restructuring (no feature/fix) | `refactor(service): extract dashboard stats method` |
| `test` | Adding or updating tests | `test: add unit tests for ReviewService` |
| `chore` | Build system, dependencies, CI | `chore: add Maven Surefire plugin configuration` |
| `init` | First commit / project setup | `init: initial Maven project skeleton` |

---

## 5. Initial Commit History (Evidence)

The following commits are made in sequence to establish the project baseline:

```
Commit 1:  init: initial Maven project structure and .gitignore
Commit 2:  feat(model): add Review and User JPA entities with enums
Commit 3:  feat(config): add Spring MVC, Hibernate and Security configuration
Commit 4:  feat(repo): add ReviewRepository and UserRepository DAOs
Commit 5:  feat(service): add ReviewService business logic and UserDetailsService
Commit 6:  feat(controller): add DashboardController and ReviewController
Commit 7:  feat(views): add JSP templates for dashboard, reviews, login
Commit 8:  feat(css): add full responsive stylesheet
Commit 9:  test: add ReviewService unit tests with JUnit 5 and Mockito
Commit 10: docs: add Task 1-4 documentation in docs/ directory
Commit 11: chore: add GitHub issue templates and README
```

---

## 6. GitHub Issues to Create

After repository initialization, create the following issues to track MVP tasks:

| Issue # | Title | Label | Milestone |
|---------|-------|-------|-----------|
| #1 | Set up Maven project with Spring MVC and Hibernate | `enhancement` | Sprint 1 |
| #2 | Implement Review submission form for CUSTOMER role | `enhancement` | Sprint 1 |
| #3 | Implement Review list page with filter and search | `enhancement` | Sprint 1 |
| #4 | Implement role-based access control (RBAC) | `enhancement` | Sprint 1 |
| #5 | Implement moderation status workflow panel | `enhancement` | Sprint 2 |
| #6 | Implement summary dashboard with KPI cards | `enhancement` | Sprint 2 |
| #7 | Write unit tests for ReviewService | `testing` | Sprint 2 |
| #8 | Add GitHub issue templates and branch policy | `documentation` | Sprint 2 |

---

## 7. Git Commands Used (Step-by-Step)

```bash
# 1. Initialize local Git repository
git init

# 2. Configure Git identity (first time)
git config user.name "Rushikesh Gawade"
git config user.email "rushikesh@example.com"

# 3. Add remote (GitHub repo)
git remote add origin https://github.com/RushikeshGawade01/customer_review_moderation_portal.git

# 4. Stage all files
git add .

# 5. Initial commit
git commit -m "init: initial Maven project structure and .gitignore"

# 6. Push to main
git push -u origin main

# 7. Create and switch to develop branch
git checkout -b develop
git push -u origin develop

# 8. Verify history
git log --oneline --graph --all

# 9. Create a feature branch (example)
git checkout -b feature/review-submission
# ... implement feature ...
git add .
git commit -m "feat(review): add submission form with star rating"
git push -u origin feature/review-submission
```

---

## 8. Protected Branch Rules (Recommended GitHub Settings)

Navigate to: **Settings → Branches → Branch protection rules**

### For `main` branch:
- [x] Require a pull request before merging
- [x] Require at least 1 approving review
- [x] Dismiss stale pull request approvals when new commits are pushed
- [x] Require status checks to pass before merging (Maven build)
- [x] Include administrators
- [x] Do not allow force pushes

### For `develop` branch:
- [x] Require a pull request before merging
- [x] Require status checks to pass (Maven build)

---

*Document prepared: October 2026 | Author: Rushikesh Gawade*
