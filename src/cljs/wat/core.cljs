(ns wat.core
  (:require [reagent.core :as r]))

(def weights (r/atom ["124"]))
(def watwat (r/atom "124"))

(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])])

(defn lister-user []
  [:div
   "Here is a list:"
   [lister @weights]])

(defn atom-input [value]
  [:input {:type "text"
           :value @watwat
           :on-change #(reset! value (-> % .-target .-value))}])

(defn weight-input []
      [:div
       [:p "Add weight: " [atom-input @watwat]]])

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @watwat ". "])

(defn weighter []
  [:div
   [counting-component]
   [weight-input]
   [lister-user]])


(r/render [weighter] (.getElementById js/document "app"))