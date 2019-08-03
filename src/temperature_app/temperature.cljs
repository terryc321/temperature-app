(ns temperature-app.temperature
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "This text is printed from src/temperature-app/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))


(def temp-f (atom 32))
(def temp-c (atom 0))

(defn f-to-c [x] (/ (* 5 (- x 32)) 9))

(defn c-to-f [x] (+ 32 (/ (* x 9) 5)))



(defn change-c [s]
  (let [n (.toFixed (js/parseFloat s) 2)]
    (if (js/isNaN n)
      (reset! temp-c s)
      [(reset! temp-c s)
       (reset! temp-f (.toFixed (c-to-f n) 2))]
      )
    )
  )



(defn celsius-entry []
    [:input {:type "text"
           :value @temp-c
           :on-change #(change-c (-> % .-target .-value))
           :placeholder "something to do..."
           :on-key-up #(and
                        (not (= "" (-> % .-target .-value)))
                        (if (= "Enter" (.-key %))
                          [
                           ;;(todo-add-item (-> % .-target .-value))
                           ;;(reset! todo-entry-val "")
                           ]
                          ))
             }])



(defn change-f [s]
  (let [n (.toFixed (js/parseFloat s) 2)]
    (if (js/isNaN n)
      (reset! temp-f s)
      [(reset! temp-f s)
       (reset! temp-c (.toFixed (f-to-c n) 2))]
      )
    )
  )




(defn fahrenheit-entry []
    [:input {:type "text"
           :value @temp-f
           :on-change #(change-f (-> % .-target .-value))
           :placeholder "something to do..."
           :on-key-up #(and
                        (not (= "" (-> % .-target .-value)))
                        (if (= "Enter" (.-key %))
                          [
                           ;;(todo-add-item (-> % .-target .-value))
                           ;;(reset! todo-entry-val "")
                           ]
                          ))
             }])





(defn temperature-app []
  [:div
   [:h1 "Temperature Conversion"]
   [:div
    [:p "celsius"]
    [celsius-entry]
    ]
   [:div
    [:p "fahrenheit"]
    [fahrenheit-entry]
    ]
   ]
  )




(reagent/render-component [temperature-app]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
