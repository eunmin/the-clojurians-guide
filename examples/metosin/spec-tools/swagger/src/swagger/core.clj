(ns swagger.core
  (:require [spec-tools.swagger.core :as swagger]
            [clojure.spec.alpha :as s]))

(s/def ::id string?)
(s/def ::name string?)
(s/def ::street string?)
(s/def ::city #{:tre :hki})
(s/def ::address (s/keys :req-un [::street ::city]))
(s/def ::user (s/keys :req-un [::id ::name ::address]))

(def s (swagger/swagger-spec
        {:swagger "2.0"
         :info {:version "1.0.0"
                :title "Sausages"
                :description "Sausage description"
                :termsOfService "http://helloreverb.com/terms/"
                :contact {:name "My API Team"
                          :email "foo@example.com"
                          :url "http://www.metosin.fi"}
                :license {:name "Eclipse Public License"
                          :url "http://www.eclipse.org/legal/epl-v10.html"}}
         :tags [{:name "user"
                 :description "User stuff"}]
         :paths {"/api/ping" {:get {:responses {:default {:description ""}}}}
                 "/user/:id" {:post {:summary "User Api"
                                     :description "User Api description"
                                     :tags ["user"]
                                     ::swagger/parameters {:path (s/keys :req [::id])
                                                           :body ::user}
                                     ::swagger/responses {200 {:schema ::user
                                                               :description "Found it!"}
                                                          404 {:description "Ohnoes."}}}}}}))
