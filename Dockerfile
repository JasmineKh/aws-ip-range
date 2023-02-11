FROM maven:latest AS maven
LABEL MAINTAINER="yasaman.kh88@gmail.com"
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM openjdk:17-alpine3.14
ARG JAR_FILE=aws-ip-range-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY --from=maven /app/target/${JAR_FILE} /opt/app/
ENTRYPOINT ["java","-jar","aws-ip-range-0.0.1-SNAPSHOT.jar"]