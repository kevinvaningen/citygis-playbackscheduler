# Citygis-Player
This is a csv-event scheduled playback MQTT-client. It is used for testing purposes. It playbacks a CSV file with (reactive programming) timed events and publishes the events to the Mosquitto message broker. The MQTT broker receives messages in JSON serialised format using QoS=0. 



#Install Mosquitto using Docker
##Get Docker 
- Windows https://docs.docker.com/windows/started/
- OSX https://docs.docker.com/mac/started/
- Linux https://docs.docker.com/linux/started/
    
##Run MQTT container
    docker run -d -p 8883:1883 -p 9001:9001 toke/mosquitto

##Run CSV pusher using the runnable Jar
    #Go to the CSV-Pusher/target directory using commandline
    #Use the following command java -jar pusher-1.0-SNAPSHOT-jar-with-dependencies -f <fullFilePath> -t <FILE-TYPE>
    #Example:
    java -jar pusher-1.0-SNAPSHOT-jar-with-dependencies -f /home/my-user/Positions.csv -t POSITIONS
    #Example with broker config to localhost
    java -jar pusher-1.0-SNAPSHOT-jar-with-dependencies -f /home/my-user/Positions.csv -t POSITIONS -h tcp://127.0.0.1:8883 -c csv -q 0
    #Example with broker config on server with authentication
    java -jar pusher-1.0-SNAPSHOT-jar-with-dependencies -f /home/my-user/Positions.csv -t POSITIONS -h tcp://127.0.0.1:8883 -c csv -q 0 -u mqttuser -p pass

#Watch the message broker messages
Use an MQTT protocol compatible tool (i like Mqtt.Fx on osx).  http://www.hivemq.com/blog/seven-best-mqtt-client-tools
Get your containers IP. For Windows en osx use the following command:
     boot2docker ip
For linux i use ifconfig and see the docker registration. U can also use:
     docker inspect <containerid>
Connect to the container using the IP. The channels correspond with the <FILE-TYPE> setting in your java -jar Run-command. 

#Types of CSV streams 
We currently support these four types:
- EVENTS
- MONITORING
- POSITIONS
- CONNECTIONS

#CSV file formats
The first line is skipped. After this our software expects the following:
###Events
    2015-03-10 07:19:55;14100064;Ignition;1
###Monitoring
    14100071;2015-03-10 07:12:20;2015-03-10 07:13:20;Gps/GpsAccuracyGyroBias;0;0;0
###Positions
    2015-03-10 00:00:02;357566000058106;158126.102542985;380446.027478599;0;31;7;1;Gps
###Connections
    2015-03-10 07:19:55;14100064;Ignition;1

