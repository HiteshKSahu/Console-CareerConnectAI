# CareerConnect AI - Requirements and Assumptions

## 1. Project Objectives
- Model the campus placement platform domain using OOP principles (encapsulation, abstraction, polymorphism, composition).
- Apply core design patterns to real software variation points:
  - **Strategy Pattern** for configurable student eligibility evaluation.
  - **Adapter Pattern** for encapsulating local LLM calls via Ollama.
  - **Repository Pattern** for abstracting data persistence.
- Provide a clean, resource-oriented REST API layer with validation and standard HTTP error handling.
- Integrate an advisory AI career assistant without coupling core domain rules to LLM availability.

## 2. Primary Actors
1. **Student**: Creates/updates profile, views drives, checks eligibility, applies to placement drives, and interacts with the AI assistant for career preparation.
2. **Placement Coordinator / Admin**: Registers companies, publishes placement drives with specific criteria, reviews applications, and updates status through the selection pipeline.
3. **AI Career Assistant**: Provides advisory guidance, answers placement FAQs, and natural language explanations based strictly on deterministic system data.
4. **LLM Engine (Ollama)**: Local LLM runtime executing model prompts (e.g. `llama3.2`).

## 3. Core Domain Assumptions
- All test and sample data used within the project is synthetic.
- Core placement data is stored in-memory using concurrent thread-safe maps (`ConcurrentHashMap`).
- A student can submit only **one application per placement drive**.
- Eligibility evaluation is strictly deterministic and calculated by domain logic, **never** by the LLM.
- The AI assistant is strictly advisory and cannot perform write operations or create applications.
- If Ollama is unavailable, all core placement REST APIs continue to function normally, while `/api/chat` returns a graceful HTTP 503 error.
