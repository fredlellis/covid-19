(defproject covid-19 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.csv "1.0.0"]
                 [metasoarous/oz "1.6.0-alpha6"]
                 [clj-time "0.15.2"]]
  :resource-paths ["resources"]
  :main ^:skip-aot covid-19.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

