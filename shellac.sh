#!/bin/bash

PORT=${SHELLAC_PORT:-5885}

(
  # the first arg is the command name, the rest are actual arguments
  ARG_COUNT=$(( $#-1 ))
  
  # print the command name
  echo "$1" &&

  # print out the number of arguments
  echo "$ARG_COUNT" &&

  # print out arguments, removing newlines
  for i in "${@:2}"
  do
    echo "$i" | (tr '\n' ' ' && echo)
  done &&

  # print out stdin
  cat
) | nc localhost $PORT

