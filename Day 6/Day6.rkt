;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day6) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
(require 2htdp/batch-io)

(define file (read-lines "data.txt"))


(define-struct group (amount answers))
;; Group is (make-group Natural String)


(define-struct char-map (char count))
;; Map is (make-char-map String Natural)


(define (seperate los)
  ;; acc is Natural ; amount of people in a section
  ;; str is String ; current total string
  ;; log is (listof Group) ; the list of groups created
  (local [(define (fn-for-los los acc str log)
            (cond [(empty? los) log]
                  [(string=? (first los) "")
                   (fn-for-los (rest los)
                               0
                               ""
                               (cons (make-group acc str)
                                     log))]
                  [else
                   (fn-for-los (rest los)
                               (add1 acc)
                               (string-append (first los)
                                              str)
                               log)]))]
    (fn-for-los (append los (list "")) 0 "" empty)))

;; -----------------------------------------------------------

;; Solution for part 1

(check-expect (problem1 (list (make-group 3 "abcxabcyabcz")))
              6)
(check-expect (problem1 (list (make-group 1 "abc")
                              (make-group 3 "abc")
                              (make-group 2 "abac")
                              (make-group 4 "aaaa")
                              (make-group 1 "b")))
              11)

;(define (problem1 log) 0) ;stub

(define (problem1 log)
  ;; acc is Natural ; amount of answers to unique questions
  (local [(define (fn-for-log log acc)
            (cond [(empty? log) acc]
                  [else
                   (fn-for-log (rest log)
                               (+ (fn-for-group (first log))
                                  acc))]))

          (define (fn-for-group g)
            (length (char-map-values (explode (group-answers g)))))]
    (fn-for-log log 0)))


;; Solution for part 2

(check-expect (problem2 (list (make-group 3 "abcxabcyabcz")))
              3)
(check-expect (problem2 (list (make-group 1 "abc")
                              (make-group 3 "abc")
                              (make-group 2 "abac")
                              (make-group 4 "aaaa")
                              (make-group 1 "b")))
              6)

;(define (problem2 log) 0) ;stub

(define (problem2 log)
  ;; acc is Natural ; amount of answers to unique questions
  (local [(define (fn-for-log log acc)
            (cond [(empty? log) acc]
                  [else
                   (fn-for-log (rest log)
                               (+ (fn-for-group (first log))
                                  acc))]))

          (define (fn-for-group g)
            (process (char-map-values (explode (group-answers g)))
                     (group-amount g)))

          
          (define (process lom n)
            ;; acc is Natural ; the amount of values
            (local [(define (fn-for-lom lom acc)
                      (cond [(empty? lom) acc]
                            [else
                             (if (= n (char-map-count (first lom)))
                                 (fn-for-lom (rest lom)
                                             (add1 acc))
                                 (fn-for-lom (rest lom)
                                             acc))]))]
              (fn-for-lom lom 0)))]
    (fn-for-log log 0)))

;; -------------------------------------------------------------------


(define (char-map-values los)
  ;; lom is (listof Map) ; the generative map of counts
  (local [(define (fn-for-los los lom)
            (cond [(empty? los) lom]
                  [else
                   (fn-for-los (rest los) (add-to lom (first los)))]))]
    (fn-for-los los empty)))

(define (add-to lom0 s)
  ;; visited is (listof Map) ; the previous visited elements of lom in lom0 
  (local [(define (fn-for-lom lom visited)
            (cond [(empty? lom) (cons (make-char-map s 1) lom0)]
                  [else
                   (if  (compare-char (first lom))
                        (append visited
                                (list (increment-count (first lom)))
                                (rest lom))
                        (fn-for-lom (rest lom)
                                    (cons (first lom) visited)))]))

          (define (compare-char m)
            (string=? s (char-map-char m)))

          (define (increment-count m)
            (make-char-map (char-map-char m)
                           (add1 (char-map-count m))))]
    (fn-for-lom lom0 empty)))


;; ---------------------------------------------------------------------


(problem1 (seperate file))
(problem2 (seperate file))