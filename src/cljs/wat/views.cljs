(ns wat.views
    (:require [re-frame.core :as re-frame]))

(defn input []
  [:div
   [:input {:type "text"}]
   [:input {:type "button"
            :value "add"}]])

(defn main-panel []
  [:div [input]])
