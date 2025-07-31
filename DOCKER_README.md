# GreenDrive Backend - Docker Setup

This document provides instructions for running the GreenDrive Backend application using Docker.

## Prerequisites

- Docker
- Docker Compose

## Quick Start

1. **Clone the repository** (if not already done):

   ```bash
   git clone <repository-url>
   cd GreenDrive-Backend
   ```

2. **Build and run the application**:

   ```bash
   docker-compose up --build
   ```

   This command will:

   - Build the Spring Boot application
   - Start a MySQL 8.0 database
   - Start the Spring Boot application
   - Set up the necessary networking between containers

3. **Access the application**:
   - Application: http://localhost:8080
   - Health Check: http://localhost:8080/actuator/health
   - MySQL Database: localhost:3306

## Docker Commands

### Build and run

```bash
# Build and start all services
docker-compose up --build

# Run in detached mode
docker-compose up -d --build

# Start only specific services
docker-compose up mysql
docker-compose up app
```

### Stop services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: This will delete database data)
docker-compose down -v
```

### View logs

```bash
# View logs for all services
docker-compose logs

# View logs for specific service
docker-compose logs app
docker-compose logs mysql

# Follow logs in real-time
docker-compose logs -f app
```

### Container management

```bash
# List running containers
docker-compose ps

# Execute commands in running containers
docker-compose exec app sh
docker-compose exec mysql mysql -u root -p

# Restart services
docker-compose restart app
docker-compose restart mysql
```

## Environment Variables

The application uses the following environment variables (configured in docker-compose.yml):

- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_JPA_HIBERNATE_DDL_AUTO`: Hibernate DDL mode
- `JWT_SECRET`: JWT secret key
- `JWT_EXPIRATION`: JWT expiration time

## Database

- **Host**: localhost (or `mysql` from within the Docker network)
- **Port**: 3306
- **Database**: greendrive_db
- **Username**: root
- **Password**: 12345678

The database data is persisted in a Docker volume named `mysql_data`.

## Health Checks

Both the application and database have health checks configured:

- **Application**: Checks `/actuator/health` endpoint
- **Database**: Uses `mysqladmin ping`

## Troubleshooting

### Application won't start

1. Check if MySQL is running: `docker-compose ps`
2. Check application logs: `docker-compose logs app`
3. Ensure MySQL is healthy before starting the app

### Database connection issues

1. Verify MySQL container is running: `docker-compose ps mysql`
2. Check MySQL logs: `docker-compose logs mysql`
3. Ensure the database credentials match in docker-compose.yml

### Port conflicts

If ports 8080 or 3306 are already in use, modify the port mappings in docker-compose.yml:

```yaml
ports:
  - "8081:8080" # Change 8080 to 8081
```

## Development

For development, you can:

1. **Mount source code for live reload**:

   ```yaml
   volumes:
     - ./src:/app/src
   ```

2. **Use different profiles**:

   ```bash
   docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
   ```

3. **Debug mode**:
   ```yaml
   environment:
     JAVA_OPTS: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
   ports:
     - "5005:5005"
   ```

## Production Considerations

For production deployment:

1. **Use environment-specific configuration files**
2. **Set secure passwords and secrets**
3. **Configure proper logging**
4. **Set up monitoring and alerting**
5. **Use external database services**
6. **Configure SSL/TLS**
7. **Set up proper backup strategies**

## Cleanup

To completely remove all Docker resources:

```bash
# Stop and remove containers, networks, and volumes
docker-compose down -v

# Remove images
docker rmi greendrive-backend_app

# Remove volumes
docker volume rm greendrive-backend_mysql_data
```
