#!/usr/bin/env bash

docker build -f Dockerfile.db -t customized-postgres .
docker run --name customized-postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 customized-postgres
