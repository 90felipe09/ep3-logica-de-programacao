(ns ep3.core-test (:gen-class))
(require '[clojure.test :refer :all])
(require '[ep3.nfa :as nfa])
(require '[ep3.automataParser :as fsmParser])
(require '[ep3.computationStructure :as cs])
(require '[ep3.tape :as tape])

(deftest get-transition-key-test
  (testing "get key of a transition in [key val] format"
  (let
    [transition ["a" ["B" "C"]]]
    (is (= "a" (nfa/get-transition-key transition))))))

(deftest is-epsilon-transition-test
  (testing "check if transition consumes empty (\"eps\") input"
  (let
    [ non-eps-transition  ["a" ["B" "C"]]
      eps-transition      ["eps" ["B" "C"]] ]
    (is (= true (nfa/is-epsilon-transition? eps-transition)))
    (is (= false (nfa/is-epsilon-transition? non-eps-transition))))))

(deftest filter-valid-transitions-test
  (testing "check if filtering transitions is succesful"
  (let
    [ transitions { "a" ["B" "C"]
                    "b" ["B" "C"]
                    "eps" ["B" "C"]}
      input "b" ]
    (is (= {"b" ["B" "C"] "eps" ["B" "C"]}
            (nfa/filter-valid-transitions transitions input))))))

(deftest step-test
  (testing "non-deterministic computation step"
  (let [machine (fsmParser/parse-automata "resources/test-machine.nfa")
        tape (slurp "resources/nfa-test-tape.tap")
        initial-computation {:E '((machine :q)) :T (machine :T) :F tape}
        resulting-computation (nfa/step initial-computation) ]
    (println resulting-computation))))