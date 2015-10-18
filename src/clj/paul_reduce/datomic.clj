(ns paul-reduce.datomic
  (require [datomic.api :as d]
           [clj-time.coerce :as c]
           [clj-time.format :as f]))

(def uri "datomic:mem://health-tracker")

(d/create-database uri)
(def conn (d/connect uri))

(def health-schema [{:db/id                 (d/tempid :db.part/db)
                     :db/ident              :health/date
                     :db/valueType          :db.type/instant
                     :db/cardinality        :db.cardinality/one
                     :db/unique             :db.unique/identity
                     :db/doc                "Date of the health fact"
                     :db.install/_attribute :db.part/db}
                    {:db/id                 (d/tempid :db.part/db)
                     :db/ident              :health/weight
                     :db/valueType          :db.type/double
                     :db/cardinality        :db.cardinality/one
                     :db/doc                "Weight"
                     :db.install/_attribute :db.part/db}])

(d/transact conn health-schema)

(defn get-all []
  (sort-by first
           (map
             (fn [h]
               {:date (f/unparse (f/formatters :date) (c/from-date (:health/date h)))
                :weight (:health/weight h)})
             (flatten
               (d/q '[:find (pull ?e [*]) :where [?e :health/date]] (d/db conn)))))
  )

(defn register [date weight]
  (d/transact conn [{:db/id         (d/tempid :db.part/user)
                     :health/date   (c/to-date (f/parse (f/formatters :date) date))
                     :health/weight  (Double/parseDouble weight) }]))
