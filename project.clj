(defproject clj-kstream-lf-producer "0.1.0-SNAPSHOT"
  :description "A Clojure application to taka a line file and produce .json messages to kafka"
  :url "http://github.com/sojoner/clj-kstream-lf-producer"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/core.async "0.2.374"]
                 ; Kafka
                 [org.apache.kafka/kafka-streams "0.10.0.1"]
                 [org.apache.kafka/connect-runtime "0.10.0.1"]
                 [org.apache.kafka/connect-api "0.10.0.1"]
                 [org.apache.kafka/kafka-clients "0.10.0.1"]
                 ; logging
                 [org.clojure/tools.logging "0.2.6"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :aot :all
  :main clj-kstream-lf-producer.core
  :profiles {:uberjar {:aot :all}}
  ;; As above, but for uberjar.
  :uberjar-name "clj-kstream-lf-producer.jar"
  :jvm-opts ["-Xmx2g" "-server"])
