FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/
RUN mkdir stock-service
WORKDIR stock-service
COPY ./target/stock-service-1.0.jar .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "stock-service-1.0.jar" ]d