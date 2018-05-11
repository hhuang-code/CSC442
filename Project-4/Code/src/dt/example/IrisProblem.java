package dt.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import dt.core.DecisionTree;
import dt.core.DecisionTreeLearner;
import dt.core.Domain;
import dt.core.Example;
import dt.core.Problem;
import dt.core.Variable;
import dt.core.YesNoDomain;
import dt.util.ArraySet;

/**
 * The restaurant WillWait example from AIMA Section 18.3, data
 * from file WillWait-data.txt.
 * <p>
 * Run and pass dataset filename on cmd-line.
 */
public class IrisProblem extends Problem {
	
	public IrisProblem() {
		super();
		this.inputs.add(new Variable("Sepal length", new Domain("S", "MS", "L","ML")));
		this.inputs.add(new Variable("Sepal width", new Domain("S", "MS", "L","ML")));
		this.inputs.add(new Variable("Petal length", new Domain("S", "MS", "L","ML")));
		this.inputs.add(new Variable("Petal width", new Domain("S", "MS", "L","ML")));
		// Output variable
		this.output = new Variable("Class", new Domain("Iris-virginica","Iris Setosa","Iris Versicolour"));
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		Problem problem = new IrisProblem();
		problem.dump();
		Set<Example> examples = problem.readExamplesFromCSVFile(new File(args[0]));
		for (Example e : examples) {
			System.out.println(e);
		}
		
		String mode = args[1];
		if (mode.equals("train")) {
			DecisionTree tree = new DecisionTreeLearner(problem).learn(examples);
			System.out.println("start");
			tree.dump();
			System.out.println("dump");
			tree.test(examples);
			System.out.println("finish");
		} else if (mode.equals("test")) {
			System.out.println("Please type the percentage of data used for test (0 < your input < 1): ");
			Scanner scan = new Scanner( System.in );
			double split = scan.nextDouble();
			
			if(split <= 0 || split >= 1) {
		        throw new IllegalArgumentException("Split should be strictly larger than 0 and smaller than 1.");
		    } 
			
			// Shuffle all examples
			List<Example> examples_list = new ArrayList<Example>(examples);
			Collections.shuffle(examples_list);
			
			// Split all examples into training and testing parts
			List<Example> train_examples_list = examples_list.subList(0, (int)(examples.size() * (1 - split)));
			List<Example> test_examples_list = examples_list.subList((int)(examples.size() * (1 - split)), examples.size());
			
			Set<Example> train_examples = new ArraySet<Example>(train_examples_list);
			Set<Example> test_examples = new ArraySet<Example>(test_examples_list);
			
			DecisionTree tree = new DecisionTreeLearner(problem).learn(train_examples);
			System.out.println("start");
			tree.dump();
			System.out.println("dump");
			tree.test(train_examples);
			System.out.println("training set\n");
			tree.test(test_examples);
			System.out.println("testing set");
			System.out.println("finish");
		}
	}

}

