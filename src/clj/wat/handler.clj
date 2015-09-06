(ns wat.handler
  (:require [wat.datomic :as db]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.response :refer [redirect]]
            [environ.core :refer [env]]
            [clojure.data.json :as json :refer [write-str]]))

(def home-page
  (html
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     (include-css (if (env :dev) "css/site.css" "css/site.min.css"))]
    [:body [:div#app [:h3 "Loading..."]]
     (include-js "https://code.jquery.com/jquery-2.1.1.min.js")
     (include-js "http://code.highcharts.com/highcharts.js")
     (include-js "http://code.highcharts.com/modules/exporting.js")
     (include-js "js/app.js")
     [:script "wat.core.init();"]]]))

(defn get-weights []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (db/get-all))})

(defn record-weight [req]
  (prn req)
  (let [date (get (:body req) :date )
        weight (get (:body req) :weight)]
    (db/register date weight))
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (db/get-all))})

(defroutes routes
  (GET "/" [] home-page)
  (POST "/weights" req (record-weight req))
  (GET "/weights" [] (get-weights))
  (resources "/")
  (not-found "Not Found"))

(def app
  (let [handler (wrap-json-body (wrap-defaults #'routes (assoc-in site-defaults [:security :anti-forgery] false)) {:keywords? true :bigdecimals? true})]
    (if (env :dev) (-> handler wrap-exceptions wrap-reload) handler)))
