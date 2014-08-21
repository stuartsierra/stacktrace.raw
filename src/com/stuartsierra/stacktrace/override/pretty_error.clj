(in-ns 'pretty-error.core)

(defn print-pretty-stack-trace
  "Originally pretty-error.core/print-pretty-stack-trace
  overridden by com.stuartsierra.stacktrace.raw
  Prints a full stack trace of Throwable to *out*."
  [^Throwable ex & _]
  (.printStackTrace ex (java.io.PrintWriter. *out*)))
