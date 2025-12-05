FROM bellsoft/liberica-openjdk-alpine:21

# 빌드된 JAR 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# start.sh 복사
COPY start.sh /start.sh
RUN chmod +x /start.sh

# 컨테이너 시작 시 start.sh 실행
ENTRYPOINT ["/start.sh"]

EXPOSE 3000
