;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day3) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
(require 2htdp/batch-io)

(define file (read-lines "data.txt"))





;; solution for part 1
(check-expect (problem1 (list "..##......."
                              "#...#...#.."
                              ".#....#..#."
                              "..#.#...#.#"
                              ".#...##..#."
                              "..#.##....."
                              ".#.#.#....#"
                              ".#........#"
                              "#.##...#..."
                              "#...##....#"
                              ".#..#...#.#"))
              7)

;(define (problem1 los) 0) ;

(define (problem1 los)
  ;; acc is Natural ; list of trees collided with
  ;; x is Natural ; position x on the field.
  ;; CONSTRANT: [0, (sub1 (string-length (first los)))]
  ;; y is Natural ; position y to move.
  (local [(define (fn-for-los los acc x y dirx diry)
            (cond [(empty? los) acc]
                  [else
                   (local [(define filterx
                             (+ x dirx))
                           (define nextx
                             (if (>= filterx (string-length (first los)))
                                 (- filterx (string-length (first los)))
                                 filterx))
                           (define nexty
                             (sub1 diry))
                           (define cur (string-ith (first los)
                                                   x))]
                     (if (= y 0)
                         (fn-for-los (rest los)
                                     (if (string=? cur "#")
                                         (add1 acc)
                                         acc)
                                     nextx
                                     nexty
                                     dirx
                                     diry)
                         (fn-for-los (rest los)
                                     acc
                                     x
                                     (sub1 y)
                                     dirx
                                     diry)))]))]
(fn-for-los los 0 0 0 3 1)))


;; solution for part 2
(check-expect (problem2 (list "..##......."
                              "#...#...#.."
                              ".#....#..#."
                              "..#.#...#.#"
                              ".#...##..#."
                              "..#.##....."
                              ".#.#.#....#"
                              ".#........#"
                              "#.##...#..."
                              "#...##....#"
                              ".#..#...#.#"))
              336)

;(define (problem2 los) 0) ;

(define (problem2 los)
  ;; acc is Natural ; list of trees collided with
  ;; x is Natural ; position x on the field.
  ;; CONSTRANT: [0, (sub1 (string-length (first los)))]
  ;; y is Natural ; position y to move.
  (local [(define (fn-for-los los acc x y dirx diry)
            (cond [(empty? los) acc]
                  [else
                   (local [(define filterx
                             (+ x dirx))
                           (define nextx
                             (if (>= filterx (string-length (first los)))
                                 (- filterx (string-length (first los)))
                                 filterx))
                           (define nexty
                             (sub1 diry))
                           (define cur (string-ith (first los)
                                                   x))]
                     (if (= y 0)
                         (fn-for-los (rest los)
                                     (if (string=? cur "#")
                                         (add1 acc)
                                         acc)
                                     nextx
                                     nexty
                                     dirx
                                     diry)
                         (fn-for-los (rest los)
                                     acc
                                     x
                                     (sub1 y)
                                     dirx
                                     diry)))]))]
(* (fn-for-los los 0 0 0 1 1)
   (fn-for-los los 0 0 0 3 1)
   (fn-for-los los 0 0 0 5 1)
   (fn-for-los los 0 0 0 7 1)
   (fn-for-los los 0 0 0 1 2))))

(problem1 file)
(problem2 file)
