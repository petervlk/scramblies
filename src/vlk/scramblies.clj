(ns vlk.scramblies
  (:gen-class))

(defn char-frequencies
  [word]
  (letfn [(keyed-char-count [[char chars]] [char (count chars)])]
    (->> word
        (group-by identity)
        (map keyed-char-count)
        (into {}))))

(defn contains-char-with-sufficient-amount?
  [char-freq-map [char char-count]]
  (and
    (contains? char-freq-map char)
    (not (> char-count (get char-freq-map char)))))

(defn char-freq-superset?
  [char-freq-tested char-freq-base]
  (every?
    (partial contains-char-with-sufficient-amount? char-freq-tested)
    char-freq-base))

(defn scramble?
  [scramble target]
  (char-freq-superset? (char-frequencies scramble) (char-frequencies target)))

(defn -main
  [& args]
  (scramble? (first args) (second args)))
