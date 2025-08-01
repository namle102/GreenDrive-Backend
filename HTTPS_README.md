# HTTPS Setup for GreenDrive Backend

This guide explains how to enable HTTPS for the GreenDrive Backend application using Nginx as a reverse proxy with SSL termination.

## Architecture

The setup uses:

- **Nginx**: Reverse proxy with SSL termination
- **Spring Boot**: Application running on port 8080 (internal)
- **SSL Certificates**: Self-signed certificates for development

## Quick Start

### 1. Generate SSL Certificates

```bash
# Generate self-signed SSL certificates
./ssl/generate-ssl.sh
```

This creates:

- `ssl/server.crt` - SSL certificate
- `ssl/server.key` - Private key

### 2. Start the Application

```bash
# Build and start all services
docker-compose up --build
```

### 3. Access the Application

- **HTTPS**: https://localhost
- **HTTP**: http://localhost (redirects to HTTPS)

## Configuration Details

### SSL Certificates

For development, self-signed certificates are used. For production:

1. **Replace certificates**: Replace `ssl/server.crt` and `ssl/server.key` with your production certificates
2. **Update Nginx config**: Modify `nginx/nginx.conf` if needed for your domain

### Nginx Configuration

The Nginx configuration (`nginx/nginx.conf`) includes:

- **SSL termination**: Handles HTTPS connections
- **HTTP to HTTPS redirect**: Automatically redirects HTTP to HTTPS
- **Security headers**: HSTS, X-Frame-Options, etc.
- **Proxy settings**: Forwards requests to Spring Boot application
- **WebSocket support**: For real-time features

### Spring Boot Configuration

The application is configured to work behind a proxy:

- **Forward headers**: Trusts proxy headers
- **Protocol detection**: Detects HTTPS from proxy headers
- **Remote IP**: Gets client IP from proxy headers

## Production Deployment

For production deployment:

1. **Use real certificates**: Replace self-signed certificates with certificates from a trusted CA
2. **Update domain**: Change `localhost` to your actual domain in Nginx config
3. **Environment variables**: Use environment variables for sensitive data
4. **Security**: Consider additional security measures like rate limiting

## Troubleshooting

### Certificate Issues

If you see certificate warnings:

- For development: Accept the self-signed certificate in your browser
- For production: Ensure your certificates are valid and properly configured

### Connection Issues

1. **Check containers**: `docker-compose ps`
2. **View logs**: `docker-compose logs nginx` or `docker-compose logs app`
3. **Test connectivity**: `curl -k https://localhost/actuator/health`

### Port Conflicts

If ports 80 or 443 are already in use:

- Stop other services using these ports
- Or modify the port mappings in `docker-compose.yml`

## Security Features

- **HSTS**: Strict Transport Security headers
- **Modern SSL**: TLS 1.2 and 1.3 only
- **Secure ciphers**: Strong cipher suite configuration
- **Security headers**: X-Frame-Options, X-Content-Type-Options, etc.
- **HTTP to HTTPS redirect**: Automatic redirection

## File Structure

```
├── nginx/
│   └── nginx.conf          # Nginx configuration
├── ssl/
│   ├── generate-ssl.sh     # SSL certificate generation script
│   ├── server.crt          # SSL certificate
│   └── server.key          # Private key
├── docker-compose.yml      # Updated with Nginx service
└── HTTPS_README.md         # This file
```
