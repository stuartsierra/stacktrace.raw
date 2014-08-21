(in-ns 'clojure.repl)

(defn pst
  "Originally clojure.repl/pst,
  overridden by com.stuartsierra.stacktrace.raw"
  ([] (pst *e))
  ([e-or-depth]
     (if (instance? Throwable e-or-depth)
       (pst e-or-depth nil)
       (pst *e nil)))
  ([^Throwable e _]
     (.printStackTrace e)))
