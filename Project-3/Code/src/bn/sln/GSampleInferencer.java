package bn.sln;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

public class GSampleInferencer implements Inferencer {
	
	private int N;
	
	public GSampleInferencer() {
	}
	
	void setN(int n) {
		this.N = n;
	}
	
	@Override
	public Distribution ask(BayesianNetwork bn, RandomVariable X, Assignment e) {

		// store counts for each value
		Distribution NN = new Distribution();
		for (Object val : X.getDomain()) {
			NN.put(val, 0.0);
		}
		
		// get all random variables in bn
		List<RandomVariable> Z = bn.getVariableListTopologicallySorted();
		
		// get all random variables in e
		Set<RandomVariable> rvs_e = e.keySet();
		
		// get non-evidence variables in bn
		for (RandomVariable rv : rvs_e) {
			Z.remove(rv);
		}
		
		Assignment x = e.copy();
		
		// initialize non-evidence variables with random value
		for (RandomVariable rv : Z) {
			Domain dm = rv.getDomain();
			
			// generate a random variable between 0 and 1
			int randnum = (int)(Math.random() * dm.size());
			
			x.set(rv, dm.get(randnum));
		}
		
		for(int j = 1; j <= N; j++) {
			for (RandomVariable rv : Z) {
				Distribution rvp = new Distribution();
				Domain dm = rv.getDomain();
				// for each value in RV's domain
				for (Object val : dm) {	
					x.set(rv, val);
					rvp.put(val, bn.getProb(rv, x));
					// for each child of rv
					Set<RandomVariable> children = bn.getChildren(rv);
					for (RandomVariable child : children) {
						rvp.put(val, rvp.get(val) * bn.getProb(child, x));
					}
				}
				// normalize
				rvp.normalize();
				
				// sample from mb
				Random random = new java.util.Random();	// generate a random variable between 0 and 1
				double randnum = random.nextDouble();
							
				// for each value in RV's domain
				for (Object val : dm) {	
					x.set(rv, val);
					randnum -= rvp.get(val);
					if (randnum <= 0) {
						break;
					}
				}
				
				// set count for each value of rv
				NN.put(x.get(X), NN.get(x.get(X)) + 1);
			}
		}
		
		NN.normalize();
		
		return NN;
		
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
	    
	    GSampleInferencer gsinfer = new GSampleInferencer();
	    gsinfer.setN(N);
	    
	    // solve
	    long startTime = System.currentTimeMillis(); // start time
	    System.out.println(gsinfer.ask(network, X, e));
	    long endTime   = System.currentTimeMillis(); //end time
	    long TotalTime = endTime - startTime;       //total time
	    System.out.println("Time consumption: " + TotalTime + "ms");
	}
}
