workspace "Open-Source-StockSip" "Description" {

    //DIAGRAMA DE CONTEXTO
    //
    //
    model {
    
        //USUARIOS
        liquorStoreOwner = person "Liquor Store Owners" "The owner of the business who manages his inventory and places orders."
        liquorSupplier = person "Liquour Suppliers" "Supplier that provides information on product preservation and liquor sales to the business."
        
        //SOFTWARE      
        stockSip = softwareSystem "StockSip" "Software that manages liquor products" "App" {
        
            //DIAGRAMA DE CONTENEDORES
            //
            //
        
            //Aplicaciones
            singlePageApplication = container "StockSip Frontend Application" "Provides all of the StockSip functionality to the user browser." "" "Web" {
                technology "TypeScript and Angular"
                
                appComponent = component "AppComponent" "Displays and contains the main UI components."
                
                sideNavigationBarComponent = component "SideNavigationBarComponent"
                
                //DIAGRAMA DE COMPONENTES
                
                // BOUNDED CONTEXT AUTHENTICATION
                
                loginComponent = component "LoginComponent" "Handles user authentication."
                confirmCodeComponent = component "ConfirmCodeComponent" ""
                selectPlanComponent = component "SelectPlanComponent" "Lets users choose subscription plans."
                registerComponent = component "RegisterComponent" "User registration form."
            
                authService = component "AuthService" "Manages authentication logic."
                
                profileComponent = component "ProfileComponent" "Displays user profile data."
                editProfileComponent = component "EditProfileComponent" "Edits user profile details."
                profileService = component "ProfileService" "Fetches/updates profile data."
                
                settingsComponent = component "SettingsComponent" "Global app settings panel."
                languageSwitcherComponent = component "LanguageSwitcherComponent" "Allows user to switch app language."
                
                // BOUNDED CONTEXT PAYMENT
                paymentProcessComponent = component "PaymentProcessComponent" "Handles payment transactions."
                paymentService = component "PaymentService" "Manages payment gateways."
                
                // BOUNDED CONTEXT INVENTORY
                
                warehouseListComponent = component "WarehouseListComponent" "Presents a collection of warehouses."
                warehouseItemComponent = component "WarehouseItemComponent" "Shows details of a single warehouse."
                warehouseService = component "WarehouseService" "Manages warehouse data."
                createMovementComponent = component "CreateMovementComponent" "Logs inventory movements (in/out)."
                
                inventoryHealthComponent = component "inventoryHealth" "Tracks inventory metrics"
                
                inventoryComponent = component "InventoryComponent" "Allows the user to manage their inventory for each warehouse."
                inventoryService = component "InventoryService" "Handles inventory operations."
                
                // BOUNDED CONTEXT NOTIFICATION
                notificationListComponent = component "NotificationListComponent" "Displays a collection of notifications."
                notificationItemComponent = component "NotificationItemComponent" "Show details of a single notification."
                notificationService = component "NotificationService" "Manages notification logic"
                
                dashboardComponent = component "DashboardComponent" "Main app dashboard."
                generalSummaryComponent = component "GeneralSummaryComponent" "Displays general business information."
                importantAlertsComponent = component "ImportantAlertsComponent" "Highlights critical alerts."
                quickAccessComponent = component "QuickAccessComponent" "Shortcuts to common actions."
                
                // BOUNDED CONTEXT REPORTING
                
                reportingComponent = component "ReportingComponent" "Show and generates reports (PDF)"
                
                replenishmentPlanComponent = component "ReplenishmentPlanComponent" "Manages purchase/sales orders."
                create-editPlanComponent = component "CreateEditPlanComponent" "Product catalog for suppliers."
                alertsPlanComponent = component "AlertsPlanComponent" "Handles catalog data."
                replenishmentPlanService = component "ReplenishmentPlanService" "Manages inventory restocking rules and automation."
                
                conservationGuideComponent = component "conservationGuideComponent" "Displays product storage/handling guidelines"
                create-editGuideComponent = component "CreateEditGuideComponent" "Creates/modifies product conservation guides."
                conservationGuideService = component "ConservationGuideService" "Handles conservation guide data operations."
                
                invoiceComponent = component "InvoiceComponent" "Main invoice management UI component."
                invoiceListComponent = component "InvoiceListComponent" "Displays a collection of all invoices."
                invoiceItemComponent = component "InvoiceItemComponent" "Shows single invoice details."
                createInvoiceComponent = component "CreateInvoiceComponent" "Generates new invoices."
                invoiceService = component "InvoiceService" "Handles invoice operations."
                
                // BOUNDED CONTEXT ORDER
                orderComponent = component "OrderComponent" "Main order management UI component."
                catalogComponent = component "CatalogComponent" "Product catalog manager."
                catalogListComponent = component "CataListComponent" "Displays a collection of catalogs for the supplier."
                catalogItemComponent = component "CatalogItemComponent" "Shows individual product details."
                create-editCatalogComponent = component "CreateEditCatalogComponent" "Adds/modifies catalog items."
                create-editDiscountComponent = component "CreateEditDiscountComponent" "Manages product discounts."
                catalogService = component "CatalogService" "Handles catalog data operations."
                
                purchaseOrderComponent = component "PurchaseOrderComponent" "Purchase order management UI component."
                createPurchaseOrderComponent = component "CreatePurchaseOrderComponent" "Generates new purchase orders."
                purchaseOrderListComponent = component "PurchaseOrderListComponet" "Displays a collection of all purchase orders"
                purchaseOrderItemComponent = component "PurchaseOrderItemComponet" "Shows single purchase order details."
                purchaseOrdenService = component "PurchaseOrdenService" "Handles purchase order operations."
                
            }
            
            webApplication = container "Web Application" "Delivers the static content and the StockSip single page application" "" "" {
                technology "Java and Spring Boot"
            }
            
            ladingPage = container "StockSip Landing Page" "Static website showcasing key information about StockSip " "" "Web" {
                technology "HTML, CSS and JavaScript"
            }
            
            apiApplication = container "API Application" "Provides StockSip functionality via JSON/HTTP API." "" {
                technology "Java and Spring Boot"
                
                //DIAGRAMA DE COMPONENTES - BOUNDED CONTEXT AUTHENTICATION
                //
                //
                Inventory = component "Inventory Bounded Context" "" "" "Component"
                Order = component "Order Bounded Context" "" "" "Component"
                Authentication = component "Authentication Bounded Context" "" "" "Component"
                Payment = component "Payment Bounded Context" "" "" "Component"
                Reporting = component "Reporting Bounded Context" "" "" "Component"
                Notification = component "Notification Bounded Context" "" "" "Component"
                Shared = component "Shared Bounded Context" "" "" "Component"
                
            }
            
            DBSQL = container "Database" "Stores users information, warehouses, orders, etc. " "" "BD" {
                technology "MySQL"
            }
        }
        
        // -----------------------------------------------------------------------------------------------------
        
        //SISTEMAS EXTERNOS
        gmail = softwareSystem "Gmail" "Google's e-mail service." "ExternalSoftwareSystem"
        paypal = softwareSystem "PayPal" "Payment gateway for processing transactions" "ExternalSoftwareSystem"
        cloudinary = softwareSystem "Cloudinary" "Cloud service for image storage and management" "ExternalSoftwareSystem"
        
        //Relaciones de contexto
        liquorStoreOwner -> stockSip "Manages inventory using"
        liquorSupplier -> stockSip "Shares products data using"
        
        stockSip -> gmail "Sends e-mail via"
        stockSip -> paypal "Process payments via"
        stockSip -> cloudinary "Stores images in"
        
        //Relaciones de contenedores
        
        liquorStoreOwner -> ladingPage "Visits the static website"
        liquorStoreOwner -> webApplication "Visits the dynamic web app"
        liquorStoreOwner -> singlePageApplication "Manages inventory using"
        
        liquorSupplier -> ladingPage "Visits the static website"
        liquorSupplier -> webApplication "Visits the dynamic web app"
        liquorSupplier -> singlePageApplication "Shares products data using"
        
        webApplication -> singlePageApplication "Delivers to the user's web browser"
        
        singlePageApplication -> apiApplication "Make API request to [JSON/HTTP]"
        apiApplication -> DBSQL "Reads from and writes to"
        
        apiApplication -> gmail "Sends e'mail via"
        apiApplication -> paypal "Process payments via"
        apiApplication -> cloudinary "Stores images in"
        
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        
        //Relaciones de componentes - FRONTEND
        //
        //
        webApplication -> appComponent "Delivers to the user's web browser"
        //
        //
        // BOUNDED CONTEXT AUTHENTICATION
        appComponent -> loginComponent "Use"
        loginComponent -> sideNavigationBarComponent "Use"
        loginComponent -> registerComponent "use"
        
        registerComponent -> confirmCodeComponent "use"
        confirmCodeComponent -> selectPlanComponent "use"
        
        loginComponent -> authService "use"
        
        sideNavigationBarComponent -> profileComponent "use"
        profileComponent -> editProfileComponent "use"
        editProfileComponent -> profileService "use"
        profileService -> apiApplication "Make Api request to [JSON/HTTP]"
        
        sideNavigationBarComponent -> settingsComponent "use"
        settingsComponent -> languageSwitcherComponent "use"
        
        // BOUNDED CONTEXT PAYMENT
        selectPlanComponent -> paymentProcessComponent "use"
        paymentProcessComponent -> paymentService "use"
        
        paymentService -> apiApplication "Make Api request to [JSON/HTTP]"
        authService -> apiApplication "Make Api request to [JSON/HTTP]"
        
        //
        //
        // BOUNDED CONTEXT INVENTORY
        sideNavigationBarComponent -> warehouseListComponent "use"
        warehouseListComponent -> inventoryHealthComponent "use"
        warehouseListComponent -> warehouseItemComponent "use"
        warehouseListComponent -> warehouseService "use"
        warehouseService -> apiApplication "Make App request to [JSON/HTTP]
        
        warehouseListComponent -> inventoryComponent "use"
        inventoryComponent -> createMovementComponent "use"
        createMovementComponent -> InventoryService "use"
        InventoryService -> apiApplication "Make App request to [JSON/HTTP]"
        
        //
        //
        // BOUNDED CONTEXT NOTIFICATION
        sideNavigationBarComponent -> notificationListComponent "use"
        notificationListComponent -> notificationItemComponent "use"
        notificationListComponent -> notificationService "use"
        notificationService -> apiApplication "Make App request to [JSON/HTTP]"
        
        sideNavigationBarComponent -> dashboardComponent "use"
        dashboardComponent -> generalSummaryComponent "use"
        dashboardComponent -> importantAlertsComponent "use"
        dashboardComponent -> quickAccessComponent "use"
        
        //
        //
        // BOUNDED CONTEXT REPORTING
        sideNavigationBarComponent -> reportingComponent "use"
        
        reportingComponent -> replenishmentPlanComponent "use"
        replenishmentPlanComponent -> create-editPlanComponent "use"
        replenishmentPlanComponent -> alertsPlanComponent "use
        create-editPlanComponent -> replenishmentPlanService "use"
        replenishmentPlanService -> apiApplication "Make App request to [JSON/HTTP]"
                
        reportingComponent -> conservationGuideComponent "use"
        conservationGuideComponent -> create-editGuideComponent "use"
        create-editGuideComponent -> conservationGuideService "use"
        conservationGuideService -> apiApplication "Make App request to [JSON/HTTP]"
        
        reportingComponent -> invoiceComponent "use"
        invoiceComponent -> invoiceListComponent "use"
        invoiceListComponent -> invoiceItemComponent "use"
        invoiceComponent -> createInvoiceComponent "use"
        createInvoiceComponent -> invoiceService "use"
        invoiceService -> apiApplication "Make App request to [JSON/HTTP]"
        
        //
        //
        // BOUNDED CONTEXT ORDER
        sideNavigationBarComponent -> orderComponent "use"
        orderComponent -> catalogComponent "use"
        catalogComponent -> create-editCatalogComponent "use"
        catalogComponent -> catalogListComponent "use"
        catalogComponent -> create-editDiscountComponent "use"
        catalogListComponent -> catalogItemComponent "use"
        
        create-editDiscountComponent -> catalogService "use"
        create-editCatalogComponent -> catalogService "use"
        
        sideNavigationBarComponent -> purchaseOrderComponent "use"
        purchaseOrderComponent -> purchaseOrderListComponent "use"
        purchaseOrderListComponent -> purchaseOrderItemComponent "use"
        
        purchaseOrderComponent -> createPurchaseOrderComponent "use"
        createPurchaseOrderComponent -> purchaseOrdenService "use"
        
        purchaseOrdenService -> apiApplication "Make App request to [JSON/HTTP]"
        catalogService -> apiApplication "Make App request to [JSON/HTTP]"
        
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------
        
        //Relaciones de componentes - BACKEND
        //
        //
        //
        Inventory -> singlePageApplication  "Create warehouses, products and manage inventories."
        Order -> singlePageApplication "Create sales orders, purchase order, catalog and invoices."
        Authentication -> singlePageApplication "Sign-in and Sign-up."
        Payment -> singlePageApplication "Makes payment authorization."
        Reporting -> singlePageApplication "Create loss report, conservation guide, care guide and internal referral guide."
        Notification -> singlePageApplication "Send notifications about expiration products or a update of a order."
        
        //Implementación con Shared
        Inventory -> Shared  "Implemets interfaces / extends classes from"
        Order -> Shared "Implemets interfaces / extends classes from"
        Authentication -> Shared "Implemets interfaces / extends classes from"
        Payment -> Shared "Implemets interfaces / extends classes from"
        Reporting -> Shared "Implemets interfaces / extends classes from"
        Notification -> Shared "Implemets interfaces / extends classes from"
        
        //Conexión a base de datos
        Inventory -> DBSQL "Reads from and writes to"
        Order -> DBSQL "Reads from and writes to'
        Authentication -> DBSQL "Reads from and writes to"
        Payment -> DBSQL "Reads from and writes to"
        Reporting -> DBSQL "Reads from and writes to"
        Notification -> DBSQL "Reads from and writes to"
        Shared -> DBSQL "Reads from and writes to"
        
        //Conexión a Servicios Externos
        Authentication -> gmail "Sends e'mail via"
        Payment -> paypal "Process payments via"
        Shared -> cloudinary "Stores images in"
        
    }

    
    configuration {
        scope softwaresystem
    }

    views {
        systemContext stockSip {
            include *
            autolayout
        }
        
        container stockSip {
            include *
            autolayout
        }
        component singlePageApplication {
            include *
            //autolayout
        }
        component apiApplication {
            include *
            //autolayout
        }
        
    styles {
    
        element "App" {
            shape "Box"
        }
        
        element "ExternalSoftwareSystem" {
            shape "Box"
            background "#979797"
            color "#ffffff"
        }
        
        element "Web" {
            shape "WebBrowser"
        }
        
        element "Context" {
            shape "hexagon"
            background "#dcb400"
        }
        
        element "Component" {
            shape "Box"
        }
        
        element "BD" {
            shape "Cylinder" 
            background "#ec0e0e" 
            color "#ffffff" 
        }
        
    }  
        
    theme default
    }

}

