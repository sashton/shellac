(ns shellac-examples.core
    (:gen-class)
    (:require [shellac.core :as shellac]))


; dupe is similar to echo, but duplicates the input lines
; according to the optional command line arg
(defmethod shellac/process "dupe" [_ in out [dupe-count]]
           (let [dupe-count (Integer. (str (or dupe-count "1")))]
                (doseq [line (line-seq in)
                        _ (range dupe-count)]
                       (.println out line))))


(defmethod shellac/process "reverse" [_ in out [dupe-count]]
  (doseq [line (line-seq in)]
    (.println out (clojure.string/reverse line))))


(defn -main
  "Start the shellac server"
  [& args]
  (shellac/start-server))
