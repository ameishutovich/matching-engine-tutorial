#!/bin/bash
echo "Starting java backend"
mvn clean install
java -jar target/matching-engine-tutorial-1.0-SNAPSHOT-jar-with-dependencies.jar &

echo "Starting frontend"
cd src/ui
yarn install
yarn start