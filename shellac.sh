#!/bin/bash

PORT=${SHELLAC_PORT:-5885}

# determine whether a given command will require stdin
USE_STDIN=$(printf "check-stdin\n1\n$1" | nc localhost $PORT)


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

  # if stdin is needed cat it, otherwise just echo the commands above
  if [ "$USE_STDIN" = true ]; then
    cat
  else
    echo
  fi

) | nc localhost $PORT

