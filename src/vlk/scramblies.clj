(ns vlk.scramblies
  (:gen-class))

(defn scramble? [scramble target]
  (clojure.string/includes? scramble target))

(comment
  (scramble? nil nil))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
