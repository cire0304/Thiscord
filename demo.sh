#! /bin/bash

cd ./backend/chat
./gradlew build -x test

cd ../Thiscode
./gradlew build -x test

cd ../../frontend
npm install
npm run build

cd ../
sudo docker-compose up --build

