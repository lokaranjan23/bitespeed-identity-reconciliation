# Bitespeed Backend Task - Identity Reconciliation

##  Introduction

This project is a solution for the Bitespeed Backend Task.

The objective of this application is to identify and reconcile customer identities based on email and phone number.

A customer may place multiple orders using different email addresses or phone numbers. This system links those records together and maintains a single primary identity for each customer.

---

##  Problem Overview

When a request is received with an email and/or phone number:

1. If no matching record exists → a new PRIMARY contact is created.
2. If matching records exist → the system finds the root PRIMARY contact.
3. If multiple PRIMARY contacts are found → the oldest one remains PRIMARY.
4. Any new data is stored as a SECONDARY contact linked to the PRIMARY.
5. The response always returns a consolidated identity view.

There will always be only one PRIMARY contact per identity group.

---

##  Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- REST API

---

##  Project Structure

bitespeed-identity-reconciliation  
│  
├── src  
│   ├── main  
│   │   ├── java/com/identityReconciliation  
│   │   │   ├── controller  
│   │   │   ├── service  
│   │   │   ├── repository  
│   │   │   ├── entity  
│   │   │   └── Dto  
│   │   └── resources  
│   │       └── application.yml  
│  
├── pom.xml  
├── README.md  
└── .gitignore

---

##  API Details

### Endpoint

POST /identify

### Local URL

http://localhost:8080/identify

### Hosted URL

(Add your Render deployed URL here after deployment)

Example:
https://your-app-name.onrender.com/identify

---

##  Request Format

At least one field must be provided.

```json
{
  "email": "string (optional)",
  "phoneNumber": "string (optional)"
}

```
## Response Format

```json

{
  "contact": {
    "primaryContactId": number,
    "emails": [string],
    "phoneNumbers": [string],
    "secondaryContactIds": [number]
  }
}

```

### Field Description

1. primaryContactId : The ID of the root primary contact.

2. emails : List of all unique email addresses linked to the identity.

3. phoneNumbers : List of all unique phone numbers linked to the identity.

4. secondaryContactIds : List of IDs of secondary contacts associated with the primary.