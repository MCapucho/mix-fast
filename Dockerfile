FROM amazoncorretto:17

RUN cd /home/runner/work/mix-fast
RUN ls

ADD target/mixfast.jar mixfast.jar

EXPOSE 9080

ENTRYPOINT ["java", "-jar", "/mixfast.jar"]