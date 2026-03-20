# Imagen base
FROM eclipse-temurin:21-jdk-alpine

# Carpeta dentro del contenedor
WORKDIR /app

# Copiar el jar
COPY target/app-backend-0.0.1-SNAPSHOT.jar app.jar

# Puerto
EXPOSE 8080

# Ejecutar app
ENTRYPOINT ["java","-jar","app.jar"]