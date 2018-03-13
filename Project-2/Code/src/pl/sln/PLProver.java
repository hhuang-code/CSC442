package pl.sln;

import java.util.HashSet;
import java.util.Set;

import pl.cnf.CNFConverter;
import pl.cnf.Clause;
import pl.cnf.Literal;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.prover.Prover;

public class PLProver implements Prover {

	@Override
	public boolean entails(KB kb, Sentence alpha) {
		// store all clauses from KB
		Set<Clause> clauses = new HashSet<Clause>();
		for (Sentence s : kb.sentences()) {
			Set<Clause> c = CNFConverter.convert(s);
			clauses.addAll(c);
		}
		// add clauses from alpha
		clauses.addAll(CNFConverter.convert(new Negation(alpha)));
		
		// an empty set
		Set<Clause> new_clauses = new HashSet<Clause>();
		
		while (true) {
			for (Clause ci : clauses) {
				for (Clause cj : clauses) {
					Set<Clause> resolvents = PLResolve(ci, cj);
					// check whether the resolvents contains empty clause
					for (Clause c : resolvents) {	
						if (c.size() == 0) {
							return true;
						}
					}
					new_clauses.addAll(resolvents);
					}
				}
			if (clauses.containsAll(new_clauses)) {
				return false;
			}
			clauses.addAll(new_clauses);
		}
	}
	
	protected Set<Clause> PLResolve(Clause ci, Clause cj) {
		// an empty set
		Set<Clause> resolvents = new HashSet<Clause>();
		
		for (Literal li : ci) {
			for (Literal lj : cj) {
				if (li.getContent().equals(lj.getContent()) && li.getPolarity() != lj.getPolarity()) {
					// copy a clause
					Clause ci_copy = new Clause(ci);
					// remove complementary
					ci_copy.remove(li);
					// copy a clause
					Clause cj_copy = new Clause(cj);
					// remove complementary
					cj_copy.remove(lj);
					// combine two clauses
					ci_copy.addAll(cj_copy);
					
					int len = ci_copy.size();
					if (len <= ci.size() && len <= cj.size()) {
						// add to resolvents
						resolvents.add(ci_copy);
					}
				}
			}
		}
		
		return resolvents;
	}

}
