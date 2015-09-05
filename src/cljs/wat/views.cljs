(ns wat.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn date-input []
  (let [entered-date (re-frame/subscribe [:entered-date])]
    (fn []
      [:input {:type      "text"
               :value     @entered-date
               :on-change #(re-frame/dispatch
                            [:entered-date-changed (-> % .-target .-value)])}])))

(defn weight-input []
  (let [entered-weight (re-frame/subscribe [:entered-weight])]
    (fn []
      [:input {:type      "text"
               :value     @entered-weight
               :on-change #(re-frame/dispatch
                            [:entered-weight-changed (-> % .-target .-value)])}])))

(defn save-button []
  [:input {:type     "button"
           :value    "add"
           :on-click #(re-frame/dispatch [:add-button-clicked])}])

(defn form []
  [:form
   [:label "Date" [date-input]]
   [:label "Weight (Kg)" [weight-input]]
   [save-button]]
  )

(defn graph-render []
  (let [chart-config (re-frame/subscribe [:chart-config])]
    (fn []
      (let [unused-ref @chart-config]
        [:div.graph {:style {:min-width "310px" :max-width "800px"
                             :height    "400px" :margin "0 auto"}}]))))

(defn graph-did-mount [this]
  (let [chart-config (re-frame/subscribe [:chart-config])]
    (.highcharts (js/$ (reagent/dom-node this))
                 (clj->js @chart-config))))

(defn graph []
  (reagent/create-class {:reagent-render       graph-render
                         :component-did-mount  graph-did-mount
                         :component-did-update graph-did-mount
                         }))

(defn main-panel []
  [:div [form][graph]])