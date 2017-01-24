(ns clj-kstream-lf-producer.cli
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-b" "--broker comma seperated" "The kafka brokers hosts"
    :validate [#(string? %1) "Must be a sth like HOST:PORT,HOST:PORT"]]
   ["-t" "--topic the topic name" "The input topic name"
    :validate [#(string? %1) "Must be a sth like NAME"]]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn usage [options-summary]
  (->> ["This is my program. There are many like it, but this one is mine."
        ""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary]
       (clojure.string/join \newline)))

(defn exit [status _]
  (System/exit status))