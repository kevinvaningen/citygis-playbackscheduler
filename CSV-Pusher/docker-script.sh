#!/usr/bin/env bash
java -jar target/pusher-1.0-SNAPSHOT-jar-with-dependencies.jar -p . -f Events.csv -t EVENTS
java -jar target/pusher-1.0-SNAPSHOT-jar-with-dependencies.jar -p . -f Monitoring.csv -t MONITORING
