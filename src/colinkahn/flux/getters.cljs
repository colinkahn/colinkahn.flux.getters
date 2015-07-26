(ns colinkahn.flux.getters)

(defprotocol IGetter
  (-getter [this x]))

(extend-type cljs.core/PersistentVector
  IGetter
  (-getter [this m] (get-in m this)))

(extend-type cljs.core/Keyword
  IGetter
  (-getter [this m] (get m this)))

(extend-type cljs.core.Symbol
  IGetter
  (-getter [this m] (get m this)))

(extend-type string
  IGetter
  (-getter [this m] (get m this)))

(extend-type number
  IGetter
  (-getter [this m] (nth (into [] m) this)))

(extend-type function
  IGetter
  (-getter [this m] (this m)))

(extend-type cljs.core/List
  IGetter
  (-getter [this m] (get-in m this)))

(extend-type cljs.core/PersistentArrayMap
  IGetter
  (-getter [this m] (get m this)))

(extend-type cljs.core/PersistentHashMap
  IGetter
  (-getter [this m] (get m this)))

(defn getter
  ([seg]
   (getter identity seg))
  ([f & segs]
   (let [f (memoize f)]
     (fn [x] (apply f (map #(-getter % x) segs))))))
