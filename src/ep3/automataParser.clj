(ns ep3.automataParser (:gen-class))
(require '[clojure.data.json :as json])

(defn parseAutomata
  "Creates a automata set from raw input"
  [rawAutomata]
  (
    let [{Q "Q" S "S" T "T" q "q" F "F"} (json/read-str rawAutomata)]
    {
      :transition T
      :initialState q
      :acceptanceStates F
    }
  )
)

(defn automata-json-to-map
  "returns automata from input json representation as a map structure"
  [automata-json]
  (let [{Q "Q" S "S" T "T" q "q" F "F"} automata-json]
    {:Q Q :S S :T T :q q :F F}))

(defn parse-automata
  "returns automata read from given filepath in map representation"
  [filepath]
  (automata-json-to-map (json/read-str (slurp filepath))))

