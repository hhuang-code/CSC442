import os
import sys


N = [200, 1000, 5000, 10000]

examples = [
' aima-alarm.xml B J true M true',
' aima-wet-grass.xml R S true',
' alarm.bif LVEDVOLUME HYPOVOLEMIA FALSE LVFAILURE TRUE',
' dog-problem.xml bowel-problem light-on false hear-bark true',
' insurance.bif Accident Age Adult Mileage TwentyThou MakeModel SportsCar'
]

tasks = [
'java bn.sln.ExactInferencer ',
'java bn.sln.RSampleInferencer ',
'java bn.sln.LSampleInferencer ',
'java bn.sln.GSampleInferencer '
]

os.chdir('./bin')

for example in examples:
	for task in tasks:
		if 'ExactInferencer' in task and '.bif' in example:
			continue
		for n in N:
			if 'ExactInferencer' in task:
				command = task + example
			else:
				command = task + str(n) + example
			print(command + '\n')
			os.system(command)
