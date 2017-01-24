(ns clj-kstream-lf-producer.core
  (:use [clojure.tools.logging :only (info debug error warn)])
  (:require
    [clojure.tools.cli :as cli]
    [clj-kstream-lf-producer.cli :as cli-def]
    [clj-kstream-lf-producer.to-kafka :as to-kafka]
    [clojure.tools.logging :as log]
    [clojure.core.async :refer [>! <!! close! chan go-loop]])
  (:gen-class)
  (:import (java.security MessageDigest)))

(def app-state (atom {}))

(defn listen-for-shutdown []
  (.addShutdownHook
    (Runtime/getRuntime)
    (Thread. #(do
                (log/debug "Got shutdown signal stopping ...")
                (close! (:producer_sentinal @app-state))
                (close! (:file_sentinal @app-state))))))

(defn start-strange-loop []
  (while true
    (and
      (<!! (:producer_sentinal @app-state))
      (<!! (:file_sentinal @app-state)))))

(defn- md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(defn start-reading-file [app-state]
  (go-loop []
   (with-open [rdr (clojure.java.io/reader (:some-text app-state))]
     (info "Read file line by line")
       (doseq [line (line-seq rdr)]
         (>! (:input app-state)
             {:key_id (md5 line)
              :value {:msg line}})))
   (recur)))

(defn bootstrap []
  (swap! app-state assoc
         :input (chan 100)
         :kafka_p_rsp (chan)
         :producer (to-kafka/create-producer @app-state)
         :some-text "/some-text.txt")
  (swap! app-state assoc :producer_sentinal (to-kafka/start-producing @app-state))
  (swap! app-state assoc :file_sentinal (start-reading-file @app-state)))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (cli/parse-opts args cli-def/cli-options)]
    (cond
      (:help options) (cli-def/exit 0 (cli-def/usage summary))
      (not= (count (keys options)) 2) (cli-def/exit 1 (cli-def/usage summary))
      errors (cli-def/exit 1 (cli-def/error-msg errors)))
    (info "Swap State")
    (swap! app-state assoc
           :broker (:broker options)
           :topic (:topic options)
           :file (:file options)
           :producer_sentinal (chan))
    (info "Boostrap")
    (bootstrap)
    (listen-for-shutdown)
    (info "Start Strange loop")
    (start-strange-loop)))

