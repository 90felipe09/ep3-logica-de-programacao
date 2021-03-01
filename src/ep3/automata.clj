(ns ep3.automata (:gen-class))
(require '[ep3.automataParser :as parser])
(require '[clojure.string :as str])

(defn getAutomataDefinition
  "Returns a transition function, a list of final states and the initial state."
  [file-path]
  (parser/parseAutomata (slurp file-path) )
)

