(ns wat.core
  (:require [reagent.core :as r]))

(defn lister [items]
  [:ul
   (for [item items]
      [:li "Item " item])])

(defn lister-user [all]
  [:div
   "Here is a list:"
   [lister @all]])

(defn atom-input [new all]
  [:div
   [:input {:type "text"
            :value @new
            :on-change #(reset! new (-> % .-target .-value))}]
   [:input {:type "button"
            :value "add"
            :on-click #((swap! all conj (str @new)) (reset! new "") )}]])

(defn shared-state []
  (let [new (r/atom "") all (r/atom [])]
    (fn []
      [:div
       [:p "Change it here: " [atom-input new all]]
       [:p [lister-user all]]])))


(r/render [shared-state] (.getElementById js/document "app"))