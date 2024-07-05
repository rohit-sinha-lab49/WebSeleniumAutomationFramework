FROM openjdk:11-jre-slim
WORKDIR C:/Projects/SeleniumManagerProject
COPY C:/Projects/SeleniumManagerProject C:/Projects/SeleniumManagerProject
COPY testng.xml testng.xml
RUN apk add --no-cache maven
RUN mvn dependency:get -Dartifact=org.testng:testng:7.5
ENTRYPOINT ["java", "-cp", "/root/.m2/repository/org/testng/testng/7.7.1/testng-7.7.1.jar:/app", "org.testng.TestNG", "testng.xml"]