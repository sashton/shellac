(ns shellac-examples.demo.cat
  (:gen-class)
  (:import (java.io BufferedReader)))

(defn -main
  "This is a simple DEMO version of the Unix 'cat' command.
   This is useful only for comparing the execution time of
   starting up a JVM to do the cat vs using shellac sockets"
  [& args]
  (doseq [file args]
    (println (slurp file))))