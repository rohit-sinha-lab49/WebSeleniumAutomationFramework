FROM openjdk:11-jre-slim
WORKDIR C:/Projects/SeleniumManagerProject
COPY target/selenium-docker.jar selenium-docker.jar
COPY target/selenium-docker-tests.jar selenium-docker-tests.jar
COPY target/libs libs
COPY testng.xml testng.xml
ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* org.testng.TestNG testng.xml