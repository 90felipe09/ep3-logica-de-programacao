(ns ep3.core (:gen-class))
(require '[ep3.automata :as automata])
(require '[ep3.tape :as tape])
(require '[ep3.computationStructure :as cs])
(require '[ep3.dfa :as dfa])
(require '[clojure.string :as str])

(defn accepts?
    "returns true or false by checking if the state of a computing state matches
    one of the accepted states from the automata tuple definition"
    [computing-state automata]
    (contains? (set (automata :acceptanceStates)) (computing-state :E))
)

(defn applyProperSimulator
  "By reading automata input extension, returns proper computation step function"
  [automata-path]
  (def fileExtension (last (str/split automata-path #"\.")))
  (if (= fileExtension "dfa")
    dfa/step
    false
  )
)

(defn simulate-debug
  "executes simulation given an automata, a tape and a step function and returns true or false if automata accepts tape"
  [automata tape step]
  (loop [computation-state (cs/init automata tape)]
      (println "Estrutura de computação:" computation-state)
      (println "Leitura do cabeçote:" (cs/readTape computation-state) "\n")
      (println "Estado atual:" (computation-state :E) "\n")
      (if (= (cs/readTape computation-state) "$")
        (accepts? computation-state automata)
        (recur (step computation-state))
      )
  )
)

(defn simulate
  "executes simulation given an automata, a tape and a step function and returns true or false if automata accepts tape"
  [automata tape step]
  (loop [computation-state (cs/init automata tape)]
      (if (= (cs/readTape computation-state) "$")
        (accepts? computation-state automata)
        (recur (step computation-state))
      )
  )
)

(defn -main
  "Starting point."
  ([automata-path, tape-path, debug-option]
    (def automata (automata/getAutomataDefinition automata-path))
    (println "\nInput Automata:" automata "\n")
    (def tape (tape/initTape tape-path))
    (println "\nInput Tape:" tape "\n")
    (def step (applyProperSimulator automata-path))
    (println (simulate-debug automata tape step))
  )
  ([automata-path, tape-path]
    (def automata (automata/getAutomataDefinition automata-path))
    (println "\nInput Automata:" automata "\n")
    (def tape (tape/initTape tape-path))
    (println "\nInput Tape:" tape "\n")
    (def step (applyProperSimulator automata-path))
    (println (simulate automata tape step))
  )
)