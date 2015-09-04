(ns wat.db)

(def default-db
  {:weights      []
   :chart-config {:chart       {:type "line"}
                  :title       {:text "Pauls reducing reduce"}
                  :xAxis       {:type "datetime"
                                :title      {:text "Date"}}
                  :yAxis       {:min    0
                                :title  {:text  "Weight (Kg)"}
                                :labels {:overflow "justify"}}
                  :tooltip     {:valueSuffix " Kgs"}
                  :plotOptions {:bar {:dataLabels {:enabled true}}}
                  :legend      {:layout        "vertical"
                                :align         "right"
                                :verticalAlign "top"
                                :x             -40
                                :y             100
                                :floating      true
                                :borderWidth   1
                                :shadow        true}
                  :credits     {:enabled false}
                  :series      [{:name "Weight"
                                 :data []}]}})
