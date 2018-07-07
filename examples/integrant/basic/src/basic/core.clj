(ns basic.core
  (:require [integrant.core :as ig]))

(def config {:test/a {:name "a"}
             :test/b {:name "b" :test (ig/ref :test/a)}})

(defmethod ig/init-key :test/a [_ {:keys [name] :as opts}]
  (println "init :test/a name:" name)
  opts)

(defmethod ig/init-key :test/b [_ {:keys [name test] :as opts}]
  (println "init :test/b name:" name "test:" test)
  opts)

(defmethod ig/halt-key! :test/a [_ {:keys [name]}]
  (println "halt :test/a name:" name))

(defmethod ig/halt-key! :test/b [_ {:keys [name]}]
  (println "halt :test/b name:" name))

(def system (ig/init config))
;; init :test/a name: a
;; init :test/b name: b test: {:name a}

(ig/halt! system)
;; halt :test/b name: b
;; halt :test/a name: a
