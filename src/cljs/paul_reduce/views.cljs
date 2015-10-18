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
  (let [weights (re-frame/subscribe [:weights])
        bmis (re-frame/subscribe [:bmis])]
    (fn []
      (let [unused-weight-ref @weights
            unused-bmi-ref @bmis]
        [:div.graph {:style {:min-width "310px" :max-width "800px"
                             :height    "400px" :margin "0 auto"}}]))))

(defn graph-did-mount [this]
  (let [weights (re-frame/subscribe [:weights])
        bmis (re-frame/subscribe [:bmis])]
    (.highcharts (js/$ (reagent/dom-node this))
                 (clj->js {:chart       {:type :spline}
                           :title       {:text ""}
                           :xAxis       {:type                 :datetime
                                         :dateTimeLabelFormats {:month "%e. %b"
                                                                :year  "%b"}

                                         :title                {:text "Date"}}
                           :yAxis       [{:title  {:text  "Weight (Kg)"
                                                   :style {:color "#4572A7"}}
                                          :labels {:style {:color "#4572A7"}}
                                          :min 70
                                          :max 100}
                                         {:title {:text  "BMI"
                                                  :style {:color "#000000"}}
                                          :labels {:style {:color "#000000"}}
                                          :min 20
                                          :max 30
                                          :opposite true}]
                           :tooltip     {:headerFormat "<b>{series.name}</b><br>"
                                         :pointFormat  "{point.x:%e. %b}: {point.y:.2f} m"}
                           :plotOptions {:spline {:marker {:enabled true}}
                                         :series {:animation false}}
                           :series      [{:name  "Weight"
                                          :data  @weights
                                          :yAxis 0}
                                         {:name  "BMI"
                                          :data  @bmis
                                          :yAxis 1}]}))))

(defn graph []
  (reagent/create-class {:reagent-render       graph-render
                         :component-did-mount  graph-did-mount
                         :component-did-update graph-did-mount
                         }))

(defn main-panel []
  [:div [notifications][form][graph]])
