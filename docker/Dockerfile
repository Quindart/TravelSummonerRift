# Build Stage
FROM maven:3.8.7-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy từng pom.xml riêng của từng service
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
