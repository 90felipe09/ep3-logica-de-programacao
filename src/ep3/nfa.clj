(ns ep3.nfa (:gen-class))
(require '[ep3.computationStructure :as cs])
(require '[ep3.tape :as tape])

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
           tape :F } computing-state]
    (for [state states
          valid-transition (filter-valid-transitions (transitions state) (tape/read-tape tape))]
      { :E (second valid-transition)
        :T transitions
        :F (if (is-epsilon-transition? valid-transition) tape (tape/config-tape tape)) })))

(defn step
  "Returns the set of possible next computations based on an
  initial set of computations with a non-deterministic transition function"
  [computing-state-list]
  (flatten (concat (map #(get-next-computations %) computing-state-list))))

(defn run-nfa
  "Returns wether the string in the tape is accepted by
  the machine supplied in hashmap representation"
  ([machine tape debug?]
  (let [final-states (machine :F)]
    (loop [computation-list [{:E [(machine :q)] :T (machine :T) :F tape}]]
      (if debug? (println (map #(select-keys % [:E :F]) computation-list)))
      (let [next-computation-list (step computation-list)]
        (if (cs/exists-valid-final-computation? next-computation-list final-states)
          (do
            (if debug? (println (map #(select-keys % [:E :F]) next-computation-list)))
            true)
        ;else
          (if (cs/exists-only-final-computations? next-computation-list)
            (do
              (if debug? (println (map #(select-keys % [:E :F]) next-computation-list)))
              false)
          ;else
            (recur next-computation-list)))))))
  ([machine tape]
    (run-nfa machine tape false)))