(ns ep3.nfa (:gen-class))
(require '[ep3.computationStructure :as cs])

(defn is-epsilon-transition?
  "Returns true if a given transition has \"eps\" as key, else false"
  [transition]
  (= (get-transition-key valid-transition) "eps"))

(defn get-transition-key
  "Returns the first element (the key) of a transition"
  [transition]
  (first transition))

(defn filter-valid-transitions
  "Returns only the transitions where the key is the input or \"eps\""
  [transitions input]
  (filter
    #(or (= (get-transition-key %) input) (is-epsilon-transition? %))
  transitions))

(defn step
  [ { state :E
      transitions :T
      tape :F } computing-state ]
  (for [valid-transition (filter-valid-transitions transitions (first tape))]
    (if (is-epsilon-transition? valid-transition)
      { :E ((valid-transition state) "eps")
        :T transitions
        :F tape }
      ;else
      { :E ((valid-transition state) (first tape))
        :T transitions
        :F (drop 1 tape) })))

(defn is-final-computation?
  [computation]
  (< (count (computation :T)) 1))

(defn is-valid-final-computation?
  [ computation final-states ]
  (let [ { :E state
           :T transitions
           :F tape } computation ]
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
    reached-computation-list)

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

(defn step
  "returns a new structure of reachable computing states by applying a given transition function over
  an input computing reached states and a tape value."
  [computation-list]
  (let [ proximas-comps
         (into #{} (for [computation computation-list] (get-next-states-for-computation computation))) ]
    (if (exists-valid-final-computation? proximas-comps)
      true
    ;else
      (if (exists-only-final-computations? proximas-comps)
        false
        ;else
        (recur proximas-comps))))
)



;; paramos quando temos todos os estados ou com fita vazia ou com alguma transição não-epsilon disponivel
;; se em algum momento um estado com fita vazia é final, para tudo e retorna true