# Kafka CRUD Application with Spring Boot 4.0.4

A complete CRUD (Create, Read, Update, Delete) REST API using **Spring Boot 4.0.4**, **Apache Kafka 4.2.0**, and **MySQL**.  
When you create, update, or delete a product, a Kafka event is automatically published and consumed asynchronously.

---

## 📋 What You Need Before Starting

- **Java 21** or higher – [Download](https://adoptium.net/)
- **Docker Desktop** – [Download](https://www.docker.com/products/docker-desktop/)
- **Maven** (optional – the project includes a Maven wrapper)
- **Postman** or `curl` (for testing)

---

## 🚀 Quick Setup

### 1. Clone the project
```bash
git clone <your-repo-url>
cd kafka-crud-project


2. Start MySQL, Kafka, and Zookeeper with Docker
Make sure Docker is running, then:

docker-compose up -d
This starts:

MySQL on port 3306

Kafka on port 9092

Zookeeper on port 2181

Kafka UI on port 8081 (optional, for monitoring)

3. Configure the database (only once)
The database kafka_crud_db is created automatically, but if you get errors, run:


docker exec -it kafka-crud-mysql mysql -u root -ppassword -e "CREATE DATABASE IF NOT EXISTS kafka_crud_db;"
4. Run the Spring Boot application
Option A – Using Maven wrapper (Linux/Mac):


./mvnw spring-boot:run
Option B – Using Maven (Windows):


mvnw.cmd spring-boot:run
Option C – From your IDE
Open the project, find the main class KafkaCrudDemoApplication, and run it.


Test the API
Open a new terminal and run these curl commands.

➕ Create a product

curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Wireless Mouse","description":"Ergonomic mouse","price":29.99,"quantity":150}'
📋 Get all products

curl -X GET http://localhost:8080/api/products
🔍 Get one product (replace 1 with real id)

curl -X GET http://localhost:8080/api/products/1
✏️ Update a product

curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Gaming Mouse","description":"RGB gaming mouse","price":49.99,"quantity":80}'
❌ Delete a product

curl -X DELETE http://localhost:8080/api/products/1
🔎 Search by name

curl -X GET "http://localhost:8080/api/products/search?name=mouse"
💰 Filter by price range

curl -X GET "http://localhost:8080/api/products/price-range?minPrice=20&maxPrice=50"
