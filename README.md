# Shop Items Backend (Spring)

## Project Description
This is the backend service for managing shop items. It is built with Spring Boot and uses PostgreSQL as the database.

## Requirements
- Docker
- A running PostgreSQL database

## Setup and Deployment

### 1. Clone the Repository
```bash
git clone https://github.com/mehdijk/shop-items-backend-spring
cd shop-items-backend-spring
```

### 2. Build the Docker Image
```bash
docker build -t shop-backend .
```

### 3. Stop and Remove Existing Container (if running)
```bash
docker stop shop_backend
docker rm shop_backend
```

### 4. Run the Backend Docker Container
Replace environment variables with your own database credentials.
```bash
docker run -d \
  --name shop_backend \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<DB_HOST>:5432/<DB_NAME> \
  -e SPRING_DATASOURCE_USERNAME=<DB_USERNAME> \
  -e SPRING_DATASOURCE_PASSWORD=<DB_PASSWORD> \
  -e LLM_API_KEY=<LLL_API_KEY> \
  shop-backend
```

### 5. Access API
- Base URL: http://<YOUR_SERVER_IP>:8080
- Example Endpoint: http://<YOUR_SERVER_IP>:8080/api/items

## Notes
- Replace <LLL_API_KEY>, <DB_HOST>, <DB_NAME>, <DB_USERNAME>, and <DB_PASSWORD> with your database details.
- Ensure the database is running and accessible from the backend container.