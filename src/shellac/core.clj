(ns shellac.core
  (:require [environ.core :refer [env]])
  (:import (java.net ServerSocket)
           (java.io BufferedReader
                    InputStreamReader
                    PrintWriter)))


(def server-port
  (or (env :shellac-port) 5885))


(defonce server-socket (atom nil))



(defn start-server
  "Starts the socket server. Requests will be handled by the 'process' multimethods"
  [commands]
  (when (nil? @server-socket)
    (let [ss (ServerSocket. server-port)]
      (reset! server-socket ss)
      (future
        (while true
          (let [client (.accept ss)]
            (future
              (try
                (with-open [client client]
                  (let [in (BufferedReader. (InputStreamReader. (.getInputStream client)))
                        out (PrintWriter. (.getOutputStream client) true)
                        ; Line #1 is the command
                        command-name (.readLine in)
                        ; Line #2 is the number of command line arguments
                        arg-count (Integer. (.readLine in))
                        ; The next 'n' lines are the arguments, collect them
                        args (doall (for [_ (range arg-count)]
                                      (.readLine in)))]
                    (if-let [command (get commands command-name)]
                      (let [handler (:handler command)
                            uses-commands? (:uses-commands? command)]
                        ; bind in/out, so the typical io functions can be used
                        (binding [*in* in
                                  *out* out]
                          (if uses-commands?
                            (handler commands in args)
                            (handler in args))
                          #_(process command in out args)))
                      (binding [*out* *err*]
                        (println "Unknown command: " command-name)))))
                (catch Exception e
                  (binding [*out* *err*]
                    (.printStackTrace e)))))))))))


(defn stop-server
  "Stops the server, if it is started"
  []
  (when @server-socket
    (.close @server-socket)
    (reset! server-socket nil)))
