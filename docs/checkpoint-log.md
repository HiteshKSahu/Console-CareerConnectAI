# Stage Checkpoint Log - CareerConnect AI

## Stage Checkpoints Summary

| Day / Stage | Description | Key Deliverable | Status |
|-------------|-------------|-----------------|--------|
| **Day 1 - D1-A** | Problem Analysis & Scope definition | Scope, actors, core assumptions documented | **Completed** |
| **Day 1 - D1-B** | Domain Modeling & Responsibilities | CRC responsibility matrix & class entities | **Completed** |
| **Day 1 - D1-C & D1-D**| Core Entities & Storage Layer | Student, Company, Drive, Application entities + ConcurrentHashMap repositories | **Completed** |
| **Day 2 - D2-A** | Eligibility Strategy Pattern | `EligibilityPolicy` interface & implementations (`CgpaPolicy`, `BacklogPolicy`, `SkillPolicy`, `CompositeEligibilityPolicy`) | **Completed** |
| **Day 2 - D2-B** | Application State Lifecycle | Enforced status transitions in `ApplicationStatus` & duplicate application guard | **Completed** |
| **Day 3 - D3-A & D3-B**| REST API Contract & HLD | Resource-oriented REST contract & Spring Boot application skeleton | **Completed** |
| **Day 3 - D3-C & D3-D**| Controllers, DTOs & Validation | Controller endpoints with Bean Validation (`@Valid`) & `GlobalExceptionHandler` | **Completed** |
| **Day 4 - D4-A & D4-B**| Ollama Chat Client Adapter | `ChatClient` interface & `OllamaChatClient` adapter with HTTP timeout error mapping | **Completed** |
| **Day 4 - D4-C & D4-D**| Assistant Service & Graceful Degradation | `CareerAssistantService` with context builder & 503 handling when Ollama is down | **Completed** |
| **Day 5 - D5-A to D5-D**| End-to-End Build & Test Package | Clean Maven build, Postman test suite (T-01 to T-17) & documentation package | **Completed** |

---

## Pattern Implementation Evidence

1. **Strategy Pattern**:
   - `com.careerconnect.policy.EligibilityPolicy`
   - `com.careerconnect.policy.CgpaPolicy`
   - `com.careerconnect.policy.BacklogPolicy`
   - `com.careerconnect.policy.SkillPolicy`
   - `com.careerconnect.policy.GraduationYearPolicy`
   - `com.careerconnect.policy.CompositeEligibilityPolicy`
   - *Purpose*: Decouples drive criteria evaluation from application service logic. Enables flexible composition of criteria without modifying caller code.

2. **Adapter Pattern**:
   - `com.careerconnect.chat.ChatClient` (Target Interface)
   - `com.careerconnect.chat.OllamaChatClient` (Adapter Implementation)
   - *Purpose*: Wraps the HTTP interaction with Ollama JSON endpoints, mapping network timeouts/errors to standard domain exceptions without polluting high-level services.

3. **Repository Pattern**:
   - `com.careerconnect.repository.StudentRepository` / `InMemoryStudentRepository`
   - `com.careerconnect.repository.CompanyRepository` / `InMemoryCompanyRepository`
   - `com.careerconnect.repository.DriveRepository` / `InMemoryDriveRepository`
   - `com.careerconnect.repository.ApplicationRepository` / `InMemoryApplicationRepository`
   - *Purpose*: Abstracts the storage mechanism away from services, allowing clean in-memory storage while keeping the design ready for future database migration.
