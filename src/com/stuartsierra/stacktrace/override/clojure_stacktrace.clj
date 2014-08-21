(in-ns 'clojure.stacktrace)

(defn print-stack-trace
  "Originally clojure.stacktrace/print-stack-trace,
  overridden by com.stuartsierra.stacktrace.raw"
  ([e]
     (print-stack-trace e nil))
  ([^Throwable e _]
     (.printStackTrace e (java.io.PrintWriter. *out*))))

(defn print-cause-trace
  "Originally clojure.stacktrace/print-cause-trace,
  overridden by com.stuartsierra.stacktrace.raw"
  ([e]
     (print-cause-trace e nil))
  ([^Throwable e _]
     (.printStackTrace e (java.io.PrintWriter. *out*))))

(defn e
  "Originally clojure.stacktrace/e,
  overridden by com.stuartsierra.stacktrace.raw"
  []
  (.printStackTrace ^Throwable *e (java.io.PrintWriter. *out*)))
