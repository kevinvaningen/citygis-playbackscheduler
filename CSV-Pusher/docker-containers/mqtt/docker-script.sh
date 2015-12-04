#!/usr/bin/env bash
java -jar target/pusher-1.0-SNAPSHOT-jar-with-dependencies.jar -f Events.csv -t EVENTS
java -jar target/pusher-1.0-SNAPSHOT-jar-with-dependencies.jar -f Monitoring.csv -t MONITORING
