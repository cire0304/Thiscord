#! /bin/bash

cd ./backend/Thiscode
./gradlew build -x test

cd ../../frontend
npm install
npm run build

cd ../
sudo docker-compose up --build

