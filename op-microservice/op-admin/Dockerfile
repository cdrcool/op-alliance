#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM apache/skywalking-base:8.6.0-es7
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV SW_AGENT_COLLECTOR_BACKEND_SERVICES="host.docker.internal:11800" \
    SW_AGENT_NAME="op-admin"


CMD java -javaagent:/skywalking/agent/skywalking-agent.jar \
         -jar app.jar