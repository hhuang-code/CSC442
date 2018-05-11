package dt.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dt.util.ArraySet;

/**
 * Implementation of the decision-tree learning algorithm in AIMA Fig 18.5.
 * This is based on ID3 (AIMA p. 758).
 */
public class DecisionTreeLearner extends AbstractDecisionTreeLearner {
	
	/**
	 * Construct and return a new DecisionTreeLearner for the given Problem.
	 */
	public DecisionTreeLearner(Problem problem) {
		super(problem);
	}
	
	/**
	 * Main recursive decision-tree learning (ID3) method.  
	 */
	@Override
	protected DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parent_examples) {
	    // Must be implemented by you; the following two methods may be useful
		if(examples.isEmpty()) {
			return new DecisionTree(this.pluralityValue(parent_examples));
		}else if(this.uniqueOutputValue(examples) != null) {
			return new DecisionTree(this.uniqueOutputValue(examples));
		}else if(attributes.isEmpty()) {
			return new DecisionTree(this.pluralityValue(examples));
		}else {
				Variable A = maxImportance(attributes,examples);
				DecisionTree tree = new DecisionTree(A);
				List<Variable> newAttr = new ArrayList<Variable>(attributes);
				
				newAttr.remove(A);
				for( String value : A.domain) {
					Set<Example> newExamples = this.examplesWithValueForAttribute(examples, A, value);
					tree.children.add(this.learn(newExamples,newAttr,examples));
				}
				
				return tree;
			
		}
	}
	
	private Variable maxImportance(List <Variable> attributes, Set<Example> examples) {
		double maxReminder = 0.0;
		Variable res = null;
		boolean init = false;
		int size = examples.size();
		for(Variable attr : attributes) {
			double reminder = 0.0;
			for(String a : attr.domain) {
				if(countExamplesWithValueForAttribute(examples, attr, a)!=0)
					reminder += (double)this.countExamplesWithValueForAttribute(examples, attr, a)/size*this.reminder(this.examplesWithValueForAttribute(examples, attr, a), attr, a);
			}
			if(reminder==reminder) {
				if(init == true) {
					if( reminder-maxReminder>0.0) {
						maxReminder=reminder;
						res = attr;
					} 
				}else{
					maxReminder=reminder;
					res = attr;
					init=true;
				}
			}
		}
		return res;
	}
	
	private double reminder(Set<Example> examples, Variable attribute,String a) {
		int size = examples.size();

		double entropy = 0.0;
		for(String outValue : this.problem.output.domain) {
			double p = (double)this.countExamplesWithValueForOutput(examples, outValue)/size;
			if(p!=0.0)
				entropy += p*this.log2(p);
		}
		return entropy;
	}
	
	/**
	 * Returns the most common output value among a set of Examples,
	 * breaking ties randomly.
	 * I don't do the random part yet.
	 */
	@Override
	protected String pluralityValue(Set<Example> examples) {
	    // Must be implemented by you
		Map<String,Integer> counter = new HashMap<String,Integer>();
		for(Example example: examples) {
			if(counter.containsKey(example.getOutputValue())) {
				counter.put(example.getOutputValue(), counter.get(example.getOutputValue())+1);
			}else {
				counter.put(example.getOutputValue(), 1);
			}
		}
		String res = "";
		Integer max=0;
		for (String key : counter.keySet()) {
			if (counter.get(key)>= max) {
				res=key;
				max=counter.get(key);
			}
		}
		return res;
	}
	
	/**
	 * Returns the single unique output value among the given examples
	 * if there is only one, otherwise null.
	 */
	@Override
	protected String uniqueOutputValue(Set<Example> examples) {
	    // Must be implemented by you
		
		Example[] eArray = examples.toArray(new Example[examples.size()]);
		String uniRes=eArray[0].getOutputValue();
		for (Example example : examples) {
			if(!example.getOutputValue().equals(uniRes)) {
				return null;
			}
		}
		return uniRes;
	}
	
	//
	// Utility methods required by the AbstractDecisionTreeLearner
	//

	/**
	 * Return the subset of the given examples for which Variable a has value vk.
	 */
	@Override
	protected Set<Example> examplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
	    // Must be implemented by you
		Set<Example> result = new ArraySet<Example>();
		for (Example e : examples) {
			if (e.getInputValue(a).equals(vk)) {
				result.add(e);
			}
		}
		return result;
	}
	
	/**
	 * Return the number of the given examples for which Variable a has value vk.
	 */
	@Override
	protected int countExamplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
		int result = 0;
		for (Example e : examples) {
			if (e.getInputValue(a).equals(vk)) {
				result += 1;
			}
		}
		return result;
		
	}

	/**
	 * Return the number of the given examples for which the output has value vk.
	 */
	@Override
	protected int countExamplesWithValueForOutput(Set<Example> examples, String vk) {
	    // Must be implemented by you
		int result = 0;
		for (Example e : examples) {
			if (e.getOutputValue().equals(vk)) {
				result += 1;
			}
		}
		return result;
	}

}