;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day16) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
(require 2htdp/batch-io)
(require racket/string)

(define file (read-lines "dataSort2.txt"))

(define fileSplit 

  (local [(define (string-split-local s)
            (string-split s ", "))]
    (map string-split-local file)))


(define-struct parsing (lolos acc))



;; Determine pattern

(check-expect (find-pattern (list (list "row")
                                  (list "class" "row")
                                  (list "class" "row" "seat")))
              (list "row" "class" "seat"))

;(define (find-pattern lolos) empty) ;stub

(define (find-pattern lolos)
  ;; todo ; worklist
  (local [(define (fn-for-parsing p todo)
            (cond [(empty? (parsing-lolos p)) (parsing-acc p)]
                  [else (local [(define next (next-parsings p))]
                          (if (not (false? next))
                              (fn-for-parsing (first next)
                                              (append (rest next) todo))
                              (fn-for-parsing (first todo)
                                              (rest todo))))]))

          (define (next-parsings p)
            (local [(define (not-member? s)
                      (not (member? s (parsing-acc p))))
                    (define not-there
                      (filter not-member? (first (parsing-lolos p))))
                    (define (create-new-parsing s)
                      (make-parsing (rest (parsing-lolos p))
                                    (append (parsing-acc p) (list s))))]
              (cond [(empty? not-there) false]
                    [else
                     (map create-new-parsing not-there)])))]
    (fn-for-parsing (make-parsing lolos empty) empty)))

(find-pattern fileSplit)