(ns wat.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :chart-config
 (fn [db]
   (reaction (:chart-config @db))))

(re-frame/register-sub
  :name
  (fn [db]
    (reaction (:name @db))))
