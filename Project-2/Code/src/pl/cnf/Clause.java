/*
 * File: Clause.java
 * Creator: George Ferguson
 * Created: Tue Mar 15 15:58:15 2011
 * Time-stamp: <Fri Mar 23 14:50:20 EDT 2012 ferguson>
 */

package pl.cnf;
import pl.core.*;
import pl.util.ArraySet;

/**
 * A clause is a disjunction of literals.
 * We represent it as a set for ease of use later.
 * <p>
 * Note that the current implementation of this class extends ArraySet, which
 * itself extends AbstractSet, which overrides {@link Object#equals} and
 *{@link Object#hashCode} (with {@link AbstractSet#equals} and
 *{@link Abstract#hashCode}, respectively). Thus we do not need to
 * provide implementations of those methods ourselves (but if we did,
 * we would need to remember that <q>the hash code of a set is defined
 * to be the sum of the hash codes of the elements in the set.</q>
 */
public class Clause extends ArraySet<Literal> {
	
	/**
	 * default constructor
	 */
	public Clause () {
		super();
	}
	
	/**
	 * copy constructor
	 * @param c: an existing Clause object
	 */
	public Clause (Clause c) {
		super(c.elements);
	}
	
	/**
	 * Return a new Clause constructed from the given Sentence, which
	 * must be a disjunction of literals (i.e., an AtomicSentence, the
	 * negation of an AtomicSentence, or a BinaryCompoundSentence whose
	 * connective is OR in which case its arguments must be disjunctions
	 * of literals.
	 */
	public Clause(Sentence s) throws IllegalArgumentException {
		super();
		toClauses(s, this);
	}

	protected static void toClauses(Sentence s, Clause c) throws IllegalArgumentException {
		if (s instanceof BinaryCompoundSentence) {
			BinaryCompoundSentence bcs = (BinaryCompoundSentence)s;
			BinaryConnective conn = bcs.getConnective();
			if (conn == BinaryConnective.OR) {
				Sentence lhs = bcs.getLhs();
				Sentence rhs = bcs.getRhs();
				toClauses(lhs, c);
				toClauses(rhs, c);
				return;
			}
		}
		// Otherwise
		c.add(new Literal(s));
	}

	/**
	 * Return the string representation of this Clause.
	 */
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("{");
		boolean first = true;
		for (Literal literal : this) {
			if (!first) {
				buf.append(",");
			} else {
				first = false;
			}
			buf.append(literal.toString());
		}
		buf.append("}");
		return buf.toString();
	}

    /**
     * Return true if this Clause is satisfied by the given Model.
     * That is, if one of its Literals is satisfied by the Model.
     */
	public boolean isSatisfiedBy(Model model) {
		for (Literal literal : this) {
			if (literal.isSatisfiedBy(model)) {
				return true;
			}
		}
		return false;
	}

}
