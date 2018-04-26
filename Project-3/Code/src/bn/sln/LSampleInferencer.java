package bn.sln;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.Domain;
import bn.core.RandomVariable;
import bn.inference.Inferencer;
import bn.parser.BIFParser;
import bn.parser.XMLBIFParser;
import bn.util.Pair;

public class LSampleInferencer implements Inferencer {
	
	private int N;
	
	public LSampleInferencer() {
	}
	
	void setN(int n) {
		this.N = n;
	}
	
	Pair<Assignment, Double> weightedSample(BayesianNetwork bn, Assignment e) {
		Assignment x = e.copy();
		Double w = 1.0;
		
		// get all random variables in bn
		List<RandomVariable> rvs = bn.getVariableListTopologicallySorted();
		
		for (RandomVariable rv : rvs) {
			if (x.containsKey(rv)) {
				w *= bn.getProb(rv, x);
			} else {
				// generate a random variable between 0 and 1
				Random random = new java.util.Random();
				double randnum = random.nextDouble();
							
				Domain dm = rv.getDomain();
				// for each value in RV's domain
				for (Object val : dm) {	
					x.set(rv, val);
					randnum -= bn.getProb(rv, x);
					if (randnum <= 0) {
						break;
					}
				}
			}
		}
		
		return new Pair<Assignment, Double>(x, w);
	}
	
	@Override
	public Distribution ask(BayesianNetwork bn, RandomVariable X, Assignment e) {

		// store counts for each value
		Distribution W = new Distribution();
		for (Object val : X.getDomain()) {
			W.put(val, 0.0);
		}
		
		for(int j = 1; j <= N; j++) {
			Pair<Assignment, Double> ew = weightedSample(bn, e);
			W.put(ew.getLeft().get(X), W.get(ew.getLeft().get(X)) + ew.getRight());
		}
		
		W.normalize();
		
		return W;
		
	}
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

		//Sample size
		int N = Integer.parseInt(args[0]);
		
		// load bn xml or bif		
		BayesianNetwork network = new BayesianNetwork();
		if(args[1].contains(".xml")) {
			XMLBIFParser parser = new XMLBIFParser();
			network = parser.readNetworkFromFile("bn/examples/" + args[1]);
		}else {
			File f= new File("bn/examples/" + args[1]);
			BIFParser parser = new BIFParser(new FileInputStream(f));
			network = parser.parseNetwork();
		}
		
		// read in query variable X
		RandomVariable X = network.getVariableByName(args[2]);

		// read in observed values for variables E
	    Assignment e = new Assignment();
	    for (int i = 3; i < args.length; i += 2) {
	    	e.set(network.getVariableByName(args[i]), args[i + 1]);
	    }
	    
	    LSampleInferencer lsinfer = new LSampleInferencer();
	    lsinfer.setN(N);
	    
	    // solve
	    long startTime = System.currentTimeMillis(); // start time
	    System.out.println(lsinfer.ask(network, X, e));
	    long endTime   = System.currentTimeMillis(); //end time
	    long TotalTime = endTime - startTime;       //total time
	    System.out.println("Time consumption: " + TotalTime  + "ms");
	}
}
