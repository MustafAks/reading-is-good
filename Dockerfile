FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
ARG CONFIG_FOLDER
ADD ${JAR_FILE} reading-is-good.jar
ENTRYPOINT ["java","-jar","reading-is-good.jar"]
EXPOSE 9090
