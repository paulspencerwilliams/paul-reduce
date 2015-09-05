(ns wat.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
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
    [:body
     [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]]
     (include-js "https://code.jquery.com/jquery-2.1.1.min.js")
     (include-js "http://code.highcharts.com/highcharts.js")
     (include-js "http://code.highcharts.com/modules/exporting.js")
     (include-js "js/app.js")
     [:script "wat.core.init();"]]]))

(defn weights []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str [{:date "2015-09-05" :weight 100}
                          {:date "2015-09-06" :weight 100}])})

(defroutes routes
  (GET "/" [] home-page)
  (POST "/weights" [] (weights))
  (GET "/weights" [] (weights))
  (resources "/")
  (not-found "Not Found"))

(def app
  (let [handler (wrap-defaults #'routes (assoc-in site-defaults [:security :anti-forgery] false))]
    (if (env :dev) (-> handler wrap-exceptions wrap-reload) handler)))
