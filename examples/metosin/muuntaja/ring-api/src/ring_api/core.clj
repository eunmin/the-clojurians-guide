(ns ring-api.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [muuntaja.core :as m]
            [muuntaja.middleware :refer [wrap-format]]))

(defn app [request]
  {:status 200 :headers {} :body "TEST"})

(defn start []
  (run-jetty (wrap-format app (assoc-in m/default-options [:http :encode-response-body?] (constantly true)))
             {:port 8080 :join? false}))

(defn -main []
  (start))
