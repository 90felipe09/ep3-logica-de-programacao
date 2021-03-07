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

(defn get-next-computations
  "Returns the set of next computations based on an
  initial computation with a non-deterministic transition function"
  [computing-state]
  (let [ { states :E
           transitions :T
           tape :F } computing-state ]
    (for [state states
          valid-transition (filter-valid-transitions (transitions state) (subs tape 0 1))]
      { :E (second valid-transition)
        :T transitions
        :F (if (is-epsilon-transition? valid-transition) tape (subs tape 1)) })))

(defn step
  "Returns the set of possible next computations based on an
  initial set of computations with a non-deterministic transition function"
  [computing-state-list]
  (first (concat (map #(get-next-computations %) computing-state-list))))

(defn is-tape-empty?
  "Returns whether the received tape is empty or not"
  [tape]
  (= (count tape) 0))

(defn exists-only-final-computations?
  "Returns whether the supplied computation list only has final computations (empty tape)"
  [reached-computation-list]
  (reduce
    (fn [acc computation]
      (if (is-tape-empty? (computation :F))
        acc
      ;else
        (reduced false)))
    true ;initial acc
    reached-computation-list))

(defn is-valid-final-computation?
  "Returns whether a given computation's state list has a valid final state"
  [computation final-states]
  (let [ { states :E
           transitions :T
           tape :F } computation ]
    (and (is-tape-empty? tape) (not= nil (some (set final-states) states)))))

(defn exists-valid-final-computation?
  "Returns whether the supplied computation list has at least one valid final computation"
  [reached-computation-list final-states]
  (reduce 
    (fn [acc computation]
      (if (is-valid-final-computation? computation final-states)
        (reduced true)
      ;else
        acc))
    false ;initial acc
    reached-computation-list))

(defn run-nfa
  ""
  [machine tape]
  (let [initial-computation {:E [(machine :q)] :T (machine :T) :F tape}
        final-states (machine :F)]
    (loop [computation-list [initial-computation]]
      (let [next-computation-list (step computation-list)]
        (println next-computation-list)
        (if (exists-valid-final-computation? next-computation-list final-states)
          true
        ;else
          (if (exists-only-final-computations? next-computation-list)
            false
          ;else
            (recur next-computation-list)))))))