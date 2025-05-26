#!/bin/bash
mkdir -p target workdir
docker run --rm --name sic-web --network redaura -e SPRING_PROFILES_ACTIVE=dev -v $HOME/.m2:/var/maven/.m2 -p 9080:9080 \
 -e MAVEN_CONFIG=/var/maven/.m2 -v "$PWD/target:/usr/src/mymaven/target"  -w /usr/src/mymaven -v "$(pwd)":/usr/src/mymaven  \
  --hostname sic-web maven:3-jdk-11 mvn -Duser.home=/var/maven verify
cp sic-web/target/sic-web.war ../../microserviciosSicAURA/Docker-imagenes-dev/wildfly

