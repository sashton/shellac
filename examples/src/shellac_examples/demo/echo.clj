(ns shellac-examples.demo.echo
  (:gen-class)
  (:import (java.io BufferedReader)))

(defn -main
  "This is a simple DEMO version of the Unix 'echo' command.
   This is useful only for comparing the execution time of
   starting up a JVM to do the echo vs using shellac sockets"
  [& args]
  (doseq [line (line-seq (BufferedReader. *in*))]
    (println line)))