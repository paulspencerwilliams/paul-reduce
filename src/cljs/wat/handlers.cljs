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
    (let [updated-weights
          (conj (:weights _)
                [(let [[y m d] (.split (:entered-date _) #"-")]
                   (Date.UTC (int y) (- (int m) 1) (int d)))
                 (int (:entered-weight _))])]
      {:weights      updated-weights
       :chart-config (assoc
                       (:chart-config _)
                       :series [{:name "Weight"
                                 :data updated-weights}])})))

(re-frame/register-handler
  :entered-date-changed
  (fn [app-state [_ entered-date]]
    (assoc app-state :entered-date entered-date)))

(re-frame/register-handler
  :entered-weight-changed
  (fn [app-state [_ entered-weight]]
    (assoc app-state :entered-weight entered-weight)))