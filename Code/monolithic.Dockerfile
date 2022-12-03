FROM maven:3.8.5-jdk-8-slim AS data_service
WORKDIR /app
COPY data_service .
RUN mvn --quiet -Dmaven.test.skip=true clean package -P Docker

FROM maven:3.8.5-jdk-8-slim AS main_entry
WORKDIR /app
COPY main_entry .
RUN mvn --quiet -Dmaven.test.skip=true clean package -P Docker

FROM openjdk:8
COPY --from=data_service /app/target/*.jar /data-service.jar
COPY --from=main_entry /app/target/*.jar /main-entry.jar

COPY start.sh /start.sh

EXPOSE 9085
EXPOSE 9080

ENTRYPOINT [ "bash", "-c" ]
CMD [ "/start.sh" ]
