(ns ep3.computationStructure (:gen-class))
(require '[ep3.tape :as tape])

(defn init 
  "Returns a tuple (E T F) by receiving a automata tuple and a tape representation" 
  [automata tape]
  { :E (automata :initialState)
    :T (automata :transition)
    :F (last tape) })

(defn readTape
  "Returns symbol on head tape from a computation structure"
  [computation-structure]
  (first (:F computation-structure)))

(defn reconfigTape
  "Returns forwarded tape from a computation structure"
  [computation-structure]
  (drop 1 (:F computation-structure)))

(defn exists-only-final-computations?
  "Returns whether the supplied computation list only has final computations (empty tape)"
  [reached-computation-list]
  (reduce
    (fn [acc computation]
      (if (tape/is-tape-empty? (computation :F))
        acc
      ;else
        (reduced false)))
    true ;initial acc
    reached-computation-list))

(defn is-valid-final-computation?
  "Returns whether a given computation's state list has a valid final state"
  [computation final-states]
  (let [ {states :E
          transitions :T
          tape :F} computation ]
    (if (or (vector? states) (list? states))
      (and (tape/is-tape-empty? tape) (not= nil (some (set final-states) states)))
    ;else
      (and (tape/is-tape-empty? tape) (contains? (set final-states) states)))))

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