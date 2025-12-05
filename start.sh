#!/bin/sh
set -e

TASK_IP=$(curl -s $ECS_CONTAINER_METADATA_URI_V4/task | jq -r '.Networks[0].IPv4Addresses[0]')

if [ -z "$TASK_IP" ]; then
  echo "Error: Could not retrieve Task Private IP from ECS metadata endpoint." >&2
  exit 1
fi

echo "Retrieved Task Private IP: $TASK_IP"

java -jar \
  -DEUREKA_INSTANCE_HOSTNAME=$TASK_IP \
  -DDB_URL=${ENV_DB_URL} \
  -DDB_USERNAME=${ENV_DB_USERNAME} \
  -DDB_PASSWORD=${ENV_DB_PASSWORD} \
  -DDB_DDL_AUTO=${ENV_DB_DDL_AUTO} \
  -DJWT_SECRET=${ENV_JWT_SECRET} \
  -DACCESS_TOKEN_EXPIRATION=${ENV_ACCESS_TOKEN_EXPIRATION} \
  -DREFRESH_TOKEN_EXPIRATION=${ENV_REFRESH_TOKEN_EXPIRATION} \
  app.jar