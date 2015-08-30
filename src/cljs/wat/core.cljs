(ns wat.core
  (:require [reagent.core :as r]))

(defonce chart-config (r/atom
  {:chart {:type "bar"}
   :title {:text "Historic World Population by Region"}
   :subtitle {:text "Source: Wikipedia.org"}
   :xAxis {:categories ["Africa" "America" "Asia" "Europe" "Oceania"]
           :title {:text nil}}
   :yAxis {:min 0
           :title {:text "Population (millions)"
                   :align "high"}
           :labels {:overflow "justify"}}
   :tooltip {:valueSuffix " millions"}
   :plotOptions {:bar {:dataLabels {:enabled true}}}
   :legend {:layout "vertical"
            :align "right"
            :verticalAlign "top"
            :x -40
            :y 100
            :floating true
            :borderWidth 1
            :shadow true}
   :credits {:enabled false}
   :series [{:name "Year 1800"
             :data [107 31 635 203 2]}]
   }))

(defonce all (r/atom []))
(defonce new (r/atom ""))

(defn input []
  [:div
   [:input {:type "text"
            :value @new
            :on-change #(reset! new (-> % .-target .-value))}]
   [:input {:type "button"
            :value "add"
            :on-click #((swap! all conj (int @new))
                        (reset! new "")
                        (swap! chart-config assoc :series [{:name "what" :data @all}]))}]])

(defn lister []
  [:ul
   (for [item @all]
     [:li item])])

(defn graph-data []
    (js/$ (fn []
            (.highcharts (js/$ "#example")
                         (clj->js @chart-config))))
  [:p @chart-config])

(defn graph []

  [:div [:h1 "The Graph..."]
   [:div#example {:style {:min-width "310px" :max-width "800px"
                          :height "400px" :margin "0 auto"}}]
   [graph-data]])

(defn home []
  [:div
   [input]
   [lister]
   [graph]])

(r/render-component [home] (.getElementById js/document "app"))