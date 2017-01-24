(ns clj-kstream-lf-producer.to-kafka
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.core.async :refer [<! close! chan go-loop]])
  (:import (java.util Properties)
           (org.apache.kafka.clients.producer KafkaProducer ProducerRecord Callback RecordMetadata))
  (:gen-class))

(defn- get-producer-props [conf]
  (doto (new Properties)
    (.put "bootstrap.servers" (:broker conf))
    (.put "key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    (.put "value.serializer", "org.apache.kafka.common.serialization.StringSerializer")))

(defn create-producer [app-state]
  (KafkaProducer. (get-producer-props app-state)))

(defn start-producing [app-state]
  (go-loop []
           (let [raw-data (<! (:input app-state))]
             (let [producer_record (ProducerRecord.
                                     (:topic app-state)
                                     (:key_id raw-data)
                                     (json/write-str (:value raw-data)))]
               (.send (:producer app-state)
                      producer_record
                      (reify Callback
                        (^void onCompletion [this ^RecordMetadata var1, ^Exception var2]
                          (when var2
                            (log/error "Error" var2))
                          (when var1
                            (log/info "Info" var1))))))

             (recur))))