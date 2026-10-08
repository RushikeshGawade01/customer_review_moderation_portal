# Task 1 — Problem Definition and Scope
## Customer Review Moderation Portal (CRMP)

---

## 1. Problem Statement

> **Without a structured moderation system, online platforms cannot reliably control the quality, authenticity, and safety of customer reviews — leading to brand damage, regulatory risk, and eroded consumer trust.**

Online businesses receive hundreds to thousands of customer reviews per day across their platforms and product pages. The current state-of-practice for small-to-mid-sized organisations involves:
- Manual email-based triage with no audit trail
- No unified view for moderators across products
- No role separation between customers, moderators, and administrators
- Delayed moderation cycles of 24–72 hours
- No analytics or reporting on review health

**CRMP** addresses all these gaps by providing a centralised, role-based, Git-backed web portal that manages the complete review lifecycle from submission to decision.

---

## 2. Target Users

| User Type | Description |
|-----------|-------------|
| **End Customers** | Consumers who write and submit product/service reviews |
| **Content Moderators** | Staff who triage, approve, reject, or flag reviews |
| **Platform Administrators** | Manage system users, configure workflows, view full analytics |
| **Product Managers** | Consume read-only dashboards and approval KPIs |
| **DevOps / IT Team** | Deploy, maintain, and monitor the application infrastructure |

---

## 3. Stakeholder List

| Stakeholder | Interest | Influence |
|-------------|---------|-----------|
| **Business Owner** | Protect brand reputation via quality moderation | High |
| **Legal / Compliance** | Ensure no defamatory/false reviews go live | High |
| **Marketing Team** | Leverage genuine reviews for campaigns | Medium |
| **Customer Support** | Escalate flagged reviews with context | Medium |
| **IT / DevOps** | Reliable, secure, maintainable deployment | High |
| **End Customers** | Fair, transparent review process | Medium |
| **Moderator Team** | Efficient tooling with clear workflow | High |

---

## 4. Existing Pain Points

| # | Pain Point | Impact |
|---|-----------|--------|
| P1 | No centralised moderation tool — email/spreadsheet chaos | High |
| P2 | No audit trail for moderation decisions | High |
| P3 | All team members share the same access level | High |
| P4 | Spam and fake reviews go undetected for days | High |
| P5 | Cannot search or filter reviews efficiently | Medium |
| P6 | No dashboard to track moderation backlog | Medium |
| P7 | No standardised escalation (Flagging) path | Medium |
| P8 | Manual deployment causes inconsistent environments | Low |

---

## 5. Objectives

1. **Centralise** — Single portal for all review submissions and moderation decisions
2. **Enforce Role-Based Workflow** — Customers submit; Moderators decide; Admins oversee
3. **Accelerate Moderation** — Target <4-hour median review-to-decision time (vs current 48h)
4. **Provide Audit Trail** — Every moderation action recorded with who/when/why
5. **Enable Analytics** — Live dashboard with approval rates and backlog metrics
6. **Enable DevOps** — Full CI/CD pipeline from commit to deployed container

---

## 6. Constraints

| Type | Constraint |
|------|-----------|
| **Technology** | Java 11, Spring MVC, Hibernate, H2 (dev), Tomcat 9, Maven |
| **Time** | 15-task MVP sprint (academic semester timeline) |
| **Team** | Single developer (Rushikesh Gawade) |
| **Budget** | Zero cost — all open-source tooling only |
| **Hosting** | Local Tomcat for phases 1–3; Docker for phase 4 |
| **Security** | Spring Security RBAC; CSRF protection; BCrypt passwords |
| **Database** | H2 in-memory for MVP (swap to MySQL/PostgreSQL for production) |

---

## 7. Measurable Success Criteria (KPIs)

| KPI | MVP Target |
|-----|-----------|
| Application deploys successfully on Tomcat | ✅ Pass/Fail |
| All 3 roles (ADMIN / MODERATOR / CUSTOMER) function correctly | ✅ Pass/Fail |
| Review CRUD operations (Create, View, Update, Search) work | ✅ Pass/Fail |
| Status transitions: PENDING → APPROVED / REJECTED / FLAGGED | ✅ Pass/Fail |
| Dashboard shows accurate real-time stats | ✅ Pass/Fail |
| Maven build (`mvn clean package`) succeeds with 0 errors | ✅ Pass/Fail |
| ≥ 3 unit tests pass in CI | ✅ Pass/Fail |
| Git history has ≥ 5 meaningful commits | ✅ Pass/Fail |
| README, issue templates, and branch policy documented | ✅ Pass/Fail |
| Approval rate calculation is accurate | ✅ Verified |

---

## 8. Approved MVP Scope — 15 Tasks

| Task # | Task Description | Role(s) |
|--------|-----------------|---------|
| **T01** | Project setup: Maven POM, folder structure, Git init | All |
| **T02** | Spring MVC + Security configuration (Java-based) | Admin/DevOps |
| **T03** | H2 database configuration + Hibernate session factory | Admin/DevOps |
| **T04** | `Review` and `User` domain models with JPA annotations | Dev |
| **T05** | Repository (DAO) layer — ReviewRepository + UserRepository | Dev |
| **T06** | Service layer — ReviewService + CustomUserDetailsService | Dev |
| **T07** | Login page + Spring Security authentication | Customer/Mod/Admin |
| **T08** | Review Submission form (Customer role) | Customer |
| **T09** | Review List page with status filter and keyword search | All |
| **T10** | Review Detail page with full metadata display | All |
| **T11** | Moderation Panel — status update by Moderator/Admin | Moderator/Admin |
| **T12** | Summary Dashboard with 6 KPI statistics cards | Moderator/Admin |
| **T13** | CSS styling and responsive UI layout | Dev |
| **T14** | Unit tests (JUnit 5 + Mockito) for service layer | Dev |
| **T15** | README, issue templates, GitHub setup and documentation | All |

---

## 9. Out of Scope (Phase 1 MVP)

- Email notifications on moderation decisions
- Pagination (all records loaded in MVP)
- Image/file attachments to reviews
- OAuth2 / social login
- Multi-language support
- MySQL/PostgreSQL (deferred to production phase)
- Docker deployment (Phase 3 task)
- Jenkins CI (Phase 2 task)

---

*Document prepared: October 2026 | Author: Rushikesh Gawade*
