# ---- Build Stage ----
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ---- Runtime Stage ----
FROM eclipse-temurin:17-jdk

# Install Tesseract OCR and required language data
RUN apt-get update && apt-get install -y \
    tesseract-ocr \
    tesseract-ocr-eng \
    && rm -rf /var/lib/apt/lists/*

# Set Tesseract data path (optional, for some setups)
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata/

# App setup
WORKDIR /app
COPY --from=build /app/target/ENotes-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]



# # ---- Build Stage ----
# FROM maven:3.9.4-eclipse-temurin-17 AS build
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests
#
# # ---- Runtime Stage ----
# FROM eclipse-temurin:17-jdk
# WORKDIR /app
# COPY --from=build /app/target/ENotes-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]
#
