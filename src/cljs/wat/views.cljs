(ns wat.views
    (:require [re-frame.core :as re-frame]
              [reagent.core :as reagent]))

(defn input []
  [:div
   [:input {:type "text"}]
   [:input {:type "button"
            :value "add"}]])

(defn graph-render []
    [:div {:style {:min-width "310px" :max-width "800px"
                   :height "400px" :margin "0 auto"}}])

(defn graph-did-mount [this])

(defn graph [data]
  (reagent/create-class {:reagent-render graph-render
                   :component-did-mount graph-did-mount
                   :component-did-update graph-did-mount
                   }))


(defn main-panel []
  [:div [input][graph]])
