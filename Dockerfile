# Build stage
FROM gradle:jdk21-alpine AS builder

WORKDIR /app

COPY . .

RUN gradle clean build -x test

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 7777

ENTRYPOINT ["java", "-jar", "app.jar"]
# 1-orinda proyektni turi yani maven yoki gradle da qilingani aniqlanadi
# 2-orinda Java ni versiyasi aniqlanadi