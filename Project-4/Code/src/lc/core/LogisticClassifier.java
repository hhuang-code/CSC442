package lc.core;

import math.util.VectorOps;
import java.lang.Math;

public class LogisticClassifier extends LinearClassifier {
	
	public LogisticClassifier(int ninputs) {
		super(ninputs);
	}
	
	public LogisticClassifier(int ninputs, double bound) {
		super(ninputs, bound);
	}
	
	/**
	 * A LogisticClassifier uses the logistic update rule
	 * (AIMA Eq. 18.8): w_i \leftarrow w_i+\alpha(y-h_w(x)) \times h_w(x)(1-h_w(x)) \times x_i 
	 */
	public void update(double[] x, double y, double alpha) {
	    // Must be implemented by you
		for(int i=0;i<this.weights.length;i++) {
			double hw = threshold(VectorOps.dot(this.weights, x));
			this.weights[i]+=alpha*(y-hw)*x[i]*hw*(1-hw);
		}
	}
	

	
	/**
	 * A LogisticClassifier uses a 0/1 sigmoid threshold at z=0.
	 */
	public double threshold(double z) {
	    // Must be implemented by you
		return 1/(1+Math.exp(-z));
	}
	
	
}
