workspace "FreshGuard ColdTrack Platform" "Cold-chain monitoring backend architecture" {
    model {
        logisticsUser = person "Logistics Staff" "Creates and supervises shipments."
        driver = person "Driver" "Monitors assigned shipments and alerts."
        qualitySupervisor = person "Quality Supervisor" "Reviews conditions, incidents, and reports."

        coldTrack = softwareSystem "ColdTrack" {
            webApp = container "ColdTrack Front" "Angular 21 web application" "TypeScript, Angular"
            api = container "ColdTrack Platform API" "RESTful API and WebSocket telemetry gateway" "Java 26, Spring Boot 4"
            database = container "ColdTrack Database" "Persists operational and identity data" "MySQL 8"
        }
        sensors = softwareSystem "IoT Sensors" "Send temperature and humidity readings."
        notificationProvider = softwareSystem "Notification Provider" "Future external e-mail notification service."

        logisticsUser -> coldTrack.webApp "Uses"
        driver -> coldTrack.webApp "Uses"
        qualitySupervisor -> coldTrack.webApp "Uses"
        coldTrack.webApp -> coldTrack.api "HTTPS/JSON and WebSocket"
        sensors -> coldTrack.api "Posts telemetry"
        coldTrack.api -> coldTrack.database "Reads and writes" "JPA/JDBC"
        coldTrack.api -> notificationProvider "Sends notifications" "HTTPS"
    }
    views {
        systemContext coldTrack "SystemContext" { include *; autoLayout lr }
        container coldTrack "Containers" { include *; autoLayout lr }
        theme default
    }
}
