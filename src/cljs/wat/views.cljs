(ns wat.views
    (:require [re-frame.core :as re-frame]
              [reagent.core :as reagent]))

(defn input []
  [:div
   [:input {:type "text"}]
   [:input {:type "button"
            :value "add"
            :on-click  #(re-frame/dispatch [:add-button-clicked])}]])

(defn graph-render []
  (.log js/console "rendered")
  (let [chart-config (re-frame/subscribe [:chart-config])]
    (fn []
      (let [unused-ref @chart-config]
    [:div.graph {:style {:min-width "310px" :max-width "800px"
                   :height "400px" :margin "0 auto"}}]))))


(defn graph-did-mount [this]
  (.log js/console "mounted")
  (let [chart-config (re-frame/subscribe [:chart-config])]
  (.highcharts (js/$ (reagent/dom-node this))
               (clj->js @chart-config))))

(defn graph []
  (reagent/create-class {:reagent-render graph-render
                   :component-did-mount graph-did-mount
                   :component-did-update graph-did-mount
                   }))

(defn output-config []
  (let [chart-config (re-frame/subscribe [:chart-config])]
    (fn []
  [:div.wwww (str @chart-config) ])))

(defn main-panel []
  [:div [input][graph][output-config]])
