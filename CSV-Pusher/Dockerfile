FROM java:8

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

ADD src /code/src
RUN ["mvn", "-Dmaven.test.skip=true", "package"]


ADD Events.csv /code/Events.csv
ADD Monitoring.csv /code/Monitoring.csv

#THIS IS NOT WORKING
ADD ./docker-script.sh /code/docker-script.sh
RUN ["chmod", "+x", "/code/docker-script.sh"]

ENTRYPOINT ["/code/docker-script.sh", "-bash"]