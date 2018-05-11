package lc.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import lc.core.Example;
import lc.core.LearningRateSchedule;
import lc.core.PerceptronClassifier;
import lc.display.ClassifierDisplay;

public class PerceptronClassifierTest {

	/**
	 * Train a PerceptronClassifier on a file of examples and
	 * print its accuracy after each training step.
	 */
	@SuppressWarnings("resource")
	public static void main(String[] argv) throws IOException {
		if (argv.length < 3) {
			System.out.println("usage: java PerceptronClassifierTest data-filename nsteps alpha");
			System.out.println("       specify alpha=0 to use decaying learning rate schedule (AIMA p725)");
			System.exit(-1);
		}
		
		String filename = argv[0];
		int nsteps = Integer.parseInt(argv[1]);
		double alpha = Double.parseDouble(argv[2]);
		System.out.println("filename: " + filename);
		System.out.println("nsteps: " + nsteps);
		System.out.println("alpha: " + alpha);
		
		// bound to weight initialization
		double bound = Double.parseDouble(argv[4]);
		
		// "train" or "test" mode
		String mode = argv[3];
		if (mode.equals("train")) {
			ClassifierDisplay display = new ClassifierDisplay("PerceptronClassifier: " + filename);
			List<Example> examples = Data.readFromFile(filename);
			int ninputs = examples.get(0).inputs.length; 
			
			PerceptronClassifier classifier = null;
			 
			if (bound == 0.0) {
				classifier = new PerceptronClassifier(ninputs) {
					public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
						double accuracy = accuracy(examples);
						System.out.println(stepnum + "\t" + accuracy);
						display.addPoint(stepnum / (double)nsteps, accuracy);
					}
				};
			} else {
				classifier = new PerceptronClassifier(ninputs, bound) {
					public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
						double accuracy = accuracy(examples);
						System.out.println(stepnum + "\t" + accuracy);
						display.addPoint(stepnum / (double)nsteps, accuracy);
					}
				};
			}
			
			if (alpha > 0) {
				classifier.train(examples, nsteps, alpha);
			} else {
				classifier.train(examples, nsteps, new LearningRateSchedule() {
					public double alpha(int t) { return 1000.0/(1000.0+t); }
				});
			}
		} else if (mode.equals("test")) {
			System.out.println("Please type the percentage of data used for test (0 < your input < 1): ");
			Scanner scan = new Scanner( System.in );
			double split = scan.nextDouble();
			
			if(split <= 0 || split >= 1) {
		        throw new IllegalArgumentException("Split should be strictly larger than 0 and smaller than 1.");
		    } 
			
			ClassifierDisplay train_display = new ClassifierDisplay("PerceptronClassifier: " + filename + " - train");
			ClassifierDisplay test_display = new ClassifierDisplay("PerceptronClassifier: " + filename + " - test");
			List<Example> examples = Data.readFromFile(filename);
			int ninputs = examples.get(0).inputs.length; 
			
			PerceptronClassifier classifier = null;
			
			if (bound == 0.0) {
				classifier = new PerceptronClassifier(ninputs) {
					public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
						double accuracy = accuracy(examples);
						System.out.println("train: " + stepnum + "\t" + accuracy);
						train_display.addPoint(stepnum/(double)nsteps, accuracy);
					}
					
					public void testingReport(List<Example> examples, int stepnum, int nsteps) {
						double accuracy = accuracy(examples);
						System.out.println("test:  " + stepnum + "\t" + accuracy);
						test_display.addPoint(stepnum/(double)nsteps, accuracy);
					}
				};
			} else {
				classifier = new PerceptronClassifier(ninputs) {
					public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
						double accuracy = accuracy(examples);
						System.out.println("train: " + stepnum + "\t" + accuracy);
						train_display.addPoint(stepnum/(double)nsteps, accuracy);
					}
					
					public void testingReport(List<Example> examples, int stepnum, int nsteps) {
						double accuracy = accuracy(examples);
						System.out.println("test:  " + stepnum + "\t" + accuracy);
						test_display.addPoint(stepnum/(double)nsteps, accuracy);
					}
				};
			}
			
			if (alpha > 0) {
				classifier.test(examples, nsteps, alpha, split);
			} else {
				classifier.test(examples, nsteps, new LearningRateSchedule() {
					public double alpha(int t) { return 1000.0/(1000.0+t); }
				}, split);
			}
		}
	}

}
