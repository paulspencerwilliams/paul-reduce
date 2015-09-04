(ns wat.handlers
  (:require [re-frame.core :as re-frame]
            [wat.db :as db]))

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/register-handler
  :add-button-clicked
  (fn [_]
    (let [weights (conj (:weights _) 1)]
      {:weights      weights
       :chart-config (assoc (:chart-config _) :series [{:name "Weight"
                                                        :data weights}])})))


