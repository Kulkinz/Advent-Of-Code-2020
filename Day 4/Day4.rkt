;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day4) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
(require 2htdp/batch-io)
(require racket/string)
(require racket/base)
 
(define file (read-lines "data.txt"))

(define-struct passport (byr iyr eyr hgt hcl ecl pid cid))
;; Passport is (make-passport String or empty ; Birth Year
;;                            String or empty ; Issue Year
;;                            String or empty ; Expiration Year
;;                            String or empty ; Height (in inches)
;;                            String or empty ; Hair Colour
;;                            String or empty ; Eye Colour
;;                            String or empty ; Passport ID
;;                            String or empty) ; Country ID

#;
(define (fn-for-lop lop)
  (local [(define (fn-for-lop lop)
            (cond [(empty? lop) (...)]
                  [else
                   (... (fn-for-passport (first lop))
                        (fn-for-lop (rest lop)))]))

          (define (fn-for-passport p)
            (local [(define byr (passport-byr p))
                    (define iyr (passport-iyr p))
                    (define eyr (passport-eyr p))
                    (define hgt (passport-hgt p))
                    (define hcl (passport-hcl p))
                    (define ecl (passport-ecl p))
                    (define pid (passport-pid p))
                    (define cid (passport-cid p))]
              (... (cond [(empty? byr) (...)]
                         [else (... byr)])
                   (cond [(empty? iyr) (...)]
                         [else (... iyr)])
                   (cond [(empty? eyr) (...)]
                         [else (... eyr)])
                   (cond [(empty? hgt) (...)]
                         [else (... hgt)])
                   (cond [(empty? hcl) (...)]
                         [else (... hcl)])
                   (cond [(empty? ecl) (...)]
                         [else (... ecl)])
                   (cond [(empty? pid) (...)]
                         [else (... pid)])
                   (cond [(empty? cid) (...)]
                         [else (... cid)]))))]
    (fn-for-lop lop)))

;; ---------------------------------------------------------------------


(define (seperate los)
  ;; acc is String ; the current passport
  ;; los2 is (listof String) ; the list of passports in string
  (local [(define (fn-for-los los acc los2)
            (cond [(empty? los) (cons acc los2)]
                  [(string=? (first los) "") (fn-for-los (rest los) ""
                                                         (cons acc los2))]
                  [else
                   (fn-for-los (rest los)
                               (string-trim (string-append acc " " (first los)))
                               los2)]))]
    (fn-for-los los "" empty)))

;; ---------------------------------------------------------------------

(define (los->lop los)
  (local [(define (create-passport s)
            (set-passport (string-split s)))]
    
    (map create-passport los)))

(define (set-passport los)
  ;; acc is Passport ; the passport :3
  (local [(define (fn-for-los los acc)
            (cond [(empty? los) acc]
                  [else
                   (fn-for-los (rest los)
                               (update-passport (first los) acc))]))]
    (fn-for-los los (make-passport empty
                                   empty
                                   empty
                                   empty
                                   empty
                                   empty
                                   empty
                                   empty))))

(define (update-passport s p)
  (local [(define dtl
            (first (string-split (string-replace s ":" " "))))
          (define info
            (second (string-split (string-replace s ":" " "))))
          (define length
            (string-length info))]
    (cond [(string=? dtl "byr") (make-passport
                                 info
                                 (passport-iyr p)
                                 (passport-eyr p)
                                 (passport-hgt p)
                                 (passport-hcl p)
                                 (passport-ecl p)
                                 (passport-pid p)
                                 (passport-cid p))]
          [(string=? dtl "iyr") (make-passport
                                 (passport-byr p)
                                 info
                                 (passport-eyr p)
                                 (passport-hgt p)
                                 (passport-hcl p)
                                 (passport-ecl p)
                                 (passport-pid p)
                                 (passport-cid p))]
          [(string=? dtl "eyr") (make-passport
                                 (passport-byr p)
                                 (passport-iyr p)
                                 info
                                 (passport-hgt p)
                                 (passport-hcl p)
                                 (passport-ecl p)
                                 (passport-pid p)
                                 (passport-cid p))]
          [(string=? dtl "hgt") (make-passport
                                 (passport-byr p)
                                 (passport-iyr p)
                                 (passport-eyr p)
                                 info
                                 (passport-hcl p)
                                 (passport-ecl p)
                                 (passport-pid p)
                                 (passport-cid p))]
          [(string=? dtl "hcl") (make-passport
                                 (passport-byr p)
                                 (passport-iyr p)
                                 (passport-eyr p)
                                 (passport-hgt p)
                                 info
                                 (passport-ecl p)
                                 (passport-pid p)
                                 (passport-cid p))]
          [(string=? dtl "ecl") (make-passport
                                 (passport-byr p)
                                 (passport-iyr p)
                                 (passport-eyr p)
                                 (passport-hgt p)
                                 (passport-hcl p)
                                 info
                                 (passport-pid p)
                                 (passport-cid p))]
          [(string=? dtl "pid") (make-passport
                                 (passport-byr p)
                                 (passport-iyr p)
                                 (passport-eyr p)
                                 (passport-hgt p)
                                 (passport-hcl p)
                                 (passport-ecl p)
                                 info
                                 (passport-cid p))]
          [else
           (make-passport
            (passport-byr p)
            (passport-iyr p)
            (passport-eyr p)
            (passport-hgt p)
            (passport-hcl p)
            (passport-ecl p)
            (passport-pid p)
            info)])))

;; -----------------------------------------------------------------------






;; solution for part 1

(check-expect (problem1 (list (make-passport 1937 2017 2020 72.0472 "fffffd"
                                             "gry" 860033327 147)
                              (make-passport 1929 2013 2023 empty "cfa07d"
                                             "amb" 028048884 350)
                              (make-passport 1931 2013 2024 70.4724 "ae17e1"
                                             "brn" 760753108 empty)
                              (make-passport empty 2011 2025 59 "cfa07d"
                                             "brn" 166559648 empty)))
              2)

;(define (problem1 lop) 0) ;stub

(define (problem1 lop)
  ;; acc is Natural ; amount of passports that match the criteria
  (local [(define (fn-for-lop lop acc)
            (cond [(empty? lop) acc]
                  [else
                   (fn-for-lop (rest lop)
                               (if (fn-for-passport (first lop))
                                   (add1 acc)
                                   acc))]))

          (define (fn-for-passport p)
            (local [(define byr (passport-byr p))
                    (define iyr (passport-iyr p))
                    (define eyr (passport-eyr p))
                    (define hgt (passport-hgt p))
                    (define hcl (passport-hcl p))
                    (define ecl (passport-ecl p))
                    (define pid (passport-pid p))
                    (define cid (passport-cid p))]
              (and (not (empty? byr))
                   (not (empty? iyr))
                   (not (empty? eyr))
                   (not (empty? hgt))
                   (not (empty? hcl))
                   (not (empty? ecl))
                   (not (empty? pid)))))]
    (fn-for-lop lop 0)))


;; solution for part 2

(check-expect (problem2 (list (make-passport "1926" "2018" "1972" "170"
                                             "#18171d" "amb" "186cm" "100")
                              (make-passport "1946" "2019" "1967" "170cm"
                                             "#602927" "grn" "012533040" empty)
                              (make-passport "1992" "2012" "2020" "182cm"
                                             "dab227" "brn" "021572410" "277")
                              (make-passport "2007" "2023" "2038" "59cm"
                                             "74454a" "zzz" "3556412378" empty))
                        )
              0)

(check-expect (problem2 (list (make-passport "1980" "2012" "2030" "74in"
                                             "#623a2f" "grn" "087499704" empty)
                              (make-passport "1989" "2014" "2029" "165cm"
                                             "#a97842" "blu" "896056539" "129")
                              (make-passport "2001" "2015" "2022" "164cm"
                                             "#888785" "hzl" "545766238" "88")
                              (make-passport "1944" "2010" "2021" "158cm"
                                             "#b6652a" "blu" "093154719" empty))
                        )
              4)

;(define (problem2 lop) 0) ;stub

(define (problem2 lop)
  ;; acc is Natural ; amount of passports that match the criteria
  (local [(define (fn-for-lop lop acc)
            (cond [(empty? lop) acc]
                  [else
                   (fn-for-lop (rest lop) (if (fn-for-passport (first lop))
                                              (add1 acc)
                                              acc))]))

          (define (fn-for-passport p)
            (local [(define byr (passport-byr p))
                    (define iyr (passport-iyr p))
                    (define eyr (passport-eyr p))
                    (define hgt (passport-hgt p))
                    (define hcl (passport-hcl p))
                    (define ecl (passport-ecl p))
                    (define pid (passport-pid p))
                    (define cid (passport-cid p))]
              (and (cond [(empty? byr) false]
                         [else
                          (<= 1920 (string->number byr) 2002)])
                   (cond [(empty? iyr) false]
                         [else
                          (<= 2010 (string->number iyr) 2020)])
                   (cond [(empty? eyr) false]
                         [else
                          (<= 2020 (string->number eyr) 2030)])
                   (cond [(empty? hgt) false]
                         [else
                          (local [(define unit
                                    (substring hgt
                                               (- (string-length hgt) 2)))
                                  (define amount
                                    (substring hgt
                                               0
                                               (- (string-length hgt) 2)))]
                            (if (string=?
                                 unit
                                 "cm")
                                (<= 150
                                    (string->number
                                     amount)
                                    193)
                                (if (string=?
                                     unit
                                     "in")
                                    (<= 59
                                        (string->number
                                         amount)
                                        76)
                                    false)))])
                   (cond [(empty? hcl) false]
                         [else
                          (if (string=? (substring hcl 0 1) "#")
                              (= 6
                                 (string-length
                                  (first (regexp-match
                                          #rx"([a-f0-9]*)"
                                          (substring hcl 1)))))
                              false)])
                   (cond [(empty? ecl) false]
                         [else
                          (or (string=? ecl "amb")
                              (string=? ecl "blu")
                              (string=? ecl "brn")
                              (string=? ecl "gry")
                              (string=? ecl "grn")
                              (string=? ecl "hzl")
                              (string=? ecl "oth"))])
                   (cond [(empty? pid) false]
                         [else
                          (= (string-length pid) 9)]))))]
    (fn-for-lop lop 0)))

(problem1 (los->lop (seperate file)))
(problem2 (los->lop (seperate file)))
