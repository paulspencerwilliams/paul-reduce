(ns wat.db)

(def default-db
  {:entered-date   ""
   :entered-weight ""
   :weights        []
   :chart-config   {:chart       {:type :spline}
                    :title       {:text "Snow depth at Vikjafjellet Norway"}
                    :subtitle    {:text "Irregular time data in Highcharts JS"}
                    :xAxis       {:type                 :datetime
                                  :dateTimeLabelFormats {:month "%e. %b"
                                                         :year  "%b"}
                                  :title                {:text "Date"}}
                    :yAxis       {:title {:text "Snow depth (m)"} :min 0}
                    :tooltip     {:headerFormat "<b>{series.name}</b><br>"
                                  :pointFormat  "{point.x:%e. %b}: {point.y:.2f} m"}
                    :plotOptions {:spline {:marker {:enabled true}}}
                    :series      [{}]}})