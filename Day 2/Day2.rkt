;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day2) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
(require 2htdp/batch-io)
(require racket/string)
(require racket/bool)

(define file (read-lines "data.txt"))


(define-struct data (low high letter password))
;; Data is (make-data Natural Natural String String)
;; interp. a set of data, with a low value, a high value, a letter, and a psswrd


(define (process f)
  (local [(define (fn-for-los los lod)
            (cond [(empty? los) lod]
                  [else
                   (local [(define (split-data d)
                             (local [(define split (string-split d))
                                     (define split-num
                                       (string-split
                                        (string-replace
                                         (first split) "-" " ")))]
                               (make-data (string->number (first split-num))
                                          (string->number (second split-num))
                                          (substring (second split) 0 1)
                                          (third split))))]
                     (fn-for-los (rest los) (cons (split-data (first los))
                                                  lod)))]))]
    (fn-for-los f empty)))


;; solution for one
(check-expect (problem1 (list (make-data 1 3 "a" "abcde")
                              (make-data 1 3 "b" "cdefg")
                              (make-data 2 9 "c" "ccccccccc")))
              2)

(define (problem1 lod)
  ;; acc is Natural ; count of occurrances
  ;; todo is (listof Data) ; worklist
  (local [(define (fn-for-lod todo acc)
            (cond [(empty? todo) acc]
                  [else
                   (fn-for-data (first todo) (rest todo) acc)]))

          (define (fn-for-data d todo acc)
            (if (helper1? d)
                (fn-for-lod todo (add1 acc))
                (fn-for-lod todo acc)))]
    (fn-for-lod lod 0)))

(define (helper1? d)
  (<= (data-low d)
      (foldr + 0 (map (lambda (s) (if (string=? s
                                                (data-letter d))
                                      1
                                      0))
                      (explode (data-password d))))
      (data-high d)))

;; solution for two
(check-expect (problem2 (list (make-data 1 3 "a" "abcde")
                              (make-data 1 3 "b" "cdefg")
                              (make-data 2 9 "c" "ccccccccc")))
              1)

(define (problem2 lod)
  ;; acc is Natural ; count of occurrances
  ;; todo is (listof Data) ; worklist
  (local [(define (fn-for-lod todo acc)
            (cond [(empty? todo) acc]
                  [else
                   (fn-for-data (first todo) (rest todo) acc)]))

          (define (fn-for-data d todo acc)
            (if (helper2? d)
                (fn-for-lod todo (add1 acc))
                (fn-for-lod todo acc)))]
    (fn-for-lod lod 0)))

(define (helper2? d)
  (xor
   (string=? (string-ith (data-password d) (- (data-low d) 1))
             (data-letter d))
   (string=? (string-ith (data-password d) (- (data-high d) 1))
             (data-letter d))))

(problem1 (process file))
(problem2 (process file))