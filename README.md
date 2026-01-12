# SOLID in Practice (Java)

Practical examples of SOLID principles applied to real-world Java code, focusing on refactoring, design trade-offs, and testability.

This repository is not about theoretical definitions or academic examples.
Instead, it demonstrates how SOLID principles naturally emerge when solving real problems and improving problematic codebases.

---

## 🎯 Goals

- Demonstrate **SOLID principles through practical refactoring**
- Show **why** and **when** design decisions are made
- Highlight **trade-offs**, not dogma
- Improve **testability, readability, and maintainability**
- Provide examples that reflect **real-world engineering scenarios**

---

## 🧠 Approach

Each module starts with **intentionally problematic code**, simulating common issues found in legacy or fast-growing systems.

From there, the code evolves step by step, applying SOLID principles where they actually make sense — avoiding overengineering and unnecessary abstractions.

Key focus areas:
- Clear responsibilities
- Explicit dependencies
- Meaningful abstractions
- Design decisions driven by change, not theory

---

## 📦 Project Structure

```text
solid-in-practice/
├── 01-srp/   # Single Responsibility Principle
├── 02-ocp/   # Open/Closed Principle
├── 03-lsp/   # Liskov Substitution Principle
├── 04-isp/   # Interface Segregation Principle
├── 05-dip/   # Dependency Inversion Principle
└── README.md
```


Each module contains:
- A **problematic implementation** (`before`)
- One or more **refactored solutions** (`after`)
- Unit tests demonstrating improved testability
- A README explaining decisions and trade-offs

---

## 🔍 Covered Principles

### 01 — Single Responsibility Principle (SRP)
- Identifying "God classes"
- Refactoring by **reason to change**
- Improving isolation and testability

### 02 — Open/Closed Principle (OCP)
- Eliminating conditional logic
- Applying Strategy and extension-based designs
- Knowing when to stop abstracting

### 03 — Liskov Substitution Principle (LSP)
- Broken inheritance contracts
- Behavioral incompatibilities
- Replacing inheritance with composition

### 04 — Interface Segregation Principle (ISP)
- Fat interfaces and leaky contracts
- Client-specific interfaces
- Cleaner API boundaries

### 05 — Dependency Inversion Principle (DIP)
- Decoupling domain from infrastructure
- Ports and adapters
- Preparing the ground for hexagonal architectures

---

## 🧪 Testing Philosophy

- Tests are used as **design feedback**
- Focus on **unit-level clarity**, not framework-heavy setups
- Mocking is kept minimal and intentional
- Test failures should clearly communicate design issues

---

## 🛠️ Tech Stack

- Java 21+
- JUnit 5
- Maven
- IntelliJ IDEA
- Ubuntu Linux

---

## 🚫 What This Repository Is Not

- A SOLID cheat sheet
- A design patterns catalog
- A framework comparison
- A production-ready system

The goal is **learning through realistic engineering decisions**, not showcasing complexity for its own sake.

---

## 👤 Target Audience

- Senior Java Developers
- Software Engineers interested in clean design
- Tech Leads and Architects
- Developers dealing with legacy codebases

---

## 📄 License

This project is licensed under the MIT.
