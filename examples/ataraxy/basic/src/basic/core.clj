(ns basic.core
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.handler :as handler]
            [ataraxy.response :refer [->response] :as response]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :as resp]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.params :refer [wrap-params]]
            [clojure.spec.alpha :as s])
  (:import [org.eclipse.jetty.server Server]))

(defmethod ataraxy/result-spec ::add [_]
  (s/cat :x int? :y (s/nilable int?)))

(def routes '{["/add" #{x ?y}] [:add ^int x ^int ?y]
              ["/users/" id] {"/foo" ^{:example "middleware param"} [:foo]
                              [:get "/bar/" name] ^:request-log [:bar name]}})

(defn add [{[_ x y] :ataraxy/result}]
  {:status 200, :headers {}, :body (str (+ x (or y 0)))})

(defn foo [request]
  {:status 200, :headers {}, :body "Foo"})

(defn bar [{[_ name] :ataraxy/result}]
  [::response/ok (str "Bar " name)])

(defn wrap-request-log [handler]
  (fn [request]
    (println request)
    (handler request)))

(defn wrap-example [handler param]
  (fn [request]
    (println param)
    (handler request)))

(def handler
  (wrap-params (ataraxy/handler
                {:routes routes
                 :middleware {:request-log wrap-request-log
                              :example wrap-example}
                 :handlers {:add add
                            :foo foo
                            :bar bar}})))

(defn start-server []
  (run-jetty handler {:port 3000 :join? false}))

(defn stop-server [^Server s]
  (.stop s))
