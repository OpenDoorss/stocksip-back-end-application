# StockSip | Back-End Application

## Summary

StockSip is a specialized inventory management solution designed specifically for liquor store owners and suppliers. Key features include:

- Efficient management of liquor stock across multiple warehouses.
- User-friendly interface for tracking stock levels.
- Comprehensive sales analytics and reporting.
- Integration with suppliers for seamless order management.
- Support for multiple warehouses.

## Features

The application includes the following documentation:

- CRUD operations for managing liquor stock.
- User authentication and authorization.
- Role-based access control for different user types (liquor store owners and suppliers).
- Real-time stock updates and notifications.
- Detailed sales reports and analytics.
- Profile management for users.

## Bounded Contexts

This version of StockSip is focused on the stock management aspect of the application, managing the profiles of the users and allow user authentication. It includes the following bounded contexts:

### Authentication Context

This context handles user authentication and authorization, ensuring secure access to the application. It includes the following features:

- User registration and login.
- Role-based access control.
- Password management (reset, change).
- Token-based authentication.
- Session management.

This context includes also an anticorruption layer to ensure that the authentication process is secure and does not interfere with the core business logic of the application. Its capabilities include:

- Validating user credentials.
- Generating and validating authentication tokens.
- Ensuring secure password storage and management.
- Implementing role-based access control to restrict access to certain features based on user roles.

### Profile Management Context

This context manages user profiles, allowing users to view and update their personal information. It includes the following features:

- User profile creation and updates.
- Profile picture management.
- Plan details and benefits information.

### Reporting and Analytics Context

This context provides detailed sales reports and analytics, helping users make informed decisions based on their stock and sales data. It includes the following features:

- Restock planning.
- Care guides creation for liquor products.
- Payment history tracking.
- Loses and damages reporting.
- Dashboard analytics for stock and sales.

### Alerts and Notifications Context

This context manages real-time stock updates and notifications, ensuring users are always informed about their inventory status. It includes the following features:

- Stock level alerts.
- Expiration date notifications.
- The creation of alerts for low stock levels.

This context also includes an anticorruption layer to ensure that the alert and notification system is secure and does not interfere with the core business logic of the application. Its capabilities include:

- Monitoring stock levels and expiration dates.
- Generating alerts for low stock levels or approaching expiration dates.

### Payments and Subscriptions Context

This context handles payment processing and subscription management, allowing users to manage their billing and subscription plans. It includes the following features:

- Payment processing for subscriptions.
- Subscription plan management for both of the target segments.

### Inventory Management Context

This context focuses on managing liquor stock across multiple warehouses, allowing users to track stock levels, add new products, and manage inventory efficiently. It includes the following features:

- Warehouse management (add, update, delete warehouses).
- Product management (add, update, delete products).
- Stock tracking (view current stock levels, update stock).
- Count the purchased products and the total amount of the purchase.
- Product exits and entries management.
- Product expiration date management.
- Product care guides management.

This context also includes an anticorruption layer to ensure that the inventory management system is secure and does not interfere with the core business logic of the application. Its capabilities include:

- Managing multiple warehouses and their stock levels.
- Tracking product entries and exits.
- Managing product expiration dates and care guides.