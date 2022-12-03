#!/usr/bin/env bash

java -jar /data-service.jar &
java -jar /main-entry.jar &

sleep infinity
