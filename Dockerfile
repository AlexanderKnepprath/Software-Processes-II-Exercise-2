#
# DockerFile for cucumber-phonebook: illustrates using cucumber with Java 8
#   Use: 
#       docker build --progress=plain ./
#   in the folder containing this file.
#

FROM ubuntu:21.04

# install maven; it will download the rest
RUN apt-get update; apt-get install -y maven
# if wish to build without maven, would likely want the following:
# RUN apt-get install -y cucumber openjdk-8-jdk junit4 

# build; see pom.xml for dependencies such as Junit 5 and cucumber
COPY . /container

# run maven with options to reduce the amount of logging
RUN cd /container; mvn --batch-mode -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn clean install
