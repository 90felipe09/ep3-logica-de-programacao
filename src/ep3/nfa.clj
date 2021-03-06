(ns ep3.nfa (:gen-class))
(require '[ep3.computationStructure :as cs])

(defn get-transition-key
  "Returns the first element (the key) of a transition"
  [transition]
  (first transition))

(defn is-epsilon-transition?
  "Returns true if a given transition has \"eps\" as key, else false"
  [transition]
  (= (get-transition-key transition) "eps"))

(defn filter-valid-transitions
  "Returns only the transitions where the key is the input or \"eps\""
  [transitions input]
  (select-keys transitions [input "eps"]))

(defn step
  "Returns the set of possible next computations based on an
  initial set of computations with a non-deterministic transition function"
  [computing-state]
  (let [ { states :E
           transitions :T
           tape :F } computing-state ]
  (println (transitions (first states)))
  (for [state states
        valid-transition (filter-valid-transitions (transitions state) (first tape))]
    { :E (flatten (rest valid-transition))
      :T transitions
      :F (if (is-epsilon-transition? valid-transition) tape (drop 1 tape)) })))

(defn is-final-computation?
  [computation]
  (< (count (computation :T)) 1))

(defn is-valid-final-computation?
  [ computation final-states ]
  (let [ { state :E
           transitions :T
           tape :F } computation ]
    (and (is-final-computation? computation) (contains? final-states state))))

(defn exists-valid-final-computation?
  [reached-computation-list final-states]
  (reduce 
    (fn [acc computation]
      (if (is-valid-final-computation? computation)
        (reduced true)
      ;else
        acc))
    false ;first acc
    reached-computation-list))

(defn exists-only-final-computations?
  [reached-computation-list final-states]
  (reduce
    (fn [acc computation]
      (if (is-final-computation? computation)
        acc
      ;else
        (reduced false)))
  true
  reached-computation-list))