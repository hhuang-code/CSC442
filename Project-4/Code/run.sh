#!/bin/bash

cd ./bin
while true; do
    # Read user's choice
    echo -e '\nWhich task do you want to run? (0 for exit)'
    echo '1 - Decision Tree Learning'
    echo '2 - Perceptron Classifier'
    echo '3 - Logistic Classifier'
    echo '0 - Exit'
    read choice
    echo -e '\n'
    case $choice in
        # Decision tree learning
        '1' )
            echo 'Please choose input file:'
            echo '1 - WillWait-data'
            echo '2 - house-votes-84-data'
            echo '3 - iris-discrete-data'
            read filename
            echo -e '\n'
            case $filename in
                # WillWait problem
                '1' )
                    java dt.example.WillWaitProblem dt/example/WillWait-data.txt;;
                # HouseVote problem
	            '2' )
	                echo 'Please choose mode: 1 - train; 2 - test'
	                read mode
	                echo -e '\n'
	                case $mode in
	                    '1' ) 
	                        java dt.example.HouseVoteProblem dt/example/house-votes-84.data.mod.txt 'train';;
	                    '2' ) 
	                        java dt.example.HouseVoteProblem dt/example/house-votes-84.data.mod.txt 'test';;
	                    *   )
	                        echo 'No such a mode! Please re-enter your choice.';;
	                esac;;
	             # Iris problem
	            '3' )
	                echo 'Please choose mode: 1 - train; 2 - test'
	                read mode
	                echo -e '\n'
	                case $mode in
	                    '1' )
	                        java dt.example.IrisProblem dt/example/iris.data.discrete.txt 'train';;
	                    '2' )
	                        java dt.example.IrisProblem dt/example/iris.data.discrete.txt 'test';;
	                    *   )
	                        echo 'No such a mode! Please re-enter your choice.';;
	                esac;;
                *   )
                    echo 'No such a test case! Please re-enter your choice.';;
            esac;;
         # Perceptron classifier
        '2' )
            echo 'Please choose input file:'
            echo '1 - earthquake-clean-data'
            echo '2 - earthquake-noisy-data'
            echo '3 - house-votes-84-data'
            read filename
            echo 'Please input the number of iterations (steps):'
            read step
            echo 'Please input learning rate alpha: 0 - decay; other positive number - fixed'
            read alpha
            echo 'Please choose mode: 1 - train; 2 - test'
            read mode
            echo 'Please input the bound of initial weights (e.g., 0.5 means weights will be initialized between -0.5 ~ 0.5; 0 means weights will be set to 0): '
            read bound
            case $filename in
                # Earthquake clean data
                '1' )
                    case $mode in
                        '1' )
                            java lc.example.PerceptronClassifierTest lc/example/earthquake-clean.data.txt ${step} ${alpha} 'train' ${bound};;  
                        '2' )
                            java lc.example.PerceptronClassifierTest lc/example/earthquake-clean.data.txt ${step} ${alpha} 'test' ${bound};; 
                        *   )
		                    echo 'No such a mode! Please re-enter your choice.';;  
                    esac;;
                # Earchquake noisy data
                '2' )
                    case $mode in
                        '1' )
                            java lc.example.PerceptronClassifierTest lc/example/earthquake-noisy.data.txt ${step} ${alpha} 'train' ${bound};;  
                        '2' )
                            java lc.example.PerceptronClassifierTest lc/example/earthquake-noisy.data.txt ${step} ${alpha} 'test' ${bound};; 
                        *   )
		                    echo 'No such a mode! Please re-enter your choice.';;  
                    esac;;
                # HouseVote data
                '3' )
                    case $mode in
                        '1' )
                            java lc.example.PerceptronClassifierTest lc/example/house-votes-84.data.num.txt ${step} ${alpha} 'train' ${bound};;  
                        '2' )
                            java lc.example.PerceptronClassifierTest lc/example/house-votes-84.data.num.txt ${step} ${alpha} 'test' ${bound};; 
                        *   )
		                    echo 'No such a mode! Please re-enter your choice.';;  
                    esac;;
                *   )
                    echo 'No such a test case! Please re-enter your choice.';;
            esac;;
        # Logistic classifier
        '3' )
            echo 'Please choose input file:'
            echo '1 - earthquake-clean-data'
            echo '2 - earthquake-noisy-data'
            echo '3 - house-votes-84-data'
            read filename
            echo 'Please input the number of iterations (steps):'
            read step
            echo 'Please input learning rate alpha: 0 - decay; other positive number - fixed'
            read alpha
            echo 'Please choose mode: 1 - train; 2 - test'
            read mode
            echo 'Please input the bound of initial weights (e.g., if you input 0.5, weights will be initialized between -0.5 ~ 0.5): '
            read bound
            case $filename in
                # Earthquake clean data
                '1' )
                    case $mode in
                        '1' )
                            java lc.example.LogisticClassifierTest lc/example/earthquake-clean.data.txt ${step} ${alpha} 'train' ${weight_init} ${bound};;  
                        '2' )
                            java lc.example.LogisticClassifierTest lc/example/earthquake-clean.data.txt ${step} ${alpha} 'test' ${weight_init} ${bound};; 
                        *   )
		                    echo 'No such a mode! Please re-enter your choice.';;  
                    esac;;
                # Earchquake noisy data
                '2' )
                    case $mode in
                        '1' )
                            java lc.example.LogisticClassifierTest lc/example/earthquake-noisy.data.txt ${step} ${alpha} 'train' ${weight_init} ${bound};;  
                        '2' )
                            java lc.example.LogisticClassifierTest lc/example/earthquake-noisy.data.txt ${step} ${alpha} 'test' ${weight_init} ${bound};; 
                        *   )
		                    echo 'No such a mode! Please re-enter your choice.';;  
                    esac;;
                # HouseVote data
                '3' )
                    case $mode in
                        '1' )
                            java lc.example.LogisticClassifierTest lc/example/house-votes-84.data.num.txt ${step} ${alpha} 'train' ${weight_init} ${bound};;  
                        '2' )
                            java lc.example.LogisticClassifierTest lc/example/house-votes-84.data.num.txt ${step} ${alpha} 'test' ${weight_init} ${bound};; 
                        *   )
		                    echo 'No such a mode! Please re-enter your choice.';;  
                    esac;;
                *   )
                    echo 'No such a test case! Please re-enter your choice.';;
            esac;;
        # Exit
        '0' )
            break;;
        *   )
            echo 'No such task! Please re-enter your choice.';;
    esac
done
