(ns vlk.scramblies-test
  (:require [clojure.test :refer :all]
            [vlk.scramblies :refer :all]))

(deftest target-scramble-equal
  (testing "empty"
    (is (true? (scramble? "" ""))))
  (testing "string of length 1"
    (is (true? (scramble? "a" "a"))))
  (testing "string of length 3"
    (is (true? (scramble? "abc" "abc")))))

(deftest target-scramble-equal-lenght-different-chars
  (testing "string of length 1"
    (is (false? (scramble? "a" "z"))))
  (testing "string of length 3"
    (is (false? (scramble? "abc" "xyz")))))

(deftest target-is-substring-of-scramble
  (testing "target empty"
    (is (true? (scramble? "xyz" ""))))
  (testing "target is string of length 1"
    (is (true? (scramble? "xyz" "x"))))
  (testing "target is string of length 2"
    (is (true? (scramble? "xyz" "yz")))))

(deftest target-is-not-substring-of-scramble
  (testing "target is string of length 1"
    (is (false? (scramble? "xyz" "a"))))
  (testing "target is string of length 2"
    (is (false? (scramble? "xyz" "yy")))))

(deftest scramble-composed-from-correct-chars-but-not-enough
  (testing "single char used - scramble is shorter"
    (is (false? (scramble? "rr" "rrr"))))
  (testing "google shorter"
    (is (false? (scramble? "gogle" "google")))))

(deftest assignment-defined-words
  (is (true? (scramble? "rekqodlw" "world")))
  (is (true? (scramble? "cedewaraaossoqqyt" "codewars")))
  (is (false? (scramble? "katas" "steak"))))

(deftest keyed-char-counts-test-all-chars-equal
  (testing "word of length 1"
    (is (= {\a 1} (keyed-char-counts "a"))))

  (testing "word longer than 1"
    (is (= {\z 2} (keyed-char-counts "zz")))
    (is (= {\r 5} (keyed-char-counts "rrrrr")))))

(deftest keyed-char-counts-test-multiple-chars
  (testing "one of each char"
    (is (= {\v 1 \l 1 \k 1} (keyed-char-counts "vlk"))))
  (testing "more than one of some chars"
    (is (= {\a 1 \l 2 \o 1} (keyed-char-counts "allo")))))

(deftest keyed-chars-meet-requirements-test
  (testing "doesnt contain all"
    (is (false? (keyed-chars-meet-requirements? {\a 2 \z 2} [\x 1 \z 1]))))
  (testing "not containing"
    (is (false? (keyed-chars-meet-requirements? {\a 2} [\z 1]))))
  (testing "exact"
    (is (true? (keyed-chars-meet-requirements? {\a 1} [\a 1]))))
  (testing "sufficient"
    (is (true? (keyed-chars-meet-requirements? {\d 5} [\d 5]))))
  (testing "insufficient"
    (is (false? (keyed-chars-meet-requirements? {\z 1} [\z 3])))))