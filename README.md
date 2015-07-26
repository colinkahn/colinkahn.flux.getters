# colinkahn.flux.getters

[![Clojars
Project](http://clojars.org/colinkahn.flux.getters/latest-version.svg)]

Composable, memoized getters for use in [Flux](https://facebook.github.io/flux/)
applications. It's mainly a paired down version of
[Fresnel](https://github.com/ckirkendall/fresnel) inspired by
[NuclearJS](https://github.com/optimizely/nuclear-js).

## Usage

```cljs
(def state {:selected 1 :items [:foo :bar :baz]})
(def selected (getter :selected))
(def items (getter :items))
(def selected-item (getter get items selected))

(selected-item state) ; => :bar
```

## License

Copyright © 2015 colinkahn

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
