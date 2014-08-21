(in-ns 'clojure.main)

(defn repl-caught
  "Originally clojure.main/repl-caught,
  overridden by com.stuartsierra.stacktrace.raw"
  [^Throwable e]
  (.printStackTrace e))
