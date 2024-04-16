FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/
RUN mkdir simple-rest-api
WORKDIR /simple-rest-api
COPY ./target/simple-rest-api-1.jar .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "simple-rest-api-1.jar" ]