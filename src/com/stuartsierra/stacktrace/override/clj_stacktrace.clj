(in-ns 'clj-stacktrace.repl)

(defn pst-on
  "Originally clj-stacktrace.repl/pst-on,
  overridden by com.stuartsierra.stacktrace.raw
  Prints a full stack trace of Throwable to the writer."
  [^java.io.Writer writer _ ^Throwable e]
  (let [w (java.io.PrintWriter. writer)]
    (.printStackTrace e w)
    (.flush w)))

