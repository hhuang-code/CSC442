#!/bin/bash

if [ ! -d "./BasicTTT/bin" ]
then
	mkdir ./BasicTTT/bin	
fi
javac -d ./BasicTTT/bin $(find "./BasicTTT/src" -name "*.java")


if [ ! -d "./AdvancedTTT/bin" ]
then
	mkdir ./AdvancedTTT/bin	
fi
javac -d ./AdvancedTTT/bin $(find "./AdvancedTTT/src" -name "*.java")

if [ ! -d "./MoreAdvancedTTT/bin" ]
then
	mkdir ./MoreAdvancedTTT/bin	
fi
javac -d ./MoreAdvancedTTT/bin $(find "./MoreAdvancedTTT/src" -name "*.java")
