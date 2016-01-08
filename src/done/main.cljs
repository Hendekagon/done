(ns done.main
  (:require
    [done.core :as f]
    [done.om :as o]))

(enable-console-print!)

(o/start)

(defn on-js-reload []
  (o/start))