# Basic Bank

A RESTful API built with Spring Boot for basic bank features.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Technologies](#technologies)
- [Contributing](#contributing)
- [Contact](#contact)

## Features

- Create a new bank account for a customer, with an initial deposit amount. A
  single customer may have multiple bank accounts.
- Transfer amounts between any two accounts, including those owned by
  different customers.
- Retrieve balance for a given account.
- Retrieve transfer history for a given account.

## Prerequisites

- Java JDK 17+
- Gradle
- Git

## Installation
```bash
git clone https://github.com/AlexanderKazakov1/basic-bank
cd {your directory}/basic-bank
./gradlew build
./gradlew bootRun
```

## API Endpoints

    POST /api/v1/accounts - Create a new bank account for a customer
    GET /api/v1/accounts/{accountId}/balance - Retrieve balance for a given account
    GET /api/v1/accounts/{accountId}/transfers - Retrieve transfer history for a given account
    POST /api/v1/accounts/transfer - Transfer amounts between any two accounts

## Technologies

    Spring Boot 3.0.2
    H2 Database 2.2.220
    JUnit 5.9.2
    Swagger UI 2.2.0

## Contributing

    Fork the Project
    Create your Feature Branch (git checkout -b feature/AmazingFeature)
    Commit your Changes (git commit -m 'Add some AmazingFeature')
    Push to the Branch (git push origin feature/AmazingFeature)
    Open a Pull Request

## Contact

    Alexander Kazakov - alexanderkazakov.p@gmail.com
    Project Link - https://github.com/AlexanderKazakov1/basic-bank
