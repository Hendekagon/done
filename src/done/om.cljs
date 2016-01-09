(ns done.om
  (:require
    [done.core :refer [make-state update-state restart]]
    [done.ui :refer [make-css request-animation-frame]]
    [goog.style]
    [garden.color :as color :refer [hsl rgb hex->rgb]]
    [garden.core :refer [css]]
    [om.core :as om  :include-macros true]
    [om.dom  :as dom :include-macros true]
    [sablono.core :as html :refer-macros [html]]
    ))

(defn bit [b i]
  [:div {:key i :className (str "bit " "bit-" b)} b])

(defn tm-component [[state position [[input & left] [input & right]]]]
  (om/component
    (html
      [:div
       {:className "tm-state"}
       [:div {:className "leftbits"}
        (map bit (take (+ 32 position) left) (range))]
       [:div {:className "tm-head"}
        [:div {:className "bit tm-head-input"} input]
        [:div {:className (str "bit tm-head-state tm-state-" state)} state]]
       [:div {:className "rightbits"}
        (map bit (take (- 32 position) right) (range))]
        ])))

(defn rule-table-component [rule-table]
  (om/component
    (html
      [:div {:className "rule-table"}
        (map
          (fn [row i]
            [:div {:className "rule-table-row"}
              (map
                (fn [values j]
                 [:div {:className "rule-table-row"}
                  (map
                    (fn [v k]
                    [:textarea
                     {:className "cell-value"
                      :value v
                      :rows 1 :cols 1
                      :maxLength 1
                      :minLength 1
                      :onChange
                      (fn [e]
                        (om/transact! rule-table
                          (fn [t]
                            (update-in t [i j k]
                              (fn [v]
                                (if (not (= "" (.-value (.-target e))))
                                 (.parseInt js/window (.-value (.-target e)))
                                 v))))) nil)}]) values (range))]) row (range))])
          rule-table (range))])))

(defn with-time [{:keys [last-update dt fdt update-interval] :as state}]
  (let [ndt (- (.now js/Date) last-update) fdt (+ fdt ndt) y (> fdt update-interval)
        ts (assoc state
             :last-update (.now js/Date)
             :dt ndt
             :fdt (if y 0 fdt)
             :in-dt y)
        ]
    (if y (update-state ts) ts)
    ))

(defn main-component [state owner]
  (reify
    om/IWillMount
    (will-mount [this]
      (goog.style/installStyles (css (make-css))))
    om/IDidMount
    (did-mount [this]
      (request-animation-frame
        (fn qwe []
          (om/transact! state with-time)
          (cond (:animate state) (request-animation-frame qwe)))))
    om/IRender
    (render [this]
      (html
        [:div {:className "app"}
          [:div {:className "frame-rate"} (str (:dt state) " " (:fdt state))]
         (om/build tm-component (:tm-state state))
         (om/build rule-table-component (:tm-rule-table state))
         [:div {:className "iterations"} (str "iteration " (:t state))]
         [:div {:className "button"
                :onClick (fn [e] (om/transact! state restart) nil)}
                  "restart"]]))))

(defn start
  ([]
    (start (make-state)))
  ([state]
    (om/root main-component state {:target (.getElementById js/document "app")})))