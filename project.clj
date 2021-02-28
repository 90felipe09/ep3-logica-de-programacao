(defproject ep3 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/90felipe09/ep3-logica-de-programacao"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [ [org.clojure/clojure "1.10.1"]
                  [org.clojure/data.json "1.0.0"] ]
  :main ^:skip-aot ep3.core
  :target-path "target/%s"
  :repl-options {:init-ns ep3.core}
  :profiles {:uberjar {:aot :açç
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
