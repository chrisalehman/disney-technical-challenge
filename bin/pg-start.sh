#!/usr/bin/env bash

docker run --name postgres -e POSTGRES_PASSWORD=postgres -d --rm -p 5432:5432 postgres