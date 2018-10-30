#!/bin/bash

cd /project
sudo kill -9 $(lsof -i:8080 -t)
rm -rf *.jar
rm -rf  drinkingfountain
git clone https://github.com/wanghaojun/drinkingfountain.git
cd drinkingfountain
mvn package
cp ./target/*.jar /project
cd /project
rm -rf nohup.out 
nohup java -jar *.jar &
echo "success"
