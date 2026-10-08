# Task 2 — Agile Planning and DevOps Workflow
## Customer Review Moderation Portal (CRMP)

---

## 1. User Stories and Acceptance Criteria

### US-01 — Customer Review Submission
**As a** Customer  
**I want to** submit a product or service review  
**So that** I can share my experience with others

**Acceptance Criteria:**
- [ ] Form accepts: Product Name, Rating (1–5), Review Text
- [ ] Customer name is auto-populated from logged-in session
- [ ] Review is saved with status = PENDING immediately
- [ ] Success message shown after submission
- [ ] Validation prevents empty form submission

---

### US-02 — Review List and Filtering
**As a** Moderator or Admin  
**I want to** view all submitted reviews with filter options  
**So that** I can prioritise my moderation queue effectively

**Acceptance Criteria:**
- [ ] All reviews displayed in a table with columns: ID, Product, Customer, Rating, Status, Date
- [ ] Filter by status (PENDING / APPROVED / REJECTED / FLAGGED)
- [ ] Keyword search across product name, customer name, review text
- [ ] Row click navigates to review detail page
- [ ] Status badge colour-coded per state

---

### US-03 — Review Moderation (Status Update)
**As a** Moderator or Admin  
**I want to** update the status of a review and add a decision note  
**So that** I can enforce the content policy with a documented audit trail

**Acceptance Criteria:**
- [ ] Status dropdown shows current status and all valid transitions
- [ ] Optional moderation note can be added with each decision
- [ ] Moderator's username is recorded automatically
- [ ] Updated status reflects immediately in the list and detail views
- [ ] CUSTOMER role cannot see or access the moderation panel

---

### US-04 — Role-Based Access Control
**As an** Admin  
**I want to** ensure each user can only access features appropriate to their role  
**So that** data integrity and security are maintained

**Acceptance Criteria:**
- [ ] CUSTOMER: can submit reviews and view own submissions only
- [ ] MODERATOR: can view all reviews and moderate them; cannot manage users
- [ ] ADMIN: full access including user overview and moderation
- [ ] Unauthenticated users are redirected to login page
- [ ] Attempting unauthorised actions returns HTTP 403

---

### US-05 — Summary Dashboard
**As a** Moderator or Admin  
**I want to** see a real-time summary of review statistics  
**So that** I can understand the moderation workload at a glance

**Acceptance Criteria:**
- [ ] Dashboard cards display: Total, Pending, Approved, Rejected, Flagged counts
- [ ] Approval Rate (%) calculated correctly
- [ ] Recent 5 reviews shown in a table with quick-view links
- [ ] Data reflects the latest state without manual refresh

---

## 2. Product Backlog

| Story ID | User Story | Priority | Story Points | Sprint |
|----------|-----------|---------|-------------|--------|
| US-04 | Role-based authentication & security | 🔴 Critical | 5 | 1 |
| US-01 | Customer review submission form | 🔴 Critical | 3 | 1 |
| US-02 | Review list with filter and search | 🔴 Critical | 5 | 1 |
| US-03 | Moderation status workflow | 🔴 Critical | 5 | 1 |
| US-05 | Summary dashboard | 🟡 High | 3 | 2 |
| — | Data model + database setup | 🔴 Critical | 2 | 1 |
| — | Spring MVC + Hibernate config | 🔴 Critical | 2 | 1 |
| — | JSP templates + CSS styling | 🟡 High | 3 | 2 |
| — | Unit tests (JUnit + Mockito) | 🟡 High | 2 | 2 |
| — | GitHub setup + documentation | 🟡 High | 2 | 2 |

---

## 3. 15-Task Kanban / Scrum Task Board

### Sprint 1 — Core Infrastructure & CRUD (Tasks T01–T09)

| Task | Description | Status | Assignee |
|------|-------------|--------|---------|
| T01 | Maven project setup, folder structure, Git init | ✅ Done | Rushikesh |
| T02 | Spring MVC + Security Java config | ✅ Done | Rushikesh |
| T03 | H2 + Hibernate SessionFactory config | ✅ Done | Rushikesh |
| T04 | Review + User domain models (JPA) | ✅ Done | Rushikesh |
| T05 | DAO layer — ReviewRepository + UserRepository | ✅ Done | Rushikesh |
| T06 | Service layer — ReviewService + UserDetailsService | ✅ Done | Rushikesh |
| T07 | Login page JSP + Security filter | ✅ Done | Rushikesh |
| T08 | Review submission form (Customer) | ✅ Done | Rushikesh |
| T09 | Review list page + filter/search | ✅ Done | Rushikesh |

