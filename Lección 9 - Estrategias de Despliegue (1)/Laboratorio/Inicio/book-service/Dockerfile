FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/
RUN mkdir book-service
WORKDIR /book-service
COPY ./target/book-service-1.0.jar .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "book-service-1.0.jar" ]d