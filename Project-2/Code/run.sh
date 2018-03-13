#!/bin/bash

echo 'Which example do you want to run?'
echo '1 - Modus Ponens'
echo '2 - Wumpus World (Simple)'
echo '3 - Horn Clauses'
echo '4 - Liars and Truth-tellers'
echo '5 - More Liars and Truth-tellers'
echo '6 - The Doors of Enlightenment'
echo '0 - Exit'

cd ./bin
while true; do
	# read user's choice
	echo -e '\nPlease enter your choice:'
	read choice
	echo -e '\n'
	case $choice in
		'1' )
			java pl.examples.ModusPonensKB;;
		'2' ) 
			java pl.examples.WumpusWorldKB;;
		'3' ) 
			java pl.examples.HornClauseKB;;
		'4' )
			java pl.examples.LiarTruthTellersKB;;
		'5' )
			java pl.examples.MoreLiarsTruthTellersKB;;
		'6' )
			java pl.examples.DoorEnlightKB;;
		'0' )
			break;;
		* )
			echo 'No such example! Please re-enter your choice.';;
	esac
done

