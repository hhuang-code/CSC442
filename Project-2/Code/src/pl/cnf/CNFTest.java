package pl.cnf;
import java.util.Set;

import pl.core.*;

/**
 * Converts a test sentence from AIMA 7.5.2 to clauses.
 */
public class CNFTest {

	public static void main(String[] args) {
		
		KB kb = new KB();
		Symbol b11 = kb.intern("B11");
		Symbol p12 = kb.intern("P12");
		Symbol p21 = kb.intern("P21");
		
		Sentence s = new Biconditional(b11, new Disjunction(p12, p21));
		System.out.println(s);

		Set<Clause> clauses = CNFConverter.convert(s);
		System.out.println(clauses);

	}

}
