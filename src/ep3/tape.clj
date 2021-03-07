(ns ep3.tape (:gen-class))
(require '[ep3.automataParser :as parser])
(require '[clojure.string :as str])

(defn initTape
  "Returns a tape structure (double list)"
  [file-path]
  [["$"] (conj (str/split (slurp file-path) #"") "$")]
)

(defn readTape
  "Given a tape structure, returns the value being read by the head"
  [tape]
  (first (last tape))
)

(defn configTape
  "Given a tape structure, returns it by moving one cell right"
  [tape]
  [(conj (first tape) (readTape tape)) (drop 1 (last tape))]
)

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
