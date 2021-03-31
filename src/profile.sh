#!/bin/bash

#Check if an amount of numbers is given
if [ -z $1 ]; then
  printf "%s " "Please give me an argument on how many numbers should I"
  printf "%s\n" "calculate the deviation of"
  exit 0
fi

#Generate numbers to a variable NUMS
RANDOM=$$
i=0
while [ "$i" -lt "$1" ]; do
  NUMS="$NUMS"$(printf "%s" "$RANDOM")"\n"
  i=$(($i+1))
done

  #DEBUGGING: print the generated numbers
  printf "Generated numbers:\n%b" "$NUMS"
  #printf "%b" "$NUMS" > profiling_numbers

RUNCALC="cd ../build/; java com.company.Main"

N=$1

AVG_SUM_EXPR=$(printf "%b" "$NUMS" | tr '\n' '+' | rev | cut -c 2- | rev)
AVG_SUM=$(eval "$RUNCALC \"$AVG_SUM_EXPR\"")
AVG_DIV_EXPR=$(printf "%b/%b" "$AVG_SUM" "$N")
AVG=$(eval "$RUNCALC \"$AVG_DIV_EXPR\"")
  #DEBUGGING
  #printf "Expression to calculate the sum: %s\n" "$AVG_SUM_EXPR"
  #printf "Expression to divide the sum: %s\n" "$AVG_DIV_EXPR"
  #printf "Average: %s\n" "$AVG"

i=1
NUMS=$(printf "%b" "$NUMS" | tr '\n' ';')
while [ "$i" -lt $(($N+1)) ]; do
  DIST_DIFF_EXPR=$(printf "%s" "$NUMS" | cut -d ';' -f "$i" )"-""$AVG"
    #DEBUGGING
    #printf "Expression to calculate the difference: %s\n" "$DIST_DIFF_EXPR"
  DIST_DIFF=$(eval "$RUNCALC \"$DIST_DIFF_EXPR\"")
    #DEBUGGING
    #printf "The result of the last expression: %s\n" "$DIST_DIFF"
  DIST_SUM_EXPR="$DIST_SUM_EXPR+"$(printf "pow(%s, 2)" "$DIST_DIFF")
  i=$(($i+1))
done
NUMS=$(printf "%s" "$NUMS" | tr ';' '\n')
DIST_SUM_EXPR=$(printf "%s" "$DIST_SUM_EXPR" | cut -c 2-)
DIST_SUM=$(eval "$RUNCALC \"$DIST_SUM_EXPR\"")
  #DEBUGGING
  #printf "Expression to calculate the sum of distances: %s\n" "$DIST_SUM_EXPR"
  #printf "Sum of distances: %s\n" "$DIST_SUM"

N_MINUS_ONE=$(eval "$RUNCALC \"$N-1\"")
DIST_SUM_DIVIDED_EXPR=$(printf "%s/%s" "$DIST_SUM" "$N_MINUS_ONE")
DIST_SUM_DIVIDED=$(eval "$RUNCALC \"$DIST_SUM_DIVIDED_EXPR\"")
  #DEBUGGING
  #printf "Expression to divide the sum by N-1: %s\n" "$DIST_SUM_DIVIDED_EXPR"
  #printf "Distances sum divided by N-1: %s\n" "$DIST_SUM_DIVIDED"

RESULT_EXPR=$(printf "root(%s, 2)" "$DIST_SUM_DIVIDED")
RESULT=$(eval "$RUNCALC \"$RESULT_EXPR\"")
  #DEBUGGING
  #printf "Expression to calculate the result: %s\n" "$RESULT_EXPR"
  printf "The result: %s\n" "$RESULT"
