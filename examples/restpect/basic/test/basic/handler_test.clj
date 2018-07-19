(ns basic.handler-test
  (:require [clojure.test :refer :all]
            [ring.adapter.jetty :refer [run-jetty]]
            [basic.handler :refer [app]]
            [restpect.core :refer [ok not-found bad-request]]
            [restpect.json :refer [GET POST PUT DELETE]]))

(def ^:private port 9999)

(defn with-http-server [f]
  (let [server (run-jetty app {:port port :join? false})]
    (try
      (f)
      (finally
        (.stop server)))))

(use-fixtures :once with-http-server)

(deftest plus-test
  (ok
   (GET (str "http://localhost:" port "/api/plus")  {"x" "1" "y" "1"})
   {:result 2}))

(deftest echo-test
  (let [pizza {:name "Pizza"
               :size "L"
               :origin {:country "FI"
                        :city "Seoul"}}]
    (ok
     (POST (str "http://localhost:" port "/api/echo") pizza)
     pizza)))

(deftest bad-request-test
  (bad-request
   (GET (str "http://localhost:" port "/api/plus")  {"x" "a" "y" "b"})
   {:errors map?}))

(deftest not-found-test
  (not-found
   (GET (str "http://localhost:" port "/api/not-found"))
   {:message #"not found"}))
