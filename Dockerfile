FROM openjdk:17-jdk-slim

COPY mixfast.jar mixfast.jar

EXPOSE 9080

ENTRYPOINT ["java", "-jar", "/mixfast.jar"]