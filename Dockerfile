FROM maven:3.6.3-jdk-11-slim as BUILDER

WORKDIR /app
COPY pom.xml .
COPY src /app/src

RUN mvn clean package -DskipTests=true

# At this point, BUILDER stage should have your .jar or whatever in some path
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*warehouse*.jar warehouse.jar

EXPOSE 8080
CMD [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "warehouse.jar" ]