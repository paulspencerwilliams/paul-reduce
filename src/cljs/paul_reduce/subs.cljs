(ns paul-reduce.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :weights
  (fn [db]
    (reaction (:weights @db))))

(re-frame/register-sub
  :bmis
  (fn [db]
    (reaction (:bmis @db))))

(re-frame/register-sub
  :name
  (fn [db]
    (reaction (:name @db))))

(re-frame/register-sub
  :entered-date
  (fn [db]
    (reaction (:entered-date @db))))

(re-frame/register-sub
  :entered-weight
  (fn [db]
    (reaction (:entered-weight @db))))

(re-frame/register-sub
  :server-add-status
  (fn [db]
    (reaction (:server-add-status @db))))
