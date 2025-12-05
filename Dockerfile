FROM bellsoft/liberica-openjdk-alpine:21

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# start.sh 복사 및 실행 권한 부여
COPY start.sh /start.sh
RUN chmod +x /start.sh

# Bash로 start.sh 실행
ENTRYPOINT ["/bin/bash", "/start.sh"]

EXPOSE 3000
