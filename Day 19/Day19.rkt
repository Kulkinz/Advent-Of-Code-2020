;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Day19) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
(require 2htdp/batch-io)
(require racket/string)

(define file1 (read-lines "data1.txt"))
(define file2 (read-lines "data2.txt"))

file1
file2

(define rules
  (map (lambda (s) (second (string-split s ": "))) file1))

(list-ref rules 0)

;; Takes a string, and runs through it until no more operations can be done

(check-expect (make-single (list 

(define (make-single rules) "")