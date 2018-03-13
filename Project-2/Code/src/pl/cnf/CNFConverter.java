/*
 * File: CNFConverter.java
 * Creator: George Ferguson
 * Created: Mon Mar 14 22:50:05 2011
 * Time-stamp: <Fri Mar 16 13:29:51 EDT 2012 ferguson>
 */

package pl.cnf;
import pl.core.*;
import java.util.*;

/**
 * This class provides a static method for converting a Sentence
 * of propositional logic to conjunctive normal form (CNF).
 */
public class CNFConverter {

	/**
	 * Convert the given Sentence to conjunctive normal form (CNF) and
	 * return the resulting Set of Clauses (disjunctions of literals).
	 * Note that the result may share structure with the input.
	 */
	public static Set<Clause> convert(Sentence s) {
		s = eliminateBiconditionals(s);
		s = eliminateImplications(s);
		s = moveNegationInwards(s);
		s = distributeOrOverAnd(s);
		return splitIntoClauses(s);
	}

	/**
	 * Remove biconditionals from the given Sentence (replacing them
	 * with one-way implications) and return the new Sentence.
	 */
	protected static Sentence eliminateBiconditionals(Sentence s) {
		traceIn("eliminateBiconditionals", s);
		if (s instanceof Symbol) {
			// Nothing to do
		} else if (s instanceof UnaryCompoundSentence) {
			UnaryCompoundSentence ucs = (UnaryCompoundSentence)s;
			UnaryConnective connective = ucs.getConnective();
			Sentence argument = eliminateBiconditionals(ucs.getArgument());
			s = ucs.rebuild(connective, argument);
		} else if (s instanceof BinaryCompoundSentence) {
			BinaryCompoundSentence bcs = (BinaryCompoundSentence)s;
			BinaryConnective connective = bcs.getConnective();
			Sentence lhs = eliminateBiconditionals(bcs.getLhs());
			Sentence rhs = eliminateBiconditionals(bcs.getRhs());
			if (connective == BinaryConnective.IFF) {
				Sentence r1 = new Implication(lhs, rhs);
				Sentence r2 = new Implication(rhs, lhs);
				s = new Conjunction(r1, r2);
			} else {
				s = bcs.rebuild(connective, lhs, rhs);
			}
		}
		traceOut("eliminateBiconditionals", s);
		return s;
	}

	/**
	 * Remove implications from the given Sentence (replacing them
	 * with a disjunction) and return the new Sentence.
	 */
	protected static Sentence eliminateImplications(Sentence s) {
		traceIn("eliminateImplications", s);
		if (s instanceof Symbol) {
			// Nothing to do
		} else if (s instanceof UnaryCompoundSentence) {
			UnaryCompoundSentence ucs = (UnaryCompoundSentence)s;
			UnaryConnective connective = ucs.getConnective();
			Sentence argument = eliminateImplications(ucs.getArgument());
			s = ucs.rebuild(connective, argument);
		} else if (s instanceof BinaryCompoundSentence) {
			BinaryCompoundSentence bcs = (BinaryCompoundSentence)s;
			BinaryConnective connective = bcs.getConnective();
			Sentence lhs = eliminateImplications(bcs.getLhs());
			Sentence rhs = eliminateImplications(bcs.getRhs());
			if (connective == BinaryConnective.IMPLIES) {
				Sentence neglhs = new Negation(lhs);
				s = new Disjunction(neglhs, rhs);
			} else {
				s = bcs.rebuild(connective, lhs, rhs);
			}
		}
		traceOut("eliminateImplications", s);
		return s;
	}

	/**
	 * Move negations in as far as possible in the given Sentence and
	 * return the result.
	 * Essentially, this means that if the given sentence is a NOT of a
	 * compound sentence (AND, OR, or a quantified sentence), then move
	 * the negation in.
	 */
	protected static Sentence moveNegationInwards(Sentence s) {
		traceIn("moveNegationInwards", s);
		if (s instanceof Symbol) {
			// Nothing to do
		} else if (s instanceof UnaryCompoundSentence) {
			UnaryCompoundSentence ucs = (UnaryCompoundSentence)s;
			UnaryConnective connective = ucs.getConnective();
			Sentence argument = ucs.getArgument();
			if (connective.equals(UnaryConnective.NOT)) {
				s = negate(argument);
			} else {
				s = ucs.rebuild(connective, moveNegationInwards(argument));
			}
		} else if (s instanceof BinaryCompoundSentence) {
			BinaryCompoundSentence bcs = (BinaryCompoundSentence)s;
			BinaryConnective connective = bcs.getConnective();
			Sentence lhs = moveNegationInwards(bcs.getLhs());
			Sentence rhs = moveNegationInwards(bcs.getRhs());
			s = bcs.rebuild(connective, lhs, rhs);
		}
		traceOut("moveNegationInwards", s);
		return s;
	}

	/**
	 * Return the negation of the given Sentence, assuming it doesn't
	 * contain any biconditionals or implications.
	 */
	protected static Sentence negate(Sentence s) {
		traceIn("negate", s);
		if (s instanceof Symbol) {
			s = new Negation(s);
		} else if (s instanceof UnaryCompoundSentence) {
			UnaryCompoundSentence ucs = (UnaryCompoundSentence)s;
			UnaryConnective connective = ucs.getConnective();
			Sentence argument = ucs.getArgument();
			if (connective.equals(UnaryConnective.NOT)) {
				s = argument;
			} else {
				// No other unary connectives in PL
				throw new RuntimeException("unexpected unary connective: " + connective);
			}
		} else if (s instanceof BinaryCompoundSentence) {
			BinaryCompoundSentence bcs = (BinaryCompoundSentence)s;
			BinaryConnective connective = bcs.getConnective();
			Sentence neglhs = negate(bcs.getLhs());
			Sentence negrhs = negate(bcs.getRhs());
			if (connective.equals(BinaryConnective.AND)) {
				s = new Disjunction(neglhs, negrhs);
			} else if (connective.equals(BinaryConnective.OR)) {
				s = new Conjunction(neglhs, negrhs);
			} else {
				// No other binary connectives possible at this point
				throw new RuntimeException("unexpected binary connective: " + connective);
			}
		} else {
			// No other types possible...
			throw new RuntimeException("unexpected sentence type: " + s.getClass());
		}
		traceOut("negate", s);
		return s;
	}

	/**
	 * Distribute OR over AND and return the resulting Sentence.
	 */
	protected static Sentence distributeOrOverAnd(Sentence s) {
		traceIn("distributeOrOverAnd", s);
		if (s instanceof Symbol) {
			// Nothing to do
		} else if (s instanceof UnaryCompoundSentence) {
			UnaryCompoundSentence ucs = (UnaryCompoundSentence)s;
			UnaryConnective connective = ucs.getConnective();
			Sentence argument = distributeOrOverAnd(ucs.getArgument());
			s = ucs.rebuild(connective, argument);
		} else if (s instanceof BinaryCompoundSentence) {
			BinaryCompoundSentence bcs = (BinaryCompoundSentence)s;
			BinaryConnective connective = bcs.getConnective();
			Sentence a = bcs.getLhs();
			Sentence b = bcs.getRhs();
			if (connective == BinaryConnective.OR) {
				// a or b
				a = distributeOrOverAnd(a);
				b = distributeOrOverAnd(b);
				if (a instanceof BinaryCompoundSentence &&
						((BinaryCompoundSentence)a).getConnective() == BinaryConnective.AND) {
					// (a1 and a2) or b
					BinaryCompoundSentence abcs = (BinaryCompoundSentence)a;
					Sentence a1 = abcs.getLhs();
					Sentence a2 = abcs.getRhs();
					Sentence a1OrB = new Disjunction(a1, b);
					Sentence a2OrB = new Disjunction(a2, b);
					s = new Conjunction(distributeOrOverAnd(a1OrB),
										distributeOrOverAnd(a2OrB));
				} else if (b instanceof BinaryCompoundSentence &&
						((BinaryCompoundSentence)b).getConnective() == BinaryConnective.AND) {
					// a or (b1 and b2)
					BinaryCompoundSentence bbcs = (BinaryCompoundSentence)b;
					Sentence b1 = bbcs.getLhs();
					Sentence b2 = bbcs.getRhs();
					Sentence aOrB1 = new Disjunction(a, b1);
					Sentence aOrB2 = new Disjunction(a, b2);
					s = new Conjunction(distributeOrOverAnd(aOrB1),
										distributeOrOverAnd(aOrB2));
				} else {
					// Neither lhs nor rhs is AND
					s = bcs.rebuild(connective, a, b);
				}
			} else {
				// Binary connective not OR
				s = bcs.rebuild(connective, distributeOrOverAnd(a), distributeOrOverAnd(b));
			}
		}
		traceOut("distributeOrOverAnd", s);
		return s;
	}

	/**
	 * At this point we have a conjunction of clauses, which we break
	 * into individual clauses and return the set.
	 */
	protected static Set<Clause> splitIntoClauses(Sentence s) {
		traceIn("splitIntoClauses", s);
		Set<Clause> clauses = new HashSet<Clause>();
		splitIntoClauses(s, clauses);
		trace("splitIntoClauses: returning: " + clauses);
		return clauses;
	}

	/**
	 * Split given Sentence into clauses, accumulating them in the given
	 * Set.
	 */
	protected static void splitIntoClauses(Sentence s, Set<Clause> clauses) {
		if (s instanceof BinaryCompoundSentence) {
			BinaryCompoundSentence bcs = (BinaryCompoundSentence)s;
			if (bcs.getConnective() == BinaryConnective.AND) {
				Sentence lhs = bcs.getLhs();
				Sentence rhs = bcs.getRhs();
				splitIntoClauses(lhs, clauses);
				splitIntoClauses(rhs, clauses);
				return;
			}
		}
		// Otherwise
		clauses.add(new Clause(s));
	}

	// Utility methods

	static void traceIn(String method, Sentence s) {
		trace(method + ": ", s);
	}

	static void traceOut(String method, Sentence s) {
		trace(method + ": returning ", s);
	}

	protected static boolean TRACE = false;

	static void trace(String str, Sentence s) {
		if (TRACE) {
			System.err.print(str);
			System.err.println(s);
		}
	}

	static void trace(String str) {
		if (TRACE) {
			System.err.println(str);
		}
	}

	/**
	 * Convert a KB (set of Sentences) to clauses and return the
	 * Set of Clauses.
	 * For anything more than a small KB, you would be better off
	 * writing this yourself so that you can monitor progress and
	 * also an appropriate Set implementation to gather the results.
	 */
	public static Set<Clause> convert(KB kb) {
		Set<Clause> clauses = new HashSet<Clause>();
		for (Sentence s : kb.sentences()) {
			clauses.addAll(convert(s));
		}
		return clauses;
	}

}
