#!/bin/bash

if [ ! -d "./bin" ]
then
	mkdir ./bin	
fi
javac -d ./bin $(find "./src" -name "*.java")
cd ./src
find . -name '*.txt' | cpio -pdm ./../bin
