(ns done.build.build
  (:require [cljs.build.api :as cljs]))

(def cljs-options
  {
   :source-paths ["src"]
   :production   {
                  :output-to     "resources/public/js/main.js"      ; default: target/cljsbuild-main.js
                  ;:output-dir    "resources/public/js"
                  :asset-path    "js"
                  :optimizations :advanced
                  :main          "done.main"
                  :pretty-print  false
                  :warnings false
                  :parallel-build true
                  }
   :dev          {
                  :id            "dev"
                  :source-map    "resources/public/js/main.js.map"
                  :output-to     "resources/public/js/main.js"      ; default: target/cljsbuild-main.js
                  :output-dir    "resources/public/js"
                  :asset-path    "js"
                  :optimizations :none
                  :main          "done.main"
                  :pretty-print  true
                  :parallel-build true
                  }
   }
  )

(defn cljs-build []
  (cljs/build "src" (:production cljs-options)))