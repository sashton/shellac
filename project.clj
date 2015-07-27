(defproject shellac "0.1.0-SNAPSHOT"
  :description "Shellac is a Clojure command-line enhancer. It provides the capability
   to write commands in Clojure, and run them from the command line without incurring
   the cost of a JVM/Clojure startup each time."
  :url "https://github.com/sashton/shellac"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [environ "1.0.0"]
                 [org.clojure/tools.nrepl "0.2.10"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
