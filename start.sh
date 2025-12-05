#!/bin/sh
set -e

echo "[entrypoint] Fetching container metadata..."
ECS_INSTANCE_IP_ADDRESS=$(curl -s "$ECS_CONTAINER_METADATA_URI" | jq -r '.Networks[0].IPv4Addresses[0]')

if [ -z "$ECS_INSTANCE_IP_ADDRESS" ]; then
  echo "[entrypoint] Failed to retrieve ECS instance IP address."
  exit 1
fi

export ECS_INSTANCE_IP_ADDRESS
echo "[entrypoint] ECS_INSTANCE_IP_ADDRESS resolved as: $ECS_INSTANCE_IP_ADDRESS"

java -jar \
  -DDB_URL=${ENV_DB_URL} \
  -DDB_USERNAME=${ENV_DB_USERNAME} \
  -DDB_PASSWORD=${ENV_DB_PASSWORD} \
  -DDB_DDL_AUTO=${ENV_DB_DDL_AUTO} \
  -DJWT_SECRET=${ENV_JWT_SECRET} \
  -DACCESS_TOKEN_EXPIRATION=${ENV_ACCESS_TOKEN_EXPIRATION} \
  -DREFRESH_TOKEN_EXPIRATION=${ENV_REFRESH_TOKEN_EXPIRATION} \
  app.jar