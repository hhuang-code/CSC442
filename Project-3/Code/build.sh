#!/bin/bash

if [ ! -d "./bin" ]
then
	mkdir ./bin	
fi
javac -d ./bin $(find "./src" -name "*.java")
cp -R ./src/bn/examples ./bin/bn
