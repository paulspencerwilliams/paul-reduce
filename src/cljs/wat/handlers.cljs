(ns wat.handlers
  (:require [re-frame.core :as re-frame]
            [ajax.core :refer [GET POST]]
            [wat.db :as db]))

(re-frame/register-handler
  :add-button-clicked
  (fn [app-state]
    (let [new-weight [(Date.parse (:entered-date app-state))
                      (js/parseInt (:entered-weight app-state))]
          updated-weights (sort-by first (conj (:weights app-state) new-weight))]
      (ajax.core/POST "/weights"
            {:params {:date (:entered-date app-state)
                      :weight (float (:entered-weight app-state))}
             :handler       #(re-frame/dispatch [:server-add-success %1])
              :error-handler #(re-frame/dispatch [:bad-response %1])
             :response-format :json
             :keywords? true})
      (assoc
        app-state
        :weights      updated-weights
        :chart-config (assoc
                        (:chart-config app-state)
                        :series [{:name "Weight"
                                  :data updated-weights}])
        :server-add-status :requested))))

(re-frame/register-handler
  :entered-date-changed
  (fn [app-state [_ entered-date]]
    (assoc app-state :entered-date entered-date)))

(re-frame/register-handler
  :entered-weight-changed
  (fn [app-state [_ entered-weight]]
    (assoc app-state :entered-weight entered-weight)))

(re-frame/register-handler
  :bad-response
  (fn [app-state [_]]
    (assoc app-state :server-add-status :failed)))

(re-frame/register-handler
  :server-add-success
  (fn [app-state [_ response]]
    (let [updated-weights (map (fn [m] [(Date.parse (m :date)) (int (m :weight))])
                               (js->clj response))]
      (assoc
        app-state
        :weights      updated-weights
        :chart-config (assoc
                        (:chart-config app-state)
                        :series [{:name "Weight"
                                  :data updated-weights}])
        :server-add-status :success))))

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))
