# Selenium Automation Framework

[![Build Status](https://img.shields.io/travis/rohit-sinha-lab49/WebSeleniumAutomationFramework.svg?style=flat-square)](https://travis-ci.org/rohit-sinha-lab49/WebSeleniumAutomationFramework)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

This is a Selenium Automation Framework built on top of TestNG and integrated with Allure for reporting. This framework is designed to make writing and running automated tests easier and more efficient.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running Tests](#running-tests)
- [Generating Reports](#generating-reports)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Features

- **Selenium WebDriver Integration**: Easily write and run Selenium tests.
- **TestNG Framework**: Leverage the powerful TestNG framework for organizing and executing tests.
- **Allure Reporting**: Generate beautiful and detailed test reports with Allure.
- **Page Object Model**: Follow the Page Object Model (POM) design pattern for better maintainability.
- **Configurable**: Easily configure browser settings, test data, and more through property files.

## Getting Started

These instructions will help you set up and run the project on your local machine.

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher
- ChromeDriver (or other WebDriver binaries as needed)
- Git (optional, for cloning the repository)

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/rohit-sinha-lab49/WebSeleniumAutomationFramework.git
    cd WebSeleniumAutomationFramework.git
    ```

2. **Install dependencies**:
    ```sh
    mvn clean install
    ```

3. **Configure WebDriver binaries**:
    Ensure you have the appropriate WebDriver binaries (e.g., ChromeDriver) available in your system's PATH.

### Running Tests

1. **Run all tests**:
    ```sh
    mvn test
    ```

2. **Run a specific test suite**:
    ```sh
    mvn -Dtest=<TestSuiteName> test
    ```

### Generating Reports

After running the tests, you can generate Allure reports.

1. **Generate Allure report**:
    ```sh
    allure serve target/allure-results
    ```

This command will start a local server and open the Allure report in your default web browser.

## Project Structure
├── pom.xml # Project configuration for Maven
├── src
│ └── test
│ └── java
│ └── com
│ │ └── example
│ │ ├── pages # Page Object Model classes
│ │ └── utility # Utility classes
│ │ └── tests # Test classes
│ └── resources
│ └── testng.xml # TestNG configuration file
└── allure-results # Allure results directory


### Explanation

- **pom.xml**: The Maven configuration file.
- **src**: The source directory.
  - **test**: The test source code directory.
    - **java**: The directory containing Java test files.
      - **com/example/pages**: The package containing Page Object Model (POM) classes.
      - **com/example/utility**: The package containing utility classes.
      - **com/example/tests**: The package containing test classes.
    - **resources**: The resources directory for test configuration files.
      - **testng.xml**: The TestNG configuration file.
- **target**: The output directory for compiled files and generated reports.
  - **allure-results**: The directory where Allure test results are stored.

## Project Files and Directories

Here's a brief overview of important files and directories in the project:

- `pom.xml`: Maven project file containing dependencies and build configurations.
- `src/test/java/com/example/pages`: Contains Page Object Model classes, which represent the web pages.
- `src/test/java/com/example/utility`: Contains utility classes for common functions and helpers.
- `src/test/java/com/example/tests`: Contains the test classes with TestNG test cases.
- `src/test/resources/testng.xml`: Configuration file for TestNG.
- `allure-results`: Directory where Allure stores test results for generating reports.

## Example Usage

To run the tests and generate the Allure report, follow these steps:

1. **Run Tests**:
    ```sh
    mvn test
    ```

2. **Generate Allure Report**:
    ```sh
    allure serve allure-results
    ```

This will start a local server and open the Allure report in your default web browser.

By following this structure, you provide a clear and organized overview of your project's layout, making it easier for others to navigate and understand. Feel free to adjust the details according to your project's specific structure and contents.



## Contributing

We welcome contributions to improve this project. Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a pull request.

Please ensure your code adheres to the existing coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [Selenium](https://www.selenium.dev/)
- [TestNG](https://testng.org/)
- [Allure](http://allure.qatools.ru/)
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)


## Overview

This project demonstrates how to build a Maven project, create a Docker image, and run the application using Docker Compose.

## Prerequisites

- Java 11
- Maven
- Docker
- Docker Compose

## Project Structure

.
├── Dockerfile
├── docker-compose.yaml
├── pom.xml
├── src
│ └── test
|  └── java
│ └── ...
└── README.md


## Build and Run Instructions

healthcheck.sh

First, create the healthcheck.sh script. This script will check if the Selenium hub is ready.

#!/usr/bin/env bash

echo "Checking if hub is ready - $HUB_HOST"

while [ "$(curl -s http://$HUB_HOST:4444/status | jq -r .value.ready)" != "true" ]; do
  sleep 1
done

java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* \
  -DHUB_HOST=$HUB_HOST \
  org.testng.TestNG $MODULE

# Make sure the script is executable:

chmod +x healthcheck.sh

# Update your Dockerfile to copy the healthcheck.sh script and use it before starting your Java application

Dockerfile

The Dockerfile defines the steps to create the Docker image.

FROM openjdk:11-jre-slim
RUN apt-get update && apt-get install -y curl jq
WORKDIR C:/Projects/SeleniumManagerProject
COPY target/selenium-docker.jar selenium-docker.jar
COPY target/selenium-docker-tests.jar selenium-docker-tests.jar
COPY target/libs libs
COPY testng.xml testng.xml
ADD healthcheck.sh healthcheck.sh
ENTRYPOINT sh healthcheck.sh

# docker-compose.yaml

The docker-compose.yaml file defines the services to be run.

version: "3"
services:
  selenium-hub:
    image: selenium/hub:4.16.1-20231219
    container_name: selenium-hub
    ports:
      - "4444:4444"

  chrome:
    image: selenium/node-chrome:4.16.1-20231219
    shm_size: 2gb
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443

  firefox:
    image: selenium/node-firefox:4.16.1-20231219
    shm_size: 2gb
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443

  edge:
    image: selenium/node-edge:4.16.1-20231219
    shm_size: 2gb
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443

  search-module:
    image: rohitsinha025/selenium-docker-again
    depends_on:
      - chrome
      - firefox
      - edge
    environment:
      - HUB_HOST=selenium-hub
      - MODULE=testng.xml
    volumes:
      - ./output:/Projects/SeleniumManagerProject/target/test-output
      

### 1. Build the Maven Project

First, you need to build the Maven project to generate the JAR file.

```sh
mvn clean package -DskipTests

2. Build the Docker Image

After the Maven build is successful, build the Docker image.

docker build -t rohitsinha025/selenium-docker-again -f ./Dockerfile .

3. Run Docker Compose

Once the Docker image is built, use Docker Compose to run the application.

docker-compose up

Accessing the Application

Once the application is running, you can access it via http://localhost:4444/ui# (or any other port you have exposed).

Cleaning Up

To stop the application and remove the Docker containers, run:

docker-compose down

Troubleshooting
Ensure Docker and Docker Compose are installed and running.
Make sure no other application is using the ports defined in docker-compose.yaml.
Check the logs for any errors using docker-compose logs.


License
This project is licensed under the MIT License - see the LICENSE.md file for details.


### Additional Notes

- Replace `your-application.jar` with the actual name of your JAR file.
- Adjust the ports and network settings in `docker-compose.yaml` as needed.
- Ensure all required dependencies are included in your `pom.xml`.

This `README.md` provides a comprehensive guide for users to build and run your project using Maven, Docker, and Docker Compose.


Feel free to modify this template to better fit your project's needs. Add any additional sections or information that might be useful for users and contributors.


//Deploy this on docker
//Master/slave configuration [Multi commit execution]
//BDD conversion
//Send report on Teams using teams plugin


