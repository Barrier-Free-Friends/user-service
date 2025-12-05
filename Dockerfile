FROM bellsoft/liberica-openjdk-alpine:21

RUN apk add --no-cache curl jq

# 빌드된 JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# start.sh 복사 및 실행 권한 부여
COPY start.sh /start.sh
RUN chmod +x /start.sh

# sh로 start.sh 실행
ENTRYPOINT ["/bin/sh", "/start.sh"]

EXPOSE 3000
