(ns ep3.core (:gen-class))
(require '[ep3.automata :as automata])
(require '[ep3.tape :as tape])

(defn -main
  "Starting point."
  ([automata-path, tape-path, debug-option]
    (
      println "debug mode"
    )
  )
  ([automata-path, tape-path]
      ;; (def fileExtension (last (str/split automata-path #"\.")))
      ;; (if (= fileExtension "dfa")
      ;;   (simulator/DFA parsedAutomata)
      ;;   (simulator/NFA parsedAutomata)
      ;; )
    (println (automata/getAutomataDefinition automata-path) "\n")
    (loop [tape (tape/initTape tape-path)]
      (println "Fita: " tape)
      (println "Leitura do cabeçote:" (tape/readTape tape) "\n")
      (if (= (tape/readTape tape) "$")
        true
        (recur (tape/configTape tape))
      )
    )
  )
)