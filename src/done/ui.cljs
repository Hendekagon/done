(ns done.ui)

(def request-animation-frame
  (or
    (.-requestAnimationFrame js/window)
    (.-webkitRequestAnimationFrame js/window)
    (.-mozRequestAnimationFrame js/window)
    (.-msRequestAnimationFrame js/window)
    (.-oRequestAnimationFrame js/window)))

(defn make-css []
  [
    [:body {:padding "0px" :font-family "sans-serif"}]
   [:.app {:padding "0px" :display :flex :flex-flow "column nowrap"
                :align-items "center"}]
   [:.frame-rate {:display :none}]
   [:.tm-state {:display :flex :flex-flow "row nowrap" :width "100%"
                :align-items "flex-start" :justify-content "center"
                :overflow :hidden
                :padding "0px" :margin "0px"
                :background "rgb(200,200,200)"}]
   [:.leftbits {:display :flex :flex-flow "row nowrap"
                :flex-direction "row-reverse"}]
   [:.rightbits {:display :flex :flex-flow "row nowrap"
                :flex-direction "row"}]
   [:.tm-head {:display :flex :justify-content "flex-start"
                :width "2em" :padding 0
                :flex-flow "column nowrap"}]
   [:.tm-head-state {:display :flex :background "white" :color "black"}]
   [:.bit {:display :flex :background "black" :color "white"
           :width "1em" :height "1em" :font-size "1em"
           :justify-content "center"
           :align-items "center"
           :margin "0.5px"
           :padding "0.5em"
           :white-space "pre" :font-family "monospace"}]
   [:.tm-head-input {:opacity 0.5}]
   [:.bit-0 {:background "white" :color "black"}]
   [:.bit-1 {:background "black" :color "white"}]
   ; generate these
   [:.tm-state-0 {:background "rgb(255,250,230)" :color "black"}]
   [:.tm-state-1 {:background "black"}]
   [:.tm-state-2 {:background "rgb(200,0,0)"}]
   [:.rule-table {:display :flex :flex-flow "column nowrap"
                  }]
   [:.rule-table-row {:display :flex :flex-flow "row nowrap"
                        :margin "0.25em" :background "white"
                        :justify-content "center"}]
   [:.cell-value {:resize :none :border :none
                  :font-size "2em" :font-family "monospace"
                  :padding "0.5em" :margin "1px"
                  :display :flex :flex-flow "row nowrap"
                  :justify-content "center"
                  :align-items "center"}]
   [:.button {
                :font-size "2em" :padding "0.5em"
                :cursor "pointer" :background "rgb(230,230,230)"
                :border :none
                :border-radius "8px"
                :user-select :none
                :-webkit-user-select :none}]
   [:.button:hover {:background "rgb(250,250,250)"}]
   [:.iterations {:padding "1em"}]
   ])