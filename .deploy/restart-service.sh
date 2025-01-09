#!/bin/bash
aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 211125330771.dkr.ecr.eu-central-1.amazonaws.com
docker pull 211125330771.dkr.ecr.eu-central-1.amazonaws.com/lottery:latest
docker run -d \
  --name lottery \
  -p 80:8080 \
  -e MONGO_USER="admin" \
  -e MONGO_PASSWORD="admin" \
  -e MONGO_HOST="172.31.45.66" \
  -e MONGO_PORT="27017" \
  -e MONGO_DB_NAME="lotto-web" \
  -e MONGO_AUTH_SOURCE="admin" \
  -e REDIS_HOST="172.31.45.66" \
  -e REDIS_PORT="63792" \
  -e SECRET_KEY="your-secret-key-here" \
  211125330771.dkr.ecr.eu-central-1.amazonaws.com/lottery:latest