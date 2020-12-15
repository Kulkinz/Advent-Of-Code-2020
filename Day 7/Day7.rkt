;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day7) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
(require 2htdp/batch-io)
(require racket/string)

(define file (read-lines "data.txt"))



(define-struct bag (colour amount))
;; Bag is (make-bag String Natural)
;; interp. A bag and the amount there are

(define B1A (make-bag "bright white" 1))
(define B1B (make-bag "muted yellow" 2))
(define B2A (make-bag "bright white" 3))
(define B2B (make-bag "muted yellow" 2))
(define B3A (make-bag "shiny gold" 1))
(define B4A (make-bag "shiny gold" 2))
(define B4B (make-bag "faded blue" 9))
(define B5A (make-bag "dark olive" 1))
(define B5B (make-bag "vibrant plum" 2))
(define B6A (make-bag "faded blue" 3))
(define B6B (make-bag "dotted black" 4))
(define B7A (make-bag "faded blue" 5))
(define B7B (make-bag "dotted black" 6))
;;(define B8A (make-bag "no other" 0))
;;(define B9A (make-bag "no other" 0))


(define-struct comb (type bags))
;; Comb is (make-comb String (listof Bag))
;; A list of bags associated with a bag type

(define C1 (make-comb "light red" (list B1A B1B)))
(define C2 (make-comb "dark orange" (list B2A B2B)))
(define C3 (make-comb "bright white" (list B3A)))
(define C4 (make-comb "muted yellow" (list B4A B4B)))
(define C5 (make-comb "shiny gold" (list B5A B5B)))
(define C6 (make-comb "dark olive" (list B6A B6B)))
(define C7 (make-comb "vibrant plum" (list B7A B7B)))
(define C8 (make-comb "faded blue" empty))
(define C9 (make-comb "dotted black" empty))

(define LOC (list C1
                  C2
                  C3
                  C4
                  C5
                  C6
                  C7
                  C8
                  C9))


;; Turns the files into the proper formatting

(define (los->loc los)
  (local [(define (split s)
            (local [(define seperated (split-contain s))]
              (make-comb (string-replace (first seperated) " bags" "")
                         (fn-for-los
                          (string-split (first (rest seperated)) ", ")))))

          (define (split-contain s)
            (string-split (string-trim s ".") " contain "))

          (define (string->bag s)
            (local [(define details (string-trim (string-trim s "s") " bag"))
                    (define split (string-split details))
                    (define (string-append-space b s)
                      (string-append b " " s))]
              (if (string=? details "no other")
                  empty
                  (make-bag (string-trim
                             (foldr string-append-space "" (rest split)))
                            (string->number (first split))))))

          (define (fn-for-los los)
            (cond [(empty? los) empty]
                  [else
                   (local [(define try (string->bag (first los)))]
                     (if (not (empty? try))
                         (cons try (fn-for-los (rest los)))  
                         (fn-for-los (rest los))))]))]
    (map split los)))



;; -----------------------------------------------------------

;; Solution for Part 1

(check-expect (problem1 LOC) 4)

;(define (problem1 loc) 0) ;stub

(define (problem1 loc0)
  ;; acc is Natural ; amount of bags containing a shiny gold bag
  (local [(define (fn-for-loc loc acc)
            (cond [(empty? loc) acc]
                  [else
                   (fn-for-loc (rest loc)
                               (if (contains? (first loc) "shiny gold")
                                   (add1 acc)
                                   acc))]))

          (define (contains? c s)
            ;; todo is (listof Comb) ; the areas left to visit
            ;; visited is (listof String) ; the areas visited so far
            (local [(define (fn-for-comb c todo visited)
                      (cond [(string=? (comb-type c) s) true]
                            [(member? (comb-type c) visited)
                             (fn-for-loc todo visited)]
                            [else
                             (fn-for-loc (append todo
                                                 (fn-for-lob (comb-bags c)))
                                         (cons (comb-type c) visited))]))

                    (define (fn-for-lob lob)
                      (cond [(empty? lob) empty]
                            [else
                             (local [(define (find-map-local b)
                                       (find-map b loc0))]
                               (map find-map-local lob))]))

                    (define (fn-for-loc todo visited)
                      (cond [(empty? todo) false]
                            [else
                             (fn-for-comb (first todo)
                                          (rest todo)
                                          visited)]))]
              (if (string=? (comb-type c) s)
                  false
                  (fn-for-comb c empty empty))))


          (define (find-map b loc)
            (cond [(empty? loc) (error "whoops")]
                  [else
                   (if  (string=? (bag-colour b) (comb-type (first loc)))
                        (first loc)
                        (find-map b (rest loc)))]))]
    (fn-for-loc loc0 0)))


;; Solution for Part 2




(check-expect (problem2 LOC "shiny gold") 32)
(check-expect (problem2 LOC "faded blue") 0)
(check-expect (problem2 LOC "dark olive") 7)

;(define (problem2 loc) 0) ;stub

(define (problem2 loc0 colour)
  (local [(define (find-map s loc)
            (cond [(empty? loc) (error "whoops")]
                  [else
                   (if  (string=? s (comb-type (first loc)))
                        (first loc)
                        (find-map s (rest loc)))]))

          (define (fn-for-comb c)
           (fn-for-lob (comb-bags c)))

          (define (fn-for-lob lob)
            (cond [(empty? lob) 0]
                  [else
                   (+ (fn-for-bag (first lob))
                      (fn-for-lob (rest lob)))]))

          (define (fn-for-bag b)
            (local [(define n (fn-for-comb (find-map (bag-colour b) loc0)))]
              (+ (bag-amount b)
                            (* (bag-amount b) n))))]
    (fn-for-comb (find-map colour loc0))))

;;(problem1 (los->loc file))
(problem2 (los->loc file) "shiny gold")

