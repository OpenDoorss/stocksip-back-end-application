# User Stories

This document contains the technical user stories for the ```stocksip-platform``` REST API from the perspective of a developer interacting with it thought HTTP request.

## TS0012: Endpoint to generate loss reports

**As a** developer, **I need** to implement the GET /api/reports/losses endpoint to generate reports on inventory losses (expired product, shrinkage, etc.).

**Acceptance Criteria**

- Scenario 1: Successful report generation

  - **Given** that the user has access to the inventory with lost products
  - **When** the user requests the loss report
  - **Then** system responds with a code 200 and the details of the loss

- Scenario 2: No losses in inventory

  - **Given** that there are no losses in inventory
  - **When** the user requests the report
  - **Then** system responds with a code 200 and a message saying "No losses found"

## TS0011: Endpoint to register a new conservation guide

**As a** developer, **I need** to implement the POST /api/guides/preservation endpoint to allow users to register preservation guides for products.

**Acceptance Criteria**

- Scenario 1: Successful Conservation Guide Registration

  - **Given** that the user provides valid data for the guide,
  - **When** the system registers the guide in the database,
  - **Then** system responds with a 201 code and the details of the registered guide.

- Scenario 2: Incomplete Data

  - **Given** that the guide does not contain sufficient information (missing product information, conditions),
  - **When** the user submits the POST request,
  - **Then** system responds with a 400 code and a "Missing required data" message.
