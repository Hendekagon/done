(ns done.core)

(defn move [direction [l r]]
  ([[(rest l) (cons (first (rest l)) r)]
    [(cons (first (rest r)) l) (rest r)]] direction))

(defn tm [f]
  (fn [[state position [[input & left] [input & right]]]]
    (let [[next-state output direction] (f state input)]
        [next-state (+ position (if (== 0 direction) -1 +1)) (move direction [(cons output left) (cons output right)])])))

(defn tmfn-from-rule-table [rule-table]
  (fn [state input]
    (get-in rule-table [state input] [:halt input])))

(def test-tm1
  [
    [[1 0 0] [2 0 0]]
    [[0 1 1] [1 1 0]]
    [[3 1 1] [1 1 1]]
    ])

(defn print-state [[state [[input & left] [input & right]]]]
  (concat (reverse (take 8 left)) `([ ~state ~input ])  right))

'(map print-state (take 8 (drop 250 (iterate (tm (make-tm test-tm1)) [0 [(repeat 0) (repeat 0)]]))))

(defn make-state
  ([]
    {
      :animate true
      :tm-rule-table test-tm1
      :tm-state [0 0 [(repeat 1024 0) (repeat 1024 0)]]
      :tm (tm (tmfn-from-rule-table test-tm1))
      :in-dt true
      :fdt 0
      :update-interval 128
      :t 0
      }))

(defn restart [s]
  (assoc s
    :t 0
    :tm-state [0 0 [(repeat 1024 0) (repeat  1024 0)]]
    :tm (tm (tmfn-from-rule-table (:tm-rule-table s)))))

(defn update-state [s]
  (assoc
    (update-in s [:tm-state] (:tm s))
    :t (inc (:t s))))