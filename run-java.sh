#!/usr/bin/env bash
# Simple script to launch the JVM with JAVA_OPTS

exec java ${JAVA_OPTS} -jar -Djava.security.egd=file:/dev/./urandom /app.jar