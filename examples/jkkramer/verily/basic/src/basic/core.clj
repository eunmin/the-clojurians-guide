(ns basic.core
  (:require [jkkramer.verily :as v]))

(def validations
  [[:required [:foo :bar :password]]
   [:equal [:password :confirm-password] "Passwords don't match, dummy"]
   [:min-length 8 :password]])

(v/validate {:foo "foo"
             :password "foobarbaz"
             :password-confirm "foobarba"}
            validations)

(def validator (v/validations->fn validations))

(defn validate-password [m]
  (when (#{"12345" "password" "hunter2"} (:password m))
    {:keys [:password] :msg "You can't use that password"}))

(def validator
  (v/combine
   (v/required [:foo :bar :password])
   (v/equal [:password :password-confirm])
   (v/min-length 8 :password)
   validate-password))

(validator {:foo "foo"
            :password "foobarbaz"
            :password-confirm "foobarba"})
