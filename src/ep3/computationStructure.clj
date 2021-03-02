(ns ep3.computationStructure (:gen-class))

(defn init 
    "Returns a tuple (E T F) by receiving a automata tuple and a tape representation" 
    [automata tape]
    {
        :E (automata :initialState)
        :T (automata :transition)
        :F (last tape)
    }   
)

(defn readTape
    "Returns symbol on head tape from a computation structure"
    [computation-structure]
    (first (computation-structure :F))
)

(defn reconfigTape
    "Returns forwarded tape from a computation structure"
    [computation-structure]
    (drop 1 (computation-structure :F))
)