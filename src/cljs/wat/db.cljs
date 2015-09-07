(ns wat.db)

(def default-db
  {:entered-date   ""
   :entered-weight ""
   :weights        []
   :chart-config   {:chart       {:type :spline}
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
                                   :data []}]}
   :server-add-status :not-requested
   :bad-response false})
