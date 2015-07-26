(ns colinkahn.flux.getters-test
  (:require [colinkahn.flux.getters :refer [getter]]
            [cemerick.cljs.test :as t])
  (:require-macros [cemerick.cljs.test
                    :refer [is deftest testing are]]))

(deftest getters
  (testing "protocols"
    (is (= 1           ((getter [:foo :bar]) {:foo {:bar 1}})))
    (is (= 1           ((getter '(:foo :bar)) {:foo {:bar 1}})))
    (is (= 1           ((getter {:foo :bar}) {{:foo :bar} 1})))
    (is (= 2           ((getter #(inc %)) 1)))
    (is (= :bar        ((getter :foo) {:foo :bar})))
    (is (= :bar        ((getter 'foo) {'foo :bar})))
    (is (= :bar        ((getter "foo") {"foo" :bar})))
    (is (= :bar        ((getter 0) [:bar])))
    (is (= [:foo :bar] ((getter 0) {:foo :bar}))))
  (testing "composing"
    (let [m {:foo :bar :baz [0 1 2]}
          foo (getter :foo)
          baz (getter :baz)
          first-baz (getter first baz)
          last-baz (getter last baz)
          foo-first-baz (getter (fn [f bf bl] [f bf bl]) foo first-baz last-baz)]
      (is (= [:bar 0 2] (foo-first-baz m)))))
  (testing "example"
    (let [state {:selected 1 :items [:foo :bar :baz]}
          selected (getter :selected)
          items (getter :items)
          selected-item (getter get items selected)]
      (is (= :bar (selected-item state))))))
