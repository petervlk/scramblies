(ns vlk.handler
  (:require [ring.middleware.cors :refer [wrap-cors]]
            [reitit.coercion.spec]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [muuntaja.core :as m]
            [vlk.scramblies :as scramblies]))

(def routes
  [["/" {:post
         {:parameters {:body {:scramble string? :target string?}}
          :responses  {200 {:body {:scrambled boolean?}}}
          :handler    (fn [{:keys [parameters]}]
                          (let [scramble-result
                                (scramblies/scramble?
                                  (-> parameters :body :scramble)
                                  (-> parameters :body :target))]
                            {:status 200
                             :body   {:scrambled scramble-result}}))}}]])

(defn create-app []
  (ring/ring-handler
    (ring/router routes
                 {:data {:coercion   reitit.coercion.spec/coercion
                         :muuntaja   m/instance
                         :middleware [[wrap-cors
                                       :access-control-allow-origin [#"http://localhost:3000"]
                                       :access-control-allow-methods [:post]]
                                      muuntaja/format-negotiate-middleware
                                      muuntaja/format-response-middleware
                                      exception/exception-middleware
                                      muuntaja/format-request-middleware
                                      coercion/coerce-request-middleware
                                      coercion/coerce-response-middleware]}})
    (ring/create-default-handler
      {:not-found          (constantly {:status 404, :body "Nothing here"})
       :method-not-allowed (constantly {:status 405, :body "Not allowed"})
       :not-acceptable     (constantly {:status 406, :body "Not acceptable"})})))
