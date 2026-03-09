
# JavaFX Bookstore Management System

A JavaFX desktop application that simulates a digital bookstore with role-based functionality for **Customers** and **Owners**.

The system supports book purchases, inventory management, user management, and a **point-based membership reward system** using object-oriented design principles and the **State Design Pattern**.

This project was developed as part of **COE 528 – Object-Oriented Engineering Analysis & Design**.

---

# Features

## Customer Features
- Secure login system
- View available books
- Select and purchase books
- Earn points from purchases
- Redeem points for discounts
- Membership status (Silver / Gold)
- Purchase summary screen

## Owner Features
- Add books to the store
- Remove books from the store
- Add new users
- Remove users
- Manage bookstore inventory

---

# Membership System

Customers accumulate points from purchases.

Membership status automatically changes depending on points:

| Membership | Requirement | Benefits |
|------------|-------------|----------|
| Silver | < 1000 points | No discount |
| Gold | ≥ 1000 points | 10% discount |

The **State Design Pattern** is used so the behavior of the user object changes depending on its state (Silver or Gold).

---

# Design Patterns Used

## State Pattern
Used to represent customer membership status.

Classes involved:
- `MemberState`
- `SilverMemberState`
- `GoldMemberState`

This allows the system to change discount behavior dynamically without modifying the `User` class.

## Singleton Pattern
Used for managing persistent data files:

- `userList`
- `bookList`

Ensures only **one instance** manages the file resources.

---

# System Architecture

The system is structured using an object-oriented design with separation between:

- **UI Screens**
- **Domain Objects**
- **Data Management Classes**

Major components include:

### UI Screens
- LoginScreen
- CustomerScreen
- CustomerCostScreen
- OwnerSelectScreen
- ownerManageBooks
- ownerManageUsers

### Core Classes
- User
- Book
- MemberState
- SilverMemberState
- GoldMemberState

### Data Managers
- userList
- bookList

---

# UML Diagrams

## Use Case Diagram
Shows the interactions between system actors.

Actors:
- Customer
- Owner

Key use cases include:
- Login
- View Books
- Buy Books
- Manage Books
- Manage Users
- Logout

See: `useCaseDiagram.pdf`

## Class Diagram
Illustrates the relationships between system classes including the State Pattern implementation.

See: `classDiagram.pdf`

---

# Technologies Used

- Java
- JavaFX
- Object-Oriented Programming
- Design Patterns
- File I/O for persistent storage
- NetBeans Ant build system

---

# Project Structure

```
src/
 └── finalproject
      ├── LoginScreen.java
      ├── CustomerScreen.java
      ├── CustomerCostScreen.java
      ├── OwnerSelectScreen.java
      ├── ownerManageBooks.java
      ├── ownerManageUsers.java
      ├── User.java
      ├── Book.java
      ├── MemberState.java
      ├── SilverMemberState.java
      ├── GoldMemberState.java
      ├── userList.java
      └── bookList.java

data/
 ├── bookList.txt
 └── customerList.txt
```

---

# How to Run

## Requirements
- Java 17+
- JavaFX SDK
- NetBeans or IntelliJ recommended

## Steps

1. Clone the repository

```
git clone https://github.com/yourusername/javafx-bookstore-system.git
```

2. Open the project in NetBeans or IntelliJ

3. Run:

```
LoginScreen.java
```

---

# Example Workflow

1. User logs into the system
2. Customer views available books
3. Customer selects books
4. System calculates total cost
5. Points are added or redeemed
6. Membership status is updated automatically

---

# Contributors

- Irtaza Abbasi
- Dean Torallo
- Ayaan Naveed

COE 528 – Object Oriented Engineering Analysis & Design  
Toronto Metropolitan University

---

# Future Improvements

- Database integration (MySQL / PostgreSQL)
- Password hashing and authentication
- REST API backend
- Web version of the bookstore
- Improved UI styling
- Transaction history
