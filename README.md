# clj-kstream-lf-producer

A Clojure application to produce endless text to kafka.

## Docker Hub

* [sojoner/clj-kstream-lf-producer](https://hub.docker.com/r/sojoner/clj-kstream-lf-producer/) 

## Requirements

* [leiningen 2.7.1](https://leiningen.org/)
* [kafka 0.10.0.1](http://kafka.apache.org) 
* [docker 1.12.6](https://www.docker.com/)

## Build Clojure
    
    $lein check

## Build .jar

    $lein uberjar

## Build .container
    
    $cd deploy
    $./containerize.sh

## Usage Leiningen

    $lein run --broker kafka-broker:9092  --topic logs-replay

## Usage java

    $java - jar clj-kstream-lf-producer.jar --broker kafka-broker:9092 --topic logs-replay

## Usage docker

    $docker run -t -i <BUILD-HASH> --broker kafka-broker:9092 --topic logs-replay

## License

Copyright © 2017 Hagen Tönnies

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
