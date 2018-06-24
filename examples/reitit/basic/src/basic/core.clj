(ns basic.core
  (:require [reitit.ring :as ring]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.coercion :as rrc]
            [reitit.coercion.spec :as spec]
            [reitit.coercion.schema :as schema]
            [schema.core :refer [Int]]
            [ring.middleware.params]
            [muuntaja.middleware]
            [ring.adapter.jetty :refer [run-jetty]])
  (:import [org.eclipse.jetty.server Server]))

(def handler
  (ring/ring-handler
   (ring/router
    ["/api"
     {:swagger {:id ::math}}

     ["/swagger.json"
      {:get {:no-doc true
             :swagger {:info {:title "my-api"}}
             :handler (swagger/create-swagger-handler)}}]

     ["/spec"
      {:coercion spec/coercion
       :swagger {:tags ["spec"]}}

      ["/plus"
       {:get {:summary "plus with spec query parameters"
              :parameters {:query {:x int?, :y int?}}
              :responses {200 {:body {:total int?}}}
              :handler (fn [{{{:keys [x y]} :query} :parameters}]
                         {:status 200
                          :body {:total (+ x y)}})}
        :post {:summary "plus with spec body parameters"
               :parameters {:body {:x int?, :y int?}}
               :responses {200 {:body {:total int?}}}
               :handler (fn [{{{:keys [x y]} :body} :parameters}]
                          {:status 200
                           :body {:total (+ x y)}})}}]]]

    {:data {:middleware [ring.middleware.params/wrap-params
                         muuntaja.middleware/wrap-format
                         swagger/swagger-feature
                         rrc/coerce-exceptions-middleware
                         rrc/coerce-request-middleware
                         rrc/coerce-response-middleware]
            :swagger {:produces #{"application/json"
                                  "application/edn"
                                  "application/transit+json"}
                      :consumes #{"application/json"
                                  "application/edn"
                                  "application/transit+json"}}}
     :extract-request-format (comp :format :muuntaja/request)
     :extract-response-format (comp :format :muuntaja/response)})

   (ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path "/", :url "/api/swagger.json"})
    (ring/create-default-handler))))

(defn start-server []
  (run-jetty handler {:port 3000 :join? false}))

(defn stop-server [^Server s]
  (.stop s))
