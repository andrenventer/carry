(ns app.controller
  (:require [app.model :as model]
            [app.util :refer [tagged]]
            [counter.core :as counter]
            [carry.core :as carry]
            [cljs.core.match :refer-macros [match]]))

(defn control
  [model signal dispatch-signal dispatch-action]
  (println "signal =" signal)
  (match signal
         ; in a more complex app we would also have to dispatch :on-start/:on-stop on inserting/removing subapps
         ; here we omit this because counter app has no start/stop code
         :on-insert (dispatch-action :insert)
         :on-remove (dispatch-action :remove)

         [[:on-counter-signal id] s]
         ((:control counter/spec)
           (carry/entangle model #(get-in % [:counters id]))
           s
           (tagged dispatch-signal [:on-counter-signal id])
           (tagged dispatch-action [:counter-action id]))))