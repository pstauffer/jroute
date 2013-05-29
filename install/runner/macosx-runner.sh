#!/bin/bash

_JAR=jroute-1.0-SNAPSHOT-jar-with-dependencies.jar
_HEAPSIZE=512m

java -Djava.library.path=../macosx/ -Xmx${_HEAPSIZE} -jar ../${_JAR}