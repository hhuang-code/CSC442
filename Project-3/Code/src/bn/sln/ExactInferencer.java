package bn.sln;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.RandomVariable;
import bn.inference.Inferencer;
import bn.parser.BIFParser;
import bn.parser.XMLBIFParser;

public class ExactInferencer implements Inferencer {

	@Override
	public Distribution ask(BayesianNetwork bn, RandomVariable X, Assignment e) {
		// TODO Auto-generated method stub
		
		// a distribution over X, initially empty
		Distribution dist = new Distribution(X);
		
		for (Object value : X.getDomain()) {
			Assignment ec = e.copy();
			ec.set(X, value);
			// get all random variables in bn
			List<RandomVariable> rvs = new ArrayList<RandomVariable>(bn.getVariableListTopologicallySorted());
			dist.put(value, all(bn, rvs, ec));
		}
		
		dist.normalize();
		
		return dist;
	}
	
	public double all(BayesianNetwork bn, List<RandomVariable> vars, Assignment e) {
		
		if (vars.isEmpty()) {
			return 1.0;
		} 
		
		RandomVariable Y = vars.get(0);	// get the first
		vars.remove(0);	// get the rest
		
		if (e.containsKey(Y)) {
			return bn.getProb(Y, e) * all(bn, vars, e);
		} else {
			double prob = 0;
			for (Object value : Y.getDomain()) {
				Assignment ec = e.copy();
				ec.set(Y, value);
				List<RandomVariable> rvs = new ArrayList<RandomVariable>(vars);
				prob += bn.getProb(Y, ec) * all(bn, rvs, ec);
			}
			
			return prob;
		}
	}

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		
		// load bn xml
		BayesianNetwork network = new BayesianNetwork();
		if(args[0].contains(".xml")) {
			XMLBIFParser parser = new XMLBIFParser();
			network = parser.readNetworkFromFile("bn/examples/" + args[0]);
		}else {
			File f= new File("bn/examples/" + args[0]);
			BIFParser parser = new BIFParser(new FileInputStream(f));
			network = parser.parseNetwork();
		}
		
		// read in query variable X
		RandomVariable X = network.getVariableByName(args[1]);
		
		// read in observed values for variables E
	    Assignment e = new Assignment();
	    for (int i = 2; i < args.length; i += 2) {
	    	e.set(network.getVariableByName(args[i]), args[i + 1]);
	    }
	    
	    ExactInferencer mbni = new ExactInferencer();
	    
	    // solve
	    long startTime = System.currentTimeMillis(); // start time
	    System.out.println(mbni.ask(network, X, e));
	    long endTime   = System.currentTimeMillis(); //end time
	    long TotalTime = endTime - startTime;       //total time
	    System.out.println("Time consumption: " + TotalTime  + "ms");
	}

}
