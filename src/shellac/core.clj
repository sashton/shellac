(ns shellac.core
  (:require [environ.core :refer [env]]
            [clojure.tools.nrepl.server :as repl])
  (:import (java.net ServerSocket)
           (java.io BufferedReader
                    InputStreamReader
                    PrintWriter)))


(def server-port
  (or (env :shellac-port) 5885))

(def nrepl-port
  (or (env :shellac-nrepl-port) 5886))


(defonce server-socket (atom nil))

(defonce server (repl/start-server :port nrepl-port))



(defmulti process
          "[command in out args]

           Processes a tcp request. Dispaches on 'command'.
           Each defmethod instance is essentially a command line program.
           It receives an input stream to read from -- i.e. stdin or output of a pipe,
           an output stream to write to, and a list of command line arguments.

           command: the name of the command to execute
           in: the input stream
           out: the output stream
           args: command line arguments for the command"
          (fn [command in out args] command))


(defmethod process :default [command _ _ _]
  (binding [*out* *err*]
    (println "Unknown command: " command)))


; a simple 'cat' command for demonstration
(defmethod process "cat" [_ in out args]
  (doseq [line (line-seq in)]
    (.println out line)))


(defn start-server
  "Starts the socket server. Requests will be handled by the 'process' multimethods"
  []
  (when (nil? @server-socket)
    (let [ss (ServerSocket. server-port)]
      (reset! server-socket ss)
      (future
        (while true
          (let [client (.accept ss)]
            (future
              (with-open [client client]
                (let [in (BufferedReader. (InputStreamReader. (.getInputStream client)))
                      out (PrintWriter. (.getOutputStream client) true)
                      [command & args] (clojure.string/split (.readLine in) #"\s")]
                  (process command in out args))))))))))


(defn stop-server
  "Stops the server, if it is started"
  []
  (when @server-socket
    (.close @server-socket)
    (reset! server-socket nil)))
