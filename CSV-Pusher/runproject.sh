#!/bin/bash
clear

echo "Running CityGis pusher software!"ch

docker run -ti -p 8883:1883 -p 9001:9001 toke/mosquitto