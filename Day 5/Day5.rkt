;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day5) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
(require 2htdp/batch-io)

(define file (read-lines "data.txt"))


;; Turn a location into a seat-id

(check-expect (process-seat-id "BFFFBBFRRR") 567)
(check-expect (process-seat-id "FFFBBBFRRR") 119)
(check-expect (process-seat-id "BBFFBBFRLL") 820)

(define (process-seat-id s)
  (+ (* (process-seat-row s) 8)
     (process-seat-column s)))

(check-expect (process-seat-row "BFFFBBFRRR") 70)
(check-expect (process-seat-row "FFFBBBFRRR") 14)
(check-expect (process-seat-row "BBFFBBFRLL") 102)

;; Gets the seat row

(define (process-seat-row s)
  ;; low is Natural ; the lower bound
  ;; high is Natural ; the upper bound
  (local [(define (fn-for-los los low high)
            (cond [(empty? los) high]
                  [(string=? (first los) "F")
                   (fn-for-los (rest los)
                               low
                               (floor (+ low (/ (- high low) 2))))]
                  [(string=? (first los) "B")
                   (fn-for-los (rest los)
                               (ceiling (+ low (/ (- high low) 2)))
                               high)]
                  [else
                   (fn-for-los (rest los)
                               low
                               high)]))]
    (fn-for-los (explode s) 0 127)))

(check-expect (process-seat-column "BFFFBBFRRR") 7)
(check-expect (process-seat-column "FFFBBBFRRR") 7)
(check-expect (process-seat-column "BBFFBBFRLL") 4)

;; Gets the seat column

(define (process-seat-column s)
  ;; low is Natural ; the lower bound
  ;; high is Natural ; the upper bound
  (local [(define (fn-for-los los low high)
            (cond [(empty? los) high]
                  [(string=? (first los) "L")
                   (fn-for-los (rest los)
                               low
                               (floor (+ low (/ (- high low) 2))))]
                  [(string=? (first los) "R")
                   (fn-for-los (rest los)
                               (ceiling (+ low (/ (- high low) 2)))
                               high)]
                  [else
                   (fn-for-los (rest los)
                               low
                               high)]))]
    (fn-for-los (explode s) 0 7)))



;; Solution for Part 1

(define (problem1 los)
  ;; acc is Natural ; the greatest Seat ID
  (local [(define (fn-for-los los acc)
            (cond [(empty? los) acc]
                  [else
                   (local [(define seat-id (process-seat-id (first los)))]
                     (fn-for-los (rest los)
                                 (if (> seat-id acc)
                                     seat-id
                                     acc)))]))]
    (fn-for-los los 0)))





;; Solution for Part 2

(define (problem2 los)
  (local [(define (problem2 lon)
            ;; acc is Natural ; the previous Seat ID
            ;; found? is Boolean ; has it found a potential value
            (local [(define losn-all (build-list (+ (* 127 8) 7) identity))

                    (define losn-missing (filter
                                          (lambda (n)
                                            (not (ormap
                                             (lambda (m) (= m n))
                                             lon)))
                                          losn-all))

                    (define (fn-for-lon lon acc found?)
                      (cond [(empty? lon) (error "wack")]
                            [else
                             (cond [(and found?
                                         (not (= (first lon) (add1 acc))))
                                    acc]
                                   [(and (not found?)
                                         (not (= (first lon) (add1 acc))))
                                    (fn-for-lon (rest lon)
                                                (first lon)
                                                true)]
                                   [else
                                    (fn-for-lon (rest lon)
                                                (first lon)
                                                false)])]))]
              (fn-for-lon losn-missing 0 false)))]
    (problem2 (map process-seat-id los))))


(problem1 file)
(problem2 file)