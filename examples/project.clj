(defproject shellac-examples "0.1.0-SNAPSHOT"
  :description "Example usage of shellac library"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [shellac "0.1.0-SNAPSHOT"]]
  :main ^:skip-aot shellac-examples.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
