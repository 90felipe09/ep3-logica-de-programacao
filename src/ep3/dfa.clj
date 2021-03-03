(ns ep3.dfa (:gen-class))
(require '[ep3.computationStructure :as cs])

(defn step
  "returns a new computing state by applying a given transition function over
  an input computing state and a tape value."
  [computing-state]
  { :E (((computing-state :T) (computing-state :E)) (cs/readTape computing-state))
    :T (computing-state :T)
    :F (cs/reconfigTape computing-state) }   
)