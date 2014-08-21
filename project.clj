(defproject com.stuartsierra/stacktrace.raw "0.1.0-SNAPSHOT"
  :description "Undo attempts to pretty-print Java stacktraces"
  :profiles {:dev [:clojure-1.7 :all-third-party]
             :all-third-party {:dependencies [[ring/ring-devel "1.3.0"]
                                              [clj-stacktrace "0.2.8"]
                                              [io.aviso/pretty "0.1.12"]
                                              [clj-pretty-error "0.0.6"]]}
             :clojure-1.7 {:dependencies [[org.clojure/clojure "1.7.0-alpha1"]]}
             :clojure-1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :clojure-1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :clojure-1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :clojure-1.3 {:dependencies [[org.clojure/clojure "1.3.0"]]}
             :clojure-1.2 {:dependencies [[org.clojure/clojure "1.2.1"]]}}
  :aliases {"check" ["with-profile" "all-third-party,clojure-1.2:all-third-party,clojure-1.3:all-third-party,clojure-1.4:all-third-party,clojure-1.5:all-third-party,clojure-1.6:all-third-party,clojure-1.7"
                     "check"]})
