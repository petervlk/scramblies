(ns vlk.system
  (:require [integrant.core :as ig]
            [ring.adapter.jetty :as jetty]
            [vlk.handler :as handler])
  (:gen-class))

(def system-config
  {:scramblies/jetty   {:handler (ig/ref :scramblies/handler)
                        :port    4000}
   :scramblies/handler nil})

(defmethod ig/init-key :scramblies/jetty [_ {:keys [handler port]}]
  (println "Starting server on port " port)
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key :scramblies/handler [_ _]
  (handler/create-app))

(defmethod ig/halt-key! :scramblies/jetty [_ jetty]
  (.stop jetty))

(defn -main
  [& _]
  (ig/init system-config))
