(ns vlk.handler)

(defn create-app []
  (fn [_] {:status 200 :body "Hello, Scramblies!"}))
