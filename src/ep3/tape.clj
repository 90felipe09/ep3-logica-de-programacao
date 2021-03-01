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
