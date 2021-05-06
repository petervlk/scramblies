(ns vlk.scramblies-test
  (:require [clojure.test :refer :all]
            [vlk.scramblies :refer :all]))

(deftest target-equals-scramble
  (testing "empty"
    (is (true? (scramble? "" ""))))
  (testing "string of length 1"
    (is (true? (scramble? "a" "a"))))
  (testing "string of length 3"
    (is (true? (scramble? "abc" "abc")))))

(deftest target-is-substring-of-scramble
  (testing "target empty"
    (is (true? (scramble? "xyz" ""))))
  (testing "target is string of length 1"
    (is (true? (scramble? "xyz" "x")))))