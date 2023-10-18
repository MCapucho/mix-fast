FROM openjdk:17-jdk-slim

CMD ["ls -a"]

COPY ./target/mixfast.jar mixfast.jar

EXPOSE 9080

ENTRYPOINT ["java", "-jar", "/mixfast.jar"]