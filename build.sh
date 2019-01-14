#!/usr/bin/env bash

JARFILE=`find . -name disney-technical-challenge*.jar`

docker build -f Dockerfile -t chrisalehman/demo --build-arg JAR_FILE=${JARFILE} .
docker run -p 8080:8080 -v /tmp chrisalehman/demo