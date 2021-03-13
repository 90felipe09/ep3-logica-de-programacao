(ns ep3.dfa (:gen-class))
(require '[ep3.computationStructure :as cs])
(require '[ep3.tape :as tape])

(defn step
  "Returns a new computing state by applying a given transition function over
  an input computing state and a tape value."
  [computation]
  (let [ {current-state :E
          transitions :T
          tape :F} computation]
    { :E ((transitions current-state) (tape/read-tape tape))
      :T transitions
      :F (tape/config-tape tape) }))

(defn run-dfa
  "Returns wether the string in the tape is accepted by
  the machine supplied in hashmap representation"
  ([machine tape debug?]
  (let [final-states (machine :F)]
    (loop [computation {:E (machine :q) :T (machine :T) :F tape}]
      (if debug? (println (select-keys computation [:E :F])))
      (let [next-computation (step computation)]
        (if (cs/is-valid-final-computation? next-computation final-states)
          (do
            (if debug? (println (select-keys next-computation [:E :F])))
            true)
        ;else
          (if (tape/is-tape-empty? (next-computation :F))
            (do
              (if debug? (println (select-keys next-computation [:E :F])))
              false)
          ;else
            (recur next-computation)))))))
  ([machine tape]
    (run-dfa machine tape false)))