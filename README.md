# spoonacular-fullstack (Dockerized fornt-end & backend)

make sure your docker engine is up * running !!

Just run the "start_spoonar_full.bat" :-)

Project Description: Full-Stack Recipe Explorer Gateway

This project delivers a containerized, full-stack web application designed to let users search for recipes and view detailed nutritional information by leveraging the Spoonacular API.

The application is built using a decoupled architecture but deployed via a unified Docker Compose setup, ensuring a streamlined development, build, and deployment process.

🏛️ Key Components and Architecture

The solution comprises two distinct, containerized services that communicate on a shared Docker network:

1. Backend Service: Spring Boot Gateway (recipe-backend-service)
   
The backend is built with Java (Spring Boot) and serves as a secure, high-performance middleware layer.

Middleware Function: The backend is the single point of contact between the React frontend and the external Spoonacular API. It secures the Spoonacular API key, which is never exposed to the client.

RESTful API: It provides RESTful endpoints for:

Recipe Search: Accepts query parameters and optional filters, supporting pagination for efficient result handling.

Recipe Details: Returns comprehensive nutritional information, ingredients, and instructions.

Custom AI Feature: Includes a specialized endpoint for recalculating total calories based on user-excluded ingredients.

Tooling: Uses the Feign Client for declarative, robust communication with the external API and features a Global Exception Handler to translate API errors into clean HTTP responses.

2. Frontend Service: React Application (recipe-frontend-service)
   
The frontend is built with React to provide a modern, responsive, and accessible user experience.

User Interface: Provides an intuitive interface for searching, filtering, and viewing recipe content.

Interactive Nutrition: Allows users to select and exclude ingredients directly on the detail page and see the updated calorie count instantly, utilizing the custom backend recalculation endpoint.

Decoupled Communication: The frontend is configured to communicate exclusively with the backend service name (http://backend:8090) over the internal Docker network, ensuring clean separation of concerns.

📦 Deployment and Automation

The entire application is orchestrated using Docker Compose for a one-command build and deployment solution.

Containerization: Both the Spring Boot backend (using a multi-stage Java build) and the React frontend (using a multi-stage Node/Nginx build) are packaged into small, efficient Docker images.

Automation: The docker-compose.yml file defines the internal network, service dependencies, and external port mappings (8090 for backend, 3000 for frontend).

Single Command Deployment: The entire system can be built and deployed using a single command: docker compose up --build -d.
how to use :

1- clone the repo

2- clcik "start_spoonar_full.bat" on Windows machine or "start_spoonar_full.sh" on Linux machine :

then :
Backend APIs Test :http://localhost:8090/swagger-ui/index.html

Frontend : http://localhost:3000/recipe/661427

<img width="614" height="210" alt="image" src="https://github.com/user-attachments/assets/6eac9bd1-a7f7-432c-bd50-1b280df3fd8e" />




Backend Swagger APIs

<img width="1644" height="926" alt="image" src="https://github.com/user-attachments/assets/f2f0a3a9-c335-4972-8686-1010fd065440" />



Frontend

<img width="1615" height="990" alt="image" src="https://github.com/user-attachments/assets/80ea8fb6-daef-4160-babd-1a2751de6e9f" />

<img width="1494" height="975" alt="image" src="https://github.com/user-attachments/assets/37878ba6-7b2d-4a74-bf46-c9336685ebfd" />


