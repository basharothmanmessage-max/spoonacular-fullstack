@echo off
set COMPOSE_FILE=docker-compose.yml

echo --- Starting Full-Stack Application Deployment (Windows) ---

REM --- STEP 1: Run Docker Compose Build & Deploy ---
echo 1. Building and starting all services (Frontend & Backend)...
docker compose up --build -d
if errorlevel 1 goto :FAILURE

echo --- Success! Application services are running in the background. ---
echo 
echo Frontend Spoonar (React) should be accessible at: http://localhost:3000
echo Backend Spoonar(Spring Boot) is listening on: http://localhost:8090
echo 

goto :END

:FAILURE
echo ERROR: Docker Compose failed. Please check the logs and YAML file syntax.

:END
pause