#!/usr/bin/env bash

container=`docker ps -a | grep postgres | awk '{print $1}'`

if [ ! -z "${container}" ]; then
  docker stop ${container}
  docker rm ${container}
fi