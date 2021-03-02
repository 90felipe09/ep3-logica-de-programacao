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