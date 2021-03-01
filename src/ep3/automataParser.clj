(ns ep3.automataParser (:gen-class))
(require '[clojure.data.json :as json])

(defn parseAutomata
  "Creates a automata set from raw input"
  [rawAutomata]
  (
    let [[Q S T q F] (vals (json/read-str rawAutomata))]
    {
      :transition T
      :initialState q
      :acceptanceStates F
    }
  )
)

