(ns ep3.automataParser (:gen-class))
(require '[clojure.data.json :as json])

(defn parseAutomata
  "Creates a automata set from raw input"
  [rawAutomata]
  (
    let [{Q "Q" S "S" T "T" q "q" F "F"} (json/read-str rawAutomata)]
    (println (json/read-str rawAutomata))
    {
      :transition T
      :initialState q
      :acceptanceStates F
    }
  )
)