### Sprint 2 — Moderation, Dashboard & Quality (Tasks T10–T15)

| Task | Description | Status | Assignee |
|------|-------------|--------|---------|
| T10 | Review detail page | ✅ Done | Rushikesh |
| T11 | Moderation panel (Moderator/Admin) | ✅ Done | Rushikesh |
| T12 | Summary dashboard with KPI cards | ✅ Done | Rushikesh |
| T13 | CSS styling + responsive layout | ✅ Done | Rushikesh |
| T14 | Unit tests — ReviewService (JUnit 5) | ✅ Done | Rushikesh |
| T15 | README, issue templates, branch policy, docs | ✅ Done | Rushikesh |

---

## 4. Sprint Plan

| Sprint | Duration | Goal | Tasks |
|--------|---------|------|-------|
| Sprint 1 | Week 1 (Days 1–5) | Build working CRUD + authentication backbone | T01–T09 |
| Sprint 2 | Week 2 (Days 6–10) | Complete moderation workflow + dashboard + quality | T10–T15 |

**Sprint Ceremonies:**
- **Daily Standup:** 15-min self-review of yesterday/today/blockers (journalled)
- **Sprint Review:** Demo working features against acceptance criteria
- **Sprint Retrospective:** Document lessons learned

---

## 5. Definition of Done (DoD)

A task is considered **Done** when ALL of the following are true:

| # | Criterion |
|---|-----------|
| DoD-1 | Code is written and compiles without errors (`mvn compile`) |
| DoD-2 | Feature matches the user story acceptance criteria |
| DoD-3 | Unit tests written and pass (`mvn test`) |
| DoD-4 | Code committed to feature branch with a descriptive commit message |
| DoD-5 | Feature branch merged into `develop` via Pull Request |
| DoD-6 | No high-severity security issues in the feature |
| DoD-7 | JSP views render correctly in browser |
| DoD-8 | Relevant documentation updated in `docs/` |
| DoD-9 | WAR builds successfully: `mvn clean package` |

---

## 6. DevOps Lifecycle Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                    DEVOPS LIFECYCLE — CRMP                   │
└──────────────────────────────────────────────────────────────┘

 Developer       Git/GitHub         Jenkins          Tomcat/Docker
     │               │                 │                  │
     │── git commit ─►│                 │                  │
     │── git push ───►│                 │                  │
     │               │── webhook ──────►│                  │
     │               │                 │── mvn clean      │
     │               │                 │   compile        │
     │               │                 │── mvn test ─────►│ (JUnit)
     │               │                 │── mvn package    │
     │               │                 │   → crmp.war     │
     │               │                 │── Archive WAR    │
     │               │                 │── Deploy to ─────►│ Tomcat
     │               │                 │   Tomcat         │
     │               │◄── Build Status ─│                  │
     │◄── Notif ─────│                 │                  │
     │               │                 │                  │
     │         (Phase 3: Docker)        │                  │
     │               │── docker build ──►                  │
     │               │── docker push → DockerHub           │
     │               │── docker run → Container            │
     │               │                                     │
     │         (Phase 4: Ansible)                          │
     │               │── ansible-playbook ─► Target Node   │
     │               │   provision + deploy + health-check │
```

### DevOps Stages Explained

| Stage | Tool | Description |
|-------|------|-------------|
| **Plan** | GitHub Issues | Track stories, bugs, features |
| **Code** | Git + IntelliJ IDEA | Feature branches, meaningful commits |
| **Build** | Maven 3.9 | `mvn clean package` → `crmp.war` |
| **Test** | JUnit 5 + Mockito | Unit tests; Selenium in Phase 9 |
| **Release** | Git Tags | `v1.0.0`, `v1.1.0` semantic versioning |
| **Deploy** | Tomcat 9 | WAR deployed to `/webapps/crmp` |
| **Operate** | Logback + H2 Console | Runtime monitoring and logs |
| **Monitor** | Dashboard + Logs | Review KPIs, error rates |

---

*Document prepared: October 2026 | Author: Rushikesh Gawade*
