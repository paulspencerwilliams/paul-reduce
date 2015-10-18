(ns paul-reduce.prod
  (:require [paul-reduce.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
