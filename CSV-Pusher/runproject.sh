#!/bin/bash
clear

echo "Running CityGis pusher software!"ch

docker run -tip 8883:1883 -p 9001:9001 toke/mosquitto