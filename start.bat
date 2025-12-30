@echo off
echo Starting PostgreSQL with Docker...
docker-compose up -d

echo Waiting for PostgreSQL to start...
timeout /t 15

echo Starting Spring Boot application with UTC timezone...
set MAVEN_OPTS=-Duser.timezone=UTC
mvn spring-boot:run