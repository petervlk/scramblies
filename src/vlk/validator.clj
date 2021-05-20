(ns vlk.validator)

(defn only-lower-case-letters? [string-value]
  (and
    (string? string-value)
    (re-matches #"^[a-z]+$" string-value)))
