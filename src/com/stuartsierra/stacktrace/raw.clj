(ns com.stuartsierra.stacktrace.raw
  "Loading this namespace searches for common libraries that attempt
  to reformat exceptions / stack traces and replaces them with
  functions that call Java's .printStackTrace. This reduces the risk
  that an error in an exception pretty-printer obscures the original
  exception it was trying to print.")

(defn- load-when-available
  "Attempts to load ns-name (symbol) with 'require'. If it succeeds,
  loads the filename (string) with 'load'."
  [ns-name filename]
  (when (try (require ns-name)
             true
             (catch Exception e false))
    (load filename)))

(load-when-available 'clojure.main
                     "override/clojure_main")

(load-when-available 'clojure.repl
                     "override/clojure_repl")

(load-when-available 'clojure.stacktrace
                     "override/clojure_stacktrace")

(load-when-available 'clj-stacktrace.repl
                     "override/clj_stacktrace")

(load-when-available 'io.aviso.exception
                     "override/io_aviso_pretty")

(load-when-available 'pretty-error.core
                     "override/pretty_error")

(load-when-available 'ring.middleware.stacktrace
                     "override/ring_devel")
