(ns ep3.core (:gen-class))
(require '[ep3.tape :as tape])
(require '[ep3.computationStructure :as cs])
(require '[ep3.dfa :as dfa])
(require '[ep3.nfa :as nfa])
(require '[ep3.automataParser :as fsmParser])
(require '[clojure.string :as str])

(defn applyProperSimulator
  "By reading automata input extension, returns proper computation step function"
  [automata-path]
  (let [fileExtension (last (str/split automata-path #"\."))]
    (case fileExtension
      "dfa" dfa/run-dfa
      "nfa" nfa/run-nfa
      false
    )))

(defn -main
  "Entry point"
  ([automata-path tape-path debug-option]
    (let [automata (fsmParser/parse-automata automata-path)
          tape (slurp tape-path)
          simulator (applyProperSimulator automata-path)
          debug? (not= debug-option false)]
      (println (str "Input Automata: " automata))
      (println (str "Input Tape: " tape))
      (println "Resultado do processamento da fita: " (simulator automata tape debug?))))
  ([automata-path tape-path]
    (-main automata-path tape-path false)))