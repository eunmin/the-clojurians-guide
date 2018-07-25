(ns tutorial.core
  (:require [integrant.core :as ig]))

(def config {:group/key1 {:value (ig/ref :group/key2)}
             :group/key2 {}
             :group/key3 {}})


(defmethod ig/init-key :group/key1 [_ {:keys [value]}]
  (println "init-key :group/key1" value)
  value)

(defmethod ig/init-key :group/key2 [_ options]
  (println "init-key :group/key2")
  2)

(defmethod ig/init-key :group/key3 [_ _]
  (println "init-key :group/key3")
  3)

(defmethod ig/prep-key :group/key1 [_ {:keys [value]}]
  (println "prep-key :group/key1" value)
  (ig/ref :group/key3))

(defmethod ig/prep-key :group/key2 [_ options]
  (println "prep-key :group/key2")
  1)

(ig/init (ig/prep config))
