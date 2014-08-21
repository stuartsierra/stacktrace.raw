(in-ns 'ring.middleware.stacktrace)

(defn wrap-stacktrace-web
  "Originally ring.middleware.stacktrace/wrap-stacktrace-web,
  overridden by com.stuartsierra.stacktrace.raw"
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable ex
        {:status 500
         :headers {"content-type" "text/plain; charset=UTF-8"}
         :body (with-out-str
                 (.printStackTrace ex (java.io.PrintWriter. *out*)))}))))
