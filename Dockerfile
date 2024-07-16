FROM openjdk:11-jre-slim
RUN apt-get update && apt-get install -y curl jq
WORKDIR C:/Projects/SeleniumManagerProject
COPY target/selenium-docker.jar selenium-docker.jar
COPY target/selenium-docker-tests.jar selenium-docker-tests.jar
COPY target/libs libs
COPY testng.xml testng.xml
ENTRYPOINT java -cp selenium-docker.jar;selenium-docker-tests.jar;libs/* -DHUB_HOST=$HUB_HOST org.testng.TestNG $MODULE
#ADD healthcheck.sh healthcheck.sh
#ENTRYPOINT sh healthcheck.sh