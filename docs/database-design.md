# PMC Connect — Database Design

## 1. Project Overview

PMC Connect is a coaching-centre management system for six coaching centres. Teachers submit class-wise attendance reports, and administrators manage teacher approvals, centre assignments, reports, and attendance summaries.

Attendance is recorded as totals only. Individual student records are not stored.

## 2. Database Tables

### 2.1 centres

Stores coaching-centre information.

| Column | Purpose |
|---|---|
| id (PK) | Unique centre identifier |
| name | Centre name |
| location | Centre location |
| description | Centre description |

### 2.2 users

Stores teacher and administrator accounts.

| Column | Purpose |
|---|---|
| id (PK) | Unique user identifier |
| full_name | User's full name |
| email (UNIQUE) | Login email |
| password | BCrypt password hash |
| role | ADMIN or TEACHER |
| status | PENDING, APPROVED, REJECTED, or INACTIVE |

### 2.3 teacher_assignments

Connects teachers to their assigned centres.

| Column | Purpose |
|---|---|
| id (PK) | Unique assignment identifier |
| teacher_id (FK) | References users.id |
| centre_id (FK) | References centres.id |
| assigned_at | Assignment timestamp |

### 2.4 class_reports

Stores one attendance report per class period.

| Column | Purpose |
|---|---|
| id (PK) | Unique report identifier |
| teacher_id (FK) | References users.id |
| centre_id (FK) | References centres.id |
| class_name | Class or grade |
| subject | Subject taught |
| report_date | Date of the class |
| start_time | Class start time |
| end_time | Class end time |
| total_students | Total students in the class |
| present_students | Students present |
| topics_covered | Topics taught |
| created_at | Report creation timestamp |
| updated_at | Last update timestamp |

Calculated values:
- Absent students = total_students - present_students
- Attendance percentage = present_students / total_students × 100

Validation: total_students must be greater than zero, present_students must be at least zero and no greater than total_students, and end_time must be after start_time.

### 2.5 teacher_approval_history

Records administrative decisions about teacher accounts.

| Column | Purpose |
|---|---|
| id (PK) | Unique history identifier |
| teacher_id (FK) | Teacher whose account was reviewed |
| admin_id (FK) | Administrator who made the decision |
| decision | Approval or rejection decision |
| notes | Optional decision notes |
| created_at | Decision timestamp |

## 3. Relationships

- One user can have multiple teacher assignments.
- One centre can have multiple teacher assignments.
- One teacher can submit multiple class reports.
- One centre can have multiple class reports.
- One teacher can have multiple approval-history records.
- One administrator can make multiple approval decisions.

The teacher_assignments table resolves the many-to-many relationship between teachers and centres.

## 4. Security Rules

- Store password hashes, never plain-text passwords.
- Only administrators can approve teachers or assign centres.
- Only approved, active teachers can submit reports.
- Verify the authenticated user's identity and centre assignment on the backend.
- Restrict report access according to the user's role and permissions.

## 5. Design Notes

Absent counts and attendance percentages are calculated from stored totals instead of being stored separately.

The database schema will be implemented and refined as the project requirements are translated into JPA entities and database constraints.