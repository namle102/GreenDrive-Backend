# GreenDrive Backend - Docker Setup

This document provides instructions for running the GreenDrive Backend application using Docker.

## Prerequisites

- Docker Desktop installed and running
- Docker Compose installed
- At least 4GB of available RAM

## Quick Start

### Production Environment

1. **Build and run the complete stack:**

   ```bash
   docker-compose up --build
   ```

2. **Run in background:**

   ```bash
   docker-compose up -d --build
   ```

3. **Stop the services:**
   ```bash
   docker-compose down
   ```

### Development Environment

1. **Build and run with development features:**

   ```bash
   docker-compose -f docker-compose.dev.yml up --build
   ```

2. **Run in background:**

   ```bash
   docker-compose -f docker-compose.dev.yml up -d --build
   ```

3. **Stop the services:**
   ```bash
   docker-compose -f docker-compose.dev.yml down
   ```

## Services

### Production Stack

- **Spring Boot App**: http://localhost:8080
- **MySQL Database**: localhost:3306

### Development Stack

- **Spring Boot App**: http://localhost:8080
- **MySQL Database**: localhost:3306
- **phpMyAdmin**: http://localhost:8081

## Database Access

### Production

- **Host**: localhost
- **Port**: 3306
- **Database**: greendrive_db
- **Username**: root
- **Password**: 12345678

### Development

- **Host**: localhost
- **Port**: 3306
- **Database**: greendrive_db
- **Username**: root
- **Password**: 12345678
- **phpMyAdmin**: http://localhost:8081 (root/12345678)

## Development Features

The development environment includes:

- **Hot Reload**: Code changes are automatically detected and the application restarts
- **Debug Port**: Available on port 5005 for remote debugging
- **SQL Logging**: SQL queries are logged to the console
- **phpMyAdmin**: Web-based database management interface

## Useful Commands

### View logs

```bash
# All services
docker-compose logs

# Specific service
docker-compose logs app
docker-compose logs mysql

# Follow logs
docker-compose logs -f app
```

### Access containers

```bash
# Access Spring Boot app container
docker exec -it greendrive-backend /bin/sh

# Access MySQL container
docker exec -it greendrive-mysql mysql -u root -p
```

### Database operations

```bash
# Backup database
docker exec greendrive-mysql mysqldump -u root -p12345678 greendrive_db > backup.sql

# Restore database
docker exec -i greendrive-mysql mysql -u root -p12345678 greendrive_db < backup.sql
```

### Clean up

```bash
# Remove containers and networks
docker-compose down

# Remove containers, networks, and volumes
docker-compose down -v

# Remove all unused containers, networks, and images
docker system prune -a
```

## Environment Variables

You can customize the application by setting environment variables:

```bash
# Database configuration
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/greendrive_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=12345678

# JPA configuration
SPRING_JPA_HIBERNATE_DDL_AUTO=create-drop
SPRING_JPA_PROPERTIES_HIBERATE_DIALECT=org.hibernate.dialect.MySQLDialect

# JWT configuration
JWT_SECRET=your-secret-key
JWT_EXPIRATION=3600000
```

## Troubleshooting

### Common Issues

1. **Port already in use:**

   ```bash
   # Check what's using the port
   lsof -i :8080

   # Kill the process or change the port in docker-compose.yml
   ```

2. **Database connection issues:**

   ```bash
   # Check if MySQL is running
   docker-compose ps mysql

   # Check MySQL logs
   docker-compose logs mysql
   ```

3. **Application not starting:**

   ```bash
   # Check application logs
   docker-compose logs app

   # Check if database is ready
   docker-compose logs mysql
   ```

4. **Permission issues:**
   ```bash
   # Fix file permissions
   sudo chown -R $USER:$USER .
   ```

### Health Checks

The application includes health checks that can be monitored:

```bash
# Check container health
docker ps

# Check health endpoint
curl http://localhost:8080/actuator/health
```

## Security Notes

⚠️ **Important**: The default configuration uses simple passwords and is intended for development only. For production:

1. Change all default passwords
2. Use environment variables for sensitive data
3. Enable SSL/TLS
4. Configure proper firewall rules
5. Use secrets management for sensitive configuration

## Performance Optimization

For better performance in production:

1. **Increase JVM memory:**

   ```bash
   # Add to docker-compose.yml environment section
   JAVA_OPTS: "-Xmx2g -Xms1g"
   ```

2. **Use production database settings:**

   ```bash
   SPRING_JPA_HIBERNATE_DDL_AUTO: validate
   ```

3. **Enable connection pooling:**
   ```bash
   SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE: 10
   ```
