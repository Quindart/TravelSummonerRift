# ========================== BUILD STAGE ==========================
FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy POM của từng service trước để cache dependencies
COPY BookingService/pom.xml BookingService/
COPY UserService/pom.xml UserService/
COPY GatewayService/pom.xml GatewayService/
COPY NotificationService/pom.xml NotificationService/
COPY DiscoveryService/pom.xml DiscoveryService/

# Download dependencies trước để tối ưu cache
RUN mvn -f BookingService/pom.xml dependency:go-offline
RUN mvn -f UserService/pom.xml dependency:go-offline
RUN mvn -f GatewayService/pom.xml dependency:go-offline
RUN mvn -f NotificationService/pom.xml dependency:go-offline
RUN mvn -f DiscoveryService/pom.xml dependency:go-offline

# Copy toàn bộ source code
COPY BookingService BookingService/
COPY UserService UserService/
COPY GatewayService GatewayService/
COPY NotificationService NotificationService/
COPY DiscoveryService DiscoveryService/

# Build từng service
RUN mvn -f BookingService/pom.xml clean package -DskipTests
RUN mvn -f UserService/pom.xml clean package -DskipTests
RUN mvn -f GatewayService/pom.xml clean package -DskipTests
RUN mvn -f NotificationService/pom.xml clean package -DskipTests
RUN mvn -f DiscoveryService/pom.xml clean package -DskipTests

# ========================== RUNTIME STAGES ==========================
# Discovery Service
FROM eclipse-temurin:17-jre AS discovery-service
WORKDIR /app
COPY --from=builder /app/DiscoveryService/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]

# User Service
FROM eclipse-temurin:17-jre AS user-service
WORKDIR /app
COPY --from=builder /app/UserService/target/*.jar app.jar
EXPOSE 5723
ENTRYPOINT ["java", "-jar", "app.jar"]

# Gateway Service
FROM eclipse-temurin:17-jre AS gateway-service
WORKDIR /app
COPY --from=builder /app/GatewayService/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Booking Service
FROM eclipse-temurin:17-jre AS booking-service
WORKDIR /app
COPY --from=builder /app/BookingService/target/*.jar app.jar
EXPOSE 1902
ENTRYPOINT ["java", "-jar", "app.jar"]

# Notification Service
FROM eclipse-temurin:17-jre AS notification-service
WORKDIR /app
COPY --from=builder /app/NotificationService/target/*.jar app.jar
EXPOSE 2005
ENTRYPOINT ["java", "-jar", "app.jar"]
