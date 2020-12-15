;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day1) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
(require 2htdp/batch-io)



(define (day-1 l)
  (local [(define (day-1 l1 l2 l3)
            (cond [(empty? l1) (day-1 l (rest l2) l3)]
                  [(empty? l2) (day-1 l1 l (rest l3))]
                  [(empty? l3) (day-1 (rest l1) l2 l)]
                  [else
                   (if  (= 2020 (+ (first l1) (first l2) (first l3)))
                        (* (first l1) (first l2) (first l3))
                        (day-1 (rest l1) l2 l3))]))]
    (day-1 l l l)))


(time (day-1 (map string->number (read-lines "data.txt"))))