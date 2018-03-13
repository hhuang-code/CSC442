package pl.prover;

import pl.core.KB;
import pl.core.Model;

public interface Solver {
	
	/**
	 * If the given KB is satisfiable, return a satisfying Model.
	 */
	public Model solve(KB kb);


}
