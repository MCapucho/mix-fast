FROM amazoncorretto:17

RUN echo $(ls)

ADD /home/runner/work/mix-fast/mix-fast/target/mixfast.jar mixfast.jar

EXPOSE 9080

ENTRYPOINT ["java", "-jar", "/mixfast.jar"]