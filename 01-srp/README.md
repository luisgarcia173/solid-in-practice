# 01 — Single Responsibility Principle (SRP)

This module demonstrates the **Single Responsibility Principle** through a realistic refactoring scenario commonly found in legacy or fast-growing Java systems.

The focus is not on the definition of SRP, but on **recognizing design smells**, understanding **why they hurt**, and applying SRP as a **natural consequence of better design decisions**.

---

## 📖 Problem Context

The initial implementation revolves around an `OrderProcessor` class that is responsible for multiple concerns at once:

- Validating orders
- Calculating prices
- Applying discounts
- Persisting data
- Sending notifications
- Logging execution details

This type of class often emerges organically under delivery pressure and quickly becomes a **maintenance bottleneck**.

---

## 🚨 Symptoms Observed

- Difficult and slow unit tests
- High coupling between unrelated concerns
- Changes in one area frequently break others
- Mocking becomes complex and fragile
- The class grows continuously over time

These symptoms indicate **multiple reasons to change**, violating SRP.

---

## 🧠 Design Decision

Instead of splitting the class by technical layers, responsibilities were separated by **reason to change**:

- Validation logic
- Price calculation
- Discount rules
- Persistence
- Notifications

Each responsibility was extracted into its own dedicated component, coordinated by a higher-level service.

SRP was applied as a **means to improve testability and clarity**, not as an end in itself.

---

## 🔄 Refactoring Overview

### Before
- One large class handling multiple responsibilities
- Implicit dependencies
- Hard-to-isolate behavior
- High cognitive load

### After
- Small, focused classes with clear intent
- Explicit dependencies
- Improved test isolation
- Easier evolution and maintenance

---

## ⚖️ Trade-offs Considered

Applying SRP introduces some costs:

- Increased number of classes
- More wiring between components
- Slightly more indirection

These trade-offs were considered acceptable in exchange for:
- Better testability
- Safer changes
- Clearer separation of concerns

Overengineering was intentionally avoided.

---

## 🧪 Testing Strategy

- Each responsibility is tested in isolation
- Tests act as **design feedback**
- Mocking is used sparingly and intentionally
- The orchestration layer is tested for behavior, not implementation

---

## 📌 Key Takeaways

- SRP is about **reasons to change**, not number of methods
- God classes are often a symptom of unclear boundaries
- Better design emerges from refactoring real problems
- SRP improves systems when applied pragmatically, not dogmatically

---

## 🔜 Next Step

The refactored discount logic naturally leads to a new challenge:
**extending behavior without modifying existing code**.

This evolution is explored in the next module: **02 — Open/Closed Principle (OCP)**.
