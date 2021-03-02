(ns ep3.core (:gen-class))
(require '[ep3.automata :as automata])
(require '[ep3.tape :as tape])
(require '[ep3.computationStructure :as cs])

(defn -main
  "Starting point."
  ([automata-path, tape-path, debug-option]
    (loop [
      tape (tape/initTape tape-path)
      automata (automata/getAutomataDefinition automata-path)
    ]
      (println "Estrutura de computação:" (cs/init automata tape))
      (println "Leitura do cabeçote:" (tape/readTape tape) "\n")
      (if (= (tape/readTape tape) "$")
        true
        (recur (tape/configTape tape) automata)
      )
    )
  )
  ([automata-path, tape-path]
      ;; (def fileExtension (last (str/split automata-path #"\.")))
      ;; (if (= fileExtension "dfa")
      ;;   (simulator/DFA parsedAutomata)
      ;;   (simulator/NFA parsedAutomata)
      ;; )
    (loop [
      tape (tape/initTape tape-path)
      automata (automata/getAutomataDefinition automata-path)
    ]
      (if (= (tape/readTape tape) "$")
        true
        (recur (tape/configTape tape) automata)
      )
    )
  )
)