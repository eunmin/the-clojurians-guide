(ns basic.core
  (:require [tracks.core :as t :refer [track deftrack]]))

(t/let [{:a {:b [greeting person]}} {:a {:b ["Hello" "World"]}}]
  (str greeting " " person "!"))

(t/let [{:a {:b x} :c {:d y}} {:a {:b 1} :c {:d 2}}]
  (+ x y))

(def swap-a-b (track {:a one :b two} {:a two :b one}))

(swap-a-b {:a 100 :b 3000})

(deftrack swap-x-y {:x one :y two} {:x two :y one})

(swap-x-y {:x 100 :y 3000})

((track {:a [zero one]} {:b [one zero]})
 {:a [:zero :one]})

(deftrack deeptx
  {0 zero, 1 one, 2 two, 3 three} {:a zero :b {:c one :d {:e two :f {:g three}}}})

(deeptx {0 "first" 1 "second" 2 "third" 3 "fourth"})

(deftrack move-players
  {:active-player p1 :players [p2 p3 p4]} {:active-player p2 :players [p3 p4 p1]})

(defonce game (atom {:active-player {:name "A"}
                     :players [{:name "B"}
                               {:name "C"}
                               {:name "D"}]}))

(swap! game move-players)

(swap! game move-players)

(swap! game move-players)

(deftrack one-to-many x {:a x :b {:c [x x]}})

(one-to-many "?")

(deftrack root-to-map x {:y x})

(root-to-map [])

(def move-a-key (track {:x one} {:y one}))

(move-a-key {:x "MoveMe"})

(move-a-key {:x [:a :b :c]})
