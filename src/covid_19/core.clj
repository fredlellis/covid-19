(ns covid-19.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [oz.core :as oz]
            [clj-time.coerce :as coerce]
            [clojure.string :as string])
  (:gen-class))



(comment "Data lake -> https://www.kaggle.com/sudalairajkumar/novel-corona-virus-2019-dataset/download")

(comment "Documentacao para integragir com o Vega ;; https://vega.github.io/vega-lite/docs/line.html#properties")






(defn read-csv []
  (with-open [reader (io/reader (io/resource "dataset/time_series_covid_19_confirmed.csv"))]
    (doall
      (csv/read-csv reader))))

(defn csv-data->maps [csv-data]
  (map zipmap
       (->> (first csv-data)                                ;; First row is the header
            (map keyword)                                   ;; Drop if you want string keys instead
            repeat)
       (rest csv-data)))

(defn covid-data []
  (into [] (flatten (map #(let [titulo (first %)
                                _data (first (second %))]
                            (into [] (filter map? (map (fn [data]
                                                         (if (not (some #{:Lat :Long :Country/Region :Province/State} data))
                                                           (hash-map :time (string/replace (str (first data)) #":" "")
                                                                     :qt (last data)
                                                                     :pais titulo)
                                                           data))
                                                       _data))))
                         (group-by :Country/Region (csv-data->maps (read-csv)))))))



(def line-plot
  {:data     {:values (covid-data)}
   :width    1300
   :height   700
   :encoding {:x     {:field "time" :time-unit "date" :type "temporal"}
              :y     {:field "qt" :type "quantitative"}
              :color {:field "pais" :type "nominal"}}
   :mark     "line"
   :point    true})

(defn start-server []
  (oz/start-server!))

(defn show []
  (oz/view! line-plot))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start-server)
  (show))

