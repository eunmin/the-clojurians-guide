(defproject basic "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.7.0-RC1"]
                 [metosin/compojure-api "1.1.11"]]
  :ring {:handler basic.handler/app}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                  [restpect "0.2.1"]]
                   :plugins [[lein-ring "0.12.0"]]}})
