# GreenDrive Full-Stack Docker Setup

This document provides instructions for running the complete GreenDrive application (Backend + Frontend) using Docker.

## Prerequisites

- Docker Desktop installed and running
- Docker Compose installed
- At least 6GB of available RAM
- Your frontend project in a separate folder (e.g., `../GreenDrive-Frontend`)

## Project Structure

```
GreenDrive-Backend/          # Current backend folder
├── docker-compose.yml       # Backend only
├── docker-compose.dev.yml   # Backend development
├── docker-compose.fullstack.yml      # Full-stack production
├── docker-compose.fullstack.dev.yml  # Full-stack development
└── frontend-templates/      # Frontend Docker templates

GreenDrive-Frontend/         # Your frontend folder (separate)
├── Dockerfile              # Copy from frontend-templates/
├── Dockerfile.dev          # Copy from frontend-templates/
└── nginx.conf              # Copy from frontend-templates/
```

## Quick Start

### 1. Setup Frontend Docker Files

First, copy the Docker templates to your frontend folder:

```bash
# From the backend directory
cp frontend-templates/Dockerfile ../GreenDrive-Frontend/
cp frontend-templates/Dockerfile.dev ../GreenDrive-Frontend/
cp frontend-templates/nginx.conf ../GreenDrive-Frontend/
```

### 2. Update Frontend Path

Edit the docker-compose files to point to your actual frontend folder:

```yaml
# In docker-compose.fullstack.yml and docker-compose.fullstack.dev.yml
frontend:
  build:
    context: ../GreenDrive-Frontend # Update this path
```

### 3. Run Full-Stack Application

#### Production Environment

```bash
docker-compose -f docker-compose.fullstack.yml up --build
```

#### Development Environment

```bash
docker-compose -f docker-compose.fullstack.dev.yml up --build
```

## Services Overview

### Production Stack

- **Frontend**: http://localhost:80 (via Nginx)
- **Backend API**: http://localhost:8080
- **MySQL Database**: localhost:3306
- **phpMyAdmin**: http://localhost:8081

### Development Stack

- **Frontend**: http://localhost:5173 (Vite dev server)
- **Backend API**: http://localhost:8080
- **MySQL Database**: localhost:3306
- **phpMyAdmin**: http://localhost:8081

## Frontend Configuration

### Environment Variables

Create a `.env` file in your frontend folder:

```env
# Development
VITE_API_URL=http://localhost:8080/api
NODE_ENV=development

# Production
VITE_API_URL=http://localhost/api
NODE_ENV=production
```

### Vite Configuration

Update your `vite.config.js` to work with Docker:

```javascript
import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    host: "0.0.0.0", // Allow external connections
    port: 5173,
    watch: {
      usePolling: true, // For Docker volume mounts
    },
  },
  build: {
    outDir: "dist",
  },
});
```

## API Integration

### CORS Configuration

Your backend is already configured for CORS with the frontend. The configuration in `WebConfig.java` allows:

```java
.allowedOrigins("http://localhost:5173") // Vite dev server
```

For production, you might want to update this to include your production domain.

### API Base URL

In your frontend, use the environment variable for API calls:

```javascript
const API_BASE_URL =
  import.meta.env.VITE_API_URL || "http://localhost:8080/api";

// Example API call
const response = await fetch(`${API_BASE_URL}/vehicles`);
```

## Development Workflow

### Hot Reload

Both frontend and backend support hot reload in development:

- **Frontend**: Changes to source files automatically trigger Vite rebuild
- **Backend**: Spring Boot DevTools automatically restart the application

### Debugging

- **Backend Debug**: Port 5005 is exposed for remote debugging
- **Frontend Debug**: Use browser dev tools or VS Code debugging

### Database Management

Access phpMyAdmin at http://localhost:8081:

- **Username**: root
- **Password**: 12345678

## Useful Commands

### View Logs

