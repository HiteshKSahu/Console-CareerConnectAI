# Console CareerConnect AI — Interactive Terminal Placement Platform

**Console CareerConnect AI** is a standalone, interactive command-line (CLI) console application for campus placement management and context-aware student career guidance powered by local LLMs via Ollama.

---

## 1. Project Overview & Features
- **Interactive Terminal UI**: Menu-driven command line interface directly in your terminal.
- **Student Profile Management**: Enforces data validation, CGPA/backlog tracking, skills mapping, and email uniqueness.
- **Company & Placement Drive Management**: Allows registering companies and publishing placement drives with specific criteria (minimum CGPA, backlog caps, allowed programmes, target graduation year, and required skills).
- **Strategy Pattern Eligibility Engine**: Evaluates student eligibility deterministically using policy classes (`CgpaPolicy`, `BacklogPolicy`, `SkillPolicy`, `GraduationYearPolicy`) and outputs itemized pass/fail criteria explanations.
- **Application Lifecycle Engine**: Enforces application creation, duplicate application prevention, deadline enforcement, and strict state transitions (`SUBMITTED` → `UNDER_REVIEW` → `SHORTLISTED` → `SELECTED`/`REJECTED`).
- **AI Assistant Integration**: Context-aware career advice via local **Ollama** LLM (`llama3.2`).
- **Pre-loaded 7-Step Automated Demo**: Run option `9` in the menu to execute the complete capstone presentation scenario in 1 keystroke.

---

## 2. Technology Stack & Requirements
- **Java**: 21 (compatible with Java 17+)
- **Framework**: Spring Boot 3.2.12
- **Persistence**: In-Memory Repositories (`ConcurrentHashMap`)
- **LLM Engine**: Ollama running locally (`llama3.2`)
- **Build Tool**: Included Maven Wrapper (`mvnw.cmd` / `./mvnw`)

---

## 3. How to Run the Console Application

Open PowerShell / Terminal:

```powershell
cd "C:\Users\MCX_Virtus\Desktop\Console CareerConnectAI"

# Set Java 21 path and run
$env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
.\mvnw.cmd spring-boot:run
```

The application will immediately launch into the interactive menu:

```
=======================================================
     WELCOME TO CONSOLE CAREERCONNECT AI PLATFORM     
=======================================================

================ MAIN MENU ================
 1. [Student]  Create / Register Profile
 2. [Student]  List All Students
 3. [Admin]    Create / Register Company
 4. [Admin]    Publish Placement Drive
 5. [Student]  Check Eligibility for Drive
 6. [Student]  Submit Application to Drive
 7. [Admin]    Update Application Status
 8. [AI Chat]  Ask AI Career Assistant (Ollama)
 9. [Demo]     Run Pre-loaded 7-Step Auto-Demo
 0. Exit System
===========================================
Select an option (0-9): 
```

---

## 4. Design Patterns Used

1. **Strategy Pattern (`com.careerconnect.policy`)**:
   - `EligibilityPolicy` interface implemented by `CgpaPolicy`, `BacklogPolicy`, `SkillPolicy`, `GraduationYearPolicy`, and orchestrated by `CompositeEligibilityPolicy`.
2. **Adapter Pattern (`com.careerconnect.chat`)**:
   - `ChatClient` interface adapted by `OllamaChatClient` for local LLM HTTP integration.
3. **Repository Pattern (`com.careerconnect.repository`)**:
   - Repository abstractions implemented by in-memory `ConcurrentHashMap` classes.
