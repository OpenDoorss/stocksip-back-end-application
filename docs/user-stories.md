# User Stories
This document contains the technical user stories for the ```stocksip-platform``` REST API from the perspective of a developer interacting with it thought HTTP request.

## TS0025: Retrieve a Warehouse via Get request
**As a** developer, **I want** to get a warehouse by its ID, **so that** I can retrieve the details of a specific warehouse.

**Acceptance criteria**
- Scenario 1: Successful retrieval
  - **Given a** valid warehouse ID
  - **When** the developer send a GET request to `/warehouses/{id}`
  - **Then** I should receive a 200 OK response with the warehouse details in JSON format
- Scenario 2: Warehouse not found
    - **Given a** non-existent warehouse ID
    - **When** the developer sends a GET request to `/warehouses/{id}`
    - **Then** I should receive a 404 Not Found response with an error message

