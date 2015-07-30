(ns shellac.commands)


;"[command in out args]
;
;           Processes a tcp request. Dispaches on 'command'.
;           Each defmethod instance is essentially a command line program.
;           It receives an input stream to read from -- i.e. stdin or output of a pipe,
;           an output stream to write to, and a list of command line arguments.
;
;           command: the name of the command to execute
;           in: the input stream
;           out: the output stream
;           args: command line arguments for the command"


(defn command*
  [name doc uses-stdin? handler]
  {:command        name
   :doc            doc
   :uses-stdin?    false
   :uses-commands? uses-stdin?
   :handler        handler})


(defn super-command*
  [name doc uses-stdin? handler]
  (assoc (command* name doc uses-stdin? handler) :uses-commands? true))




(def default-commands
  {"help"
   (super-command* "help"
                   "Shows docs for available commands"
                   false
                   (fn [commands in [help-command]]
                     (if help-command
                       (let [help-command (clojure.string/trim help-command)]
                         (if-let [command (get commands help-command)]
                           (do
                             (println "Command: " help-command)
                             (println "  Usage: " (:doc command)))
                           (println "Unknown command: " help-command)))
                       (let [command-names (sort (keys commands))]
                         (println "Available commands:\n")
                         (doseq [c command-names]
                           (println "\t" c))
                         (println "\nType 'help [command-name]' to get more information")))))})
