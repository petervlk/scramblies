(ns vlk.scramblies
  (:gen-class))

(defn keyed-char-counts
  [word]
  (letfn [(keyed-char-count [[char chars]] [char (count chars)])]
    (->> word
        (group-by identity)
        (map keyed-char-count)
        (into {}))))

(defn keyed-chars-meet-requirements?
  [keyed-chars-under-test [required-char min-char-count]]
  (and
    (contains? keyed-chars-under-test required-char)
    (<= min-char-count (get keyed-chars-under-test required-char))))

(defn contains-all-chars-with-sufficient-amount?
  [keyed-chars-under-test required-keyed-chars-with-minimum-vals]
  (every?
    (partial keyed-chars-meet-requirements? keyed-chars-under-test)
    required-keyed-chars-with-minimum-vals))

(defn scramble?
  [scramble target]
  (contains-all-chars-with-sufficient-amount?
    (keyed-char-counts scramble)
    (keyed-char-counts target)))

(defn -main
  [& args]
  (scramble? (first args) (second args)))
