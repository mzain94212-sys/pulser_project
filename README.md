# Pulsar Project

## Overview

This project is a real-time event-driven data pipeline built using Spring Boot, Apache Pulsar, MySQL, and an external REST API.

It demonstrates a producer-consumer architecture.


## Architecture

External API → Producer Service → Apache Pulsar → Consumer Service → MySQL Database

## Tech Stack

- Java 17
- Spring Boot
- MySQL
- Apache Pulsar

## External API Used

https://jsonplaceholder.typicode.com/users


## Producer Service
Fetches data from API and sends it to Pulsar.

Key components:
- ApiService.java → calls API
- DataScheduler.java → runs every 30 seconds
- PulsarProducer.java → sends messages to Pulsar

Topic:
persistent://public/default/data-topic


## Consumer Service
Consumes messages from Pulsar and stores them in MySQL.

Key components:
- PulsarConsumer.java → listens to topic
- DbService.java → insert/update logic
- UserEntity.java → database mapping
- UserRepo.java → JPA repository


## Database Table

users:
- id
- name
- email
- username
- phone
- website

## Flow

1. Producer fetches API data every 30 seconds
2. Sends data to Pulsar topic
3. Consumer listens to topic
4. Processes message
5. Stores in MySQL


## How to Run

### Start Pulsar
bin/pulsar standalone

### Start MySQL
sudo service mysql start

### Run Consumer
mvn clean package -DskipTests
java -jar target/consumer.jar

### Run Producer
mvn clean package -DskipTests
java -jar target/producer.jar


## Concepts Used

- Producer-consumer pattern
- Messaging system (Pulsar)
- REST API integration
- Scheduling


