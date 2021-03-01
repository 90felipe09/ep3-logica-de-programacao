(ns ep3.core (:gen-class))
(require '[ep3.automata :as automata])

(defn -main
  "Starting point."
  ([automata-path, tape-path, debug-option]
    (
      println "debug mode"
    )
  )
  ([automata-path, tape-path]
    (
      ;; (def fileExtension (last (str/split automata-path #"\.")))
      ;; (if (= fileExtension "dfa")
      ;;   (simulator/DFA parsedAutomata)
      ;;   (simulator/NFA parsedAutomata)
      ;; )
      println (automata/getAutomataDefinition automata-path)
    )
  )
)