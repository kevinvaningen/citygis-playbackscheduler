# Citygis-Player
This is a csv to Mosquitto player. It it used for testing purposes. It playbacks a csv file using a hashtable and currenttime and publishes the events to the Mosquitto message broker. 

#Install Mosquitto broker - dev environment
We are planning to use a Dockerised version for dev purposes in the near future. But for now this is the way to go.
    brew install mosquitto
    /usr/local/sbin/mosquitto -c /usr/local/etc/mosquitto/mosquitto.conf;

#Mosquitto in docker
    https://github.com/toke/docker-mosquitto
