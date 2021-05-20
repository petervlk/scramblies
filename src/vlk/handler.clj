(ns vlk.handler
  (:require [reitit.coercion.spec]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [muuntaja.core :as m]
            [clojure.spec.alpha :as s]
            [vlk.scramblies :as scramblies]
            [vlk.validator :as v]))

(s/def ::scramble v/only-lower-case-letters?)
(s/def ::target v/only-lower-case-letters?)
(s/def ::scramblies-request (s/keys :req-un [::scramble ::target]))

(def routes
  [["/" {:post
         {:parameters {:body ::scramblies-request}
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
                         :middleware [muuntaja/format-negotiate-middleware
                                      muuntaja/format-response-middleware
                                      exception/exception-middleware
                                      muuntaja/format-request-middleware
                                      coercion/coerce-request-middleware
                                      coercion/coerce-response-middleware]}})
    (ring/create-default-handler
      {:not-found          (constantly {:status 404, :body "Nothing here"})
       :method-not-allowed (constantly {:status 405, :body "Not allowed"})
       :not-acceptable     (constantly {:status 406, :body "Not acceptable"})})))
