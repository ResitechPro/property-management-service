FROM maven:3.9.5-amazoncorretto-17 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests -U

FROM openjdk:17
COPY --from=build /app/target/property-management-service.jar property-management-service
ENTRYPOINT ["java", "-jar","property-management-service"]