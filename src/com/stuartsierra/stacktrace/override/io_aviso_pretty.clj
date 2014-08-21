(in-ns 'io.aviso.exception)

(defn write-exception
  "Originally io.aviso.exception/write-exception,
  overridden by com.stuartsierra.stacktrace.raw"
  ([exception]
     (write-exception *out* exception))
  ([writer exception]
     (write-exception writer exception nil))
  ([^java.io.Writer writer ^Throwable exception _]
     (.printStackTrace exception (java.io.PrintWriter. writer))))

(defn format-exception
  "Originally io.aviso.exception/format-exception,
  overridden by com.stuartsierra.stacktrace.raw"
  ([exception]
     (format-exception exception nil))
  ([^Throwable exception _]
     (with-out-str
       (.printStackTrace exception (java.io.PrintWriter. *out*)))))
