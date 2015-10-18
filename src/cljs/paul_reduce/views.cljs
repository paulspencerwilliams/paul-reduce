(ns paul-reduce.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn notifications []
  (let [display (re-frame/subscribe [:server-add-status])]
    (fn []
      [:div
       (case @display
         :not-requested nil
         :requested [:div.notification "Sending..."]
         :success [:div.notification "Weight saved"]
         :failed [:div.error "Failed"])])))

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
  (let [weights (re-frame/subscribe [:weights])]
    (fn []
      (let [unused-ref @weights]
        [:div.graph {:style {:min-width "310px" :max-width "800px"
                             :height    "400px" :margin "0 auto"}}]))))

(defn graph-did-mount [this]
  (let [weights (re-frame/subscribe [:weights])]
    (.highcharts (js/$ (reagent/dom-node this))
                 (clj->js {:chart       {:type :spline}
                           :title       {:text "(reduce (reduce :pauls-weight))"}
                           :xAxis       {:type                 :datetime
                                         :dateTimeLabelFormats {:month "%e. %b"
                                                                :year  "%b"}

                                         :title                {:text "Date"}}
                           :yAxis       {:title {:text "Weight (Kg)"} :min 0 :max 100}
                           :tooltip     {:headerFormat "<b>{series.name}</b><br>"
                                         :pointFormat  "{point.x:%e. %b}: {point.y:.2f} m"}
                           :plotOptions {:spline {:marker {:enabled true}}}
                           :series      [{:name "Weight"
                                          :data @weights}]}))))

(defn graph []
  (reagent/create-class {:reagent-render       graph-render
                         :component-did-mount  graph-did-mount
                         :component-did-update graph-did-mount
                         }))

(defn main-panel []
  [:div.debug [notifications][form][graph]])