```bash
# All services
docker-compose -f docker-compose.fullstack.dev.yml logs

# Specific service
docker-compose -f docker-compose.fullstack.dev.yml logs frontend
docker-compose -f docker-compose.fullstack.dev.yml logs backend
docker-compose -f docker-compose.fullstack.dev.yml logs mysql

# Follow logs
docker-compose -f docker-compose.fullstack.dev.yml logs -f frontend
```

### Access Containers

```bash
# Frontend container
docker exec -it greendrive-frontend-fullstack-dev /bin/sh

# Backend container
docker exec -it greendrive-backend-fullstack-dev /bin/sh

# MySQL container
docker exec -it greendrive-mysql-fullstack-dev mysql -u root -p
```

### Rebuild Services

```bash
# Rebuild specific service
docker-compose -f docker-compose.fullstack.dev.yml build frontend
docker-compose -f docker-compose.fullstack.dev.yml build backend

# Rebuild and restart
docker-compose -f docker-compose.fullstack.dev.yml up --build frontend
```

### Clean Up

```bash
# Stop all services
docker-compose -f docker-compose.fullstack.dev.yml down

# Remove volumes (WARNING: This will delete database data)
docker-compose -f docker-compose.fullstack.dev.yml down -v

# Remove all containers and images
docker system prune -a
```

## Production Deployment

### 1. Build Production Images

```bash
docker-compose -f docker-compose.fullstack.yml build
```

### 2. Environment Variables

Create a `.env` file for production:

```env
# Database
MYSQL_ROOT_PASSWORD=your_secure_password
MYSQL_DATABASE=greendrive_db
MYSQL_USER=greendrive_user
MYSQL_PASSWORD=your_secure_password

# Backend
JWT_SECRET=your_very_secure_jwt_secret
JWT_EXPIRATION=3600000

# Frontend
VITE_API_URL=https://yourdomain.com/api
```

### 3. SSL Configuration

For HTTPS in production:

1. Add SSL certificates to `nginx/ssl/`
2. Uncomment HTTPS server block in `nginx/nginx.conf`
3. Update domain names in configuration

### 4. Deploy

```bash
docker-compose -f docker-compose.fullstack.yml up -d
```

## Troubleshooting

### Common Issues

1. **Frontend not connecting to backend:**

   ```bash
   # Check if backend is running
   docker-compose -f docker-compose.fullstack.dev.yml ps backend

   # Check backend logs
   docker-compose -f docker-compose.fullstack.dev.yml logs backend
   ```

2. **CORS errors:**

   - Verify CORS configuration in `WebConfig.java`
   - Check if frontend URL matches allowed origins
   - Ensure API calls use correct base URL

3. **Database connection issues:**

   ```bash
   # Check MySQL status
   docker-compose -f docker-compose.fullstack.dev.yml ps mysql

   # Check MySQL logs
   docker-compose -f docker-compose.fullstack.dev.yml logs mysql
   ```

4. **Port conflicts:**

   ```bash
   # Check what's using the ports
   lsof -i :5173
   lsof -i :8080
   lsof -i :3306
   ```

5. **Volume mount issues:**

   ```bash
   # Check volume permissions
   ls -la ../GreenDrive-Frontend/

   # Fix permissions if needed
   sudo chown -R $USER:$USER ../GreenDrive-Frontend/
   ```

### Performance Optimization

1. **Increase memory limits:**

   ```yaml
   # Add to docker-compose.fullstack.dev.yml
   deploy:
     resources:
       limits:
         memory: 2G
   ```

2. **Use production database settings:**

   ```yaml
   SPRING_JPA_HIBERNATE_DDL_AUTO: validate
   ```

3. **Enable connection pooling:**
   ```yaml
   SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE: 10
   ```

## Security Considerations

⚠️ **Important**: For production deployment:

1. Change all default passwords
2. Use environment variables for sensitive data
3. Enable SSL/TLS
4. Configure proper firewall rules
5. Use secrets management
6. Regularly update Docker images
7. Implement proper logging and monitoring

## Support

If you encounter issues:

1. Check the logs: `docker-compose logs [service-name]`
2. Verify network connectivity between containers
3. Ensure all required files are in the correct locations
4. Check Docker and Docker Compose versions are up to date
