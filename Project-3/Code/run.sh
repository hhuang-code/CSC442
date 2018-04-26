#!/bin/bash

cd ./bin
while true; do
	# read user's choice
	echo -e '\nWhich task do you want to run? (0 for exit)'
	echo '1 - Exact Inference'
	echo '2 - Rejection Sampling'
	echo '3 - Likelihood Weighting'
	echo '4 - Gibbs Sampling'
	echo '0 - Exit'
	read choice
	echo -e '\n'
	case $choice in
		'1' )
			echo 'Which type of example do you want to run?'
			echo 'A - java ExactInferencer aima-alarm.xml B J true M true'
			echo 'B - java ExactInferencer aima-wet-grass.xml R S true'
			echo 'C - your custom example'
			read ctype
			case ${ctype^^} in
				'A' )
					java bn.sln.ExactInferencer aima-alarm.xml B J 'true' M 'true';;
				'B' )
					java bn.sln.ExactInferencer aima-wet-grass.xml R S 'true';;
				'C' )
					echo -e 'Please enter your command like A or B:'
					read custom_command
					custom_command=${custom_command//ExactInferencer/bn.sln.ExactInferencer}
					$custom_command;;
				* )
					echo 'No such an example! Let''s try again.';;
			esac;;
		'2' )
			echo 'Which type of example do you want to run?'
			echo 'A - java RSampleInferencer 10000 aima-alarm.xml B J true M true'
			echo 'B - java RSampleInferencer 10000 alarm.bif LVEDVOLUME HYPOVOLEMIA FALSE LVFAILURE TRUE'
			echo 'C - your custom example'
			read ctype
			case ${ctype^^} in
				'A' )
					java bn.sln.RSampleInferencer 10000 aima-alarm.xml B J 'true' M 'true';;
				'B' )
					java bn.sln.RSampleInferencer 10000 alarm.bif LVEDVOLUME HYPOVOLEMIA FALSE LVFAILURE TRUE;;
				'C' )
					echo -e 'Please enter your command like A or B:'
					read custom_command
					custom_command=${custom_command//RSampleInferencer/bn.sln.RSampleInferencer}
					echo -e ''
					$custom_command;;
				* )
					echo 'No such an example! Let''s try again.';;
			esac;;
		'3' )
			echo 'Which type of example do you want to run?'
			echo 'A - java LSampleInferencer 10000 aima-alarm.xml B J true M true'
			echo 'B - java LSampleInferencer 10000 alarm.bif LVEDVOLUME HYPOVOLEMIA FALSE LVFAILURE TRUE'
			echo 'C - your custom example'
			read ctype
			case ${ctype^^} in
				'A' )
					java bn.sln.LSampleInferencer 10000 aima-alarm.xml B J 'true' M 'true';;
				'B' )
					java bn.sln.LSampleInferencer 10000 alarm.bif LVEDVOLUME HYPOVOLEMIA FALSE LVFAILURE TRUE;;
				'C' )
					echo -e 'Please enter your command like A or B:'
					read custom_command
					custom_command=${custom_command//LSampleInferencer/bn.sln.LSampleInferencer}
					echo -e ''
					$custom_command;;
				* )
					echo 'No such an example! Let''s try again.';;
			esac;;
		'4' )
			echo 'Which type of example do you want to run?'
			echo 'A - java GSampleInferencer 10000 aima-alarm.xml B J true M true'
			echo 'B - java GSampleInferencer 10000 alarm.bif LVEDVOLUME HYPOVOLEMIA FALSE LVFAILURE TRUE'
			echo 'C - your custom example'
			read ctype
			case ${ctype^^} in
				'A' )
					java bn.sln.GSampleInferencer 10000 aima-alarm.xml B J 'true' M 'true';;
				'B' )
					java bn.sln.GSampleInferencer 10000 alarm.bif LVEDVOLUME HYPOVOLEMIA FALSE LVFAILURE TRUE;;
				'C' )
					echo -e 'Please enter your command like A or B:'
					read custom_command
					custom_command=${custom_command//GSampleInferencer/bn.sln.GSampleInferencer}
					echo -e ''
					$custom_command;;
				* )
					echo 'No such an example! Let''s try again.';;
			esac;;
		'0' )
			break;;
		* )
			echo 'No such a task! Please re-enter your choice.';;
	esac
done

