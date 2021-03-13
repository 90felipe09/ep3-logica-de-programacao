(ns ep3.tape (:gen-class))
(require '[ep3.automataParser :as parser])
(require '[clojure.string :as str])

(defn read-tape
  [tape]
  (if (> (count tape) 0)
    (subs tape 0 1)
  ;else
    tape))

(defn config-tape
  [tape]
  (if (> (count tape) 0)
    (subs tape 1)
  ;else
    tape))

(defn is-tape-empty?
  "Returns whether the received tape is empty or not"
  [tape]
  (= (count tape) 0))
