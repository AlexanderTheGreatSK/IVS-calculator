#!/bin/sh

#Check if an amount of numbers is given
if [ -z $1 ]; then
  printf "%s " "Please give me an argument on how many numbers should I"
  printf "%s\n" "generate."
  exit 0
fi

DIR="numbers/"

#Generate numbers to a variable NUMS
i=0
while [ "$i" -lt "$1" ]; do
  NUMS="$NUMS"$(printf "%s" $(shuf -i 0-1000000 -n 1))"\n"
  i=$(($i+1))
done

OUTFILE="$DIR""generated_numbers_""$1"

#DEBUGGING: print the generated numbers
#printf "Generated numbers:\n%b" "$NUMS"
mkdir -p "$DIR"; printf "%b" "$NUMS" > "$OUTFILE"
