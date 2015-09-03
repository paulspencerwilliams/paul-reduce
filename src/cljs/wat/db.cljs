(ns wat.db)

(def default-db
  {:chart-config {:chart {:type "bar"}
                  :title {:text "Pauls reducing reduce"}
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
                  :series [{:name "Weight"
                            :data []}]
                  }})
