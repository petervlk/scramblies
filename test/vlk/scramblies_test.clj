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

