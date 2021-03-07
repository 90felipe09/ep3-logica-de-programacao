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
  (let [machine (fsmParser/parse-automata "resources/machines/test-machine.nfa")
        tape (slurp "resources/tapes/nfa-test-tape.tap")
        initial-computation {:E [(machine :q)] :T (machine :T) :F tape}
        resulting-computations (nfa/get-next-computations initial-computation)]
    (is (= '(["A"]) (map #(:E %) resulting-computations))))))

(deftest two-step-test
  (testing "nfa step chaining"
  (let [machine (fsmParser/parse-automata "resources/machines/test-machine.nfa")
        tape (slurp "resources/tapes/nfa-test-tape.tap")
        initial-computation {:E [(machine :q)] :T (machine :T) :F tape}
        first-step-computations (nfa/get-next-computations initial-computation)
        second-step-computations (concat (map #(nfa/get-next-computations %) first-step-computations))])))

(deftest is-tape-empty-test
  (testing "check computation for empty tape"
  (let [non-empty-tape "001"
        empty-tape ""]
    (is (= true (nfa/is-tape-empty? empty-tape)))
    (is (= false (nfa/is-tape-empty? non-empty-tape))))))

(deftest exists-only-final-computation-test
  (testing "check computation list for finality"
  (let [non-final-computation-list [{:E nil :T nil :F ""}
                                    {:E nil :T nil :F "0"}]
        final-computation-list [{:E nil :T nil :F ""}
                                {:E nil :T nil :F ""}] ]
    (is (= true (nfa/exists-only-final-computations? final-computation-list)))
    (is (= false (nfa/exists-only-final-computations? non-final-computation-list))))))

(deftest is-valid-final-computation-test
  (testing "check computation for validity and finality"
  (let [final-states ["B"]
        non-final-non-valid-comp {:E ["A"] :T nil :F "0"}
        non-final-valid-comp {:E ["B"] :T nil :F "0"}
        final-non-valid-comp {:E ["A"] :T nil :F ""}
        final-valid-comp {:E ["B"] :T nil :F ""}]
    (is (= false (nfa/is-valid-final-computation? non-final-non-valid-comp final-states)))
    (is (= true (nfa/is-valid-final-computation? final-valid-comp final-states)))
    (is (= false (nfa/is-valid-final-computation? final-non-valid-comp final-states)))
    (is (= false (nfa/is-valid-final-computation? non-final-valid-comp final-states))))))

(deftest exists-valid-final-computation-test
  (testing "check computation list for a state with finality and validity"
  (let [final-states ["B"]
        non-final-computation-list [{:E ["A"] :T nil :F ""}
                                    {:E ["B"] :T nil :F "0"}
                                    {:E ["A"] :T nil :F "0"}]
        final-computation-list [{:E ["A"] :T nil :F ""}
                                {:E ["B"] :T nil :F "0"}
                                {:E ["B"] :T nil :F ""}
                                {:E ["A"] :T nil :F "0"}] ]
    (is (= true (nfa/exists-valid-final-computation? final-computation-list final-states)))
    (is (= false (nfa/exists-valid-final-computation? non-final-computation-list final-states))))))

(deftest run-nfa-test
  (testing "check successful simulation"
  (let [machine (fsmParser/parse-automata "resources/machines/test-machine.nfa")
        tape (slurp "resources/tapes/nfa-test-tape.tap")]
    (is (= true (nfa/run-nfa machine tape))))))

