#!/bin/bash
mv ../target/clj-kstream-lf-producer.jar .
docker build --tag "sojoner/clj-kstream-lf-producer:0.1.0" .

#docker tag <HASH> sojoner/clj-kstream-lf-producer:0.1.0
#docker login
#docker push sojoner/clj-kstream-lf-producer
