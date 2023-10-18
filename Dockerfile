FROM amazoncorretto:17

RUN ls

ADD target/mixfast.jar mixfast.jar

EXPOSE 9080

ENTRYPOINT ["java", "-jar", "/mixfast.jar"]