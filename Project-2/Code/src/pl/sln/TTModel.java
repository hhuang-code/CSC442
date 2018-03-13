package pl.sln;

import java.util.HashMap;
import java.util.Map;

import pl.core.KB;
import pl.core.Model;
import pl.core.Sentence;
import pl.core.Symbol;

public class TTModel implements Model {
	
	protected Map<Symbol, Boolean> assignment = new HashMap<>();
	
	TTModel (TTModel model) {
		assignment = new HashMap<>(model.getAssignment());
	}
	
	public TTModel() {
		// TODO Auto-generated constructor stub
	}

	Map<Symbol, Boolean> getAssignment () {
		return assignment;
	}

	@Override
	public void set(Symbol sym, boolean value) {
		// TODO Auto-generated method stub
		assignment.put(sym, value);
	}

	@Override
	public boolean get(Symbol sym) {
		// TODO Auto-generated method stub
		return assignment.get(sym);
	}

	@Override
	public boolean satisfies(KB kb) {
		// TODO Auto-generated method stub
		for (Sentence s : kb.sentences()) {
			if (!s.isSatisfiedBy(this)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean satisfies(Sentence sentence) {
		// TODO Auto-generated method stub
		return sentence.isSatisfiedBy(this);
	}

	@Override
	public void dump() {
		// TODO Auto-generated method stub
		for (HashMap.Entry<Symbol, Boolean> entry : assignment.entrySet()) {  
		    System.out.println(entry.getKey() + " " + entry.getValue());  
		}  
	}

}
