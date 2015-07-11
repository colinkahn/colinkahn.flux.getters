(ns colinkahn.flux.getters-test
  (:require [colinkahn.flux.getters :refer [getter]]
            [cemerick.cljs.test :as t])
  (:require-macros [cemerick.cljs.test
                    :refer [is deftest testing are]]))

(deftest getters
  (testing "protocols"
    (is (= 1           ((getter identity [:foo :bar]) {:foo {:bar 1}})))
    (is (= 1           ((getter identity '(:foo :bar)) {:foo {:bar 1}})))
    (is (= 1           ((getter identity {:foo :bar}) {{:foo :bar} 1})))
    (is (= 2           ((getter identity #(inc %)) 1)))
    (is (= :bar        ((getter identity :foo) {:foo :bar})))
    (is (= :bar        ((getter identity 'foo) {'foo :bar})))
    (is (= :bar        ((getter identity "foo") {"foo" :bar})))
    (is (= :bar        ((getter identity 0) [:bar])))
    (is (= [:foo :bar] ((getter identity 0) {:foo :bar}))))
  (testing "composing"
    (let [m {:foo :bar :baz [0 1 2]}
          foo (getter identity :foo)
          baz (getter identity :baz)
          first-baz (getter first baz)
          last-baz (getter last baz)
          foo-first-baz (getter (fn [f bf bl] [f bf bl]) foo first-baz last-baz)]
      (is (= [:bar 0 2] (foo-first-baz m)))))
  (testing "example"
    (let [state {:selected 1 :items [:foo :bar :baz]}
          selected (getter identity :selected)
          items (getter identity :items)
          selected-item (getter get items selected)]
      (is (= :bar (selected-item state))))))
