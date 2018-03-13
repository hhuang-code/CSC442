package pl.core;

public class Disjunction extends BinaryCompoundSentence {

	public Disjunction(Sentence lhs, Sentence rhs) {
		super(BinaryConnective.OR, lhs, rhs);
	}

	/**
	 * Return true if this Disjunction is satisfied by the given Model.
	 * That is, if either of its arguments are satisfied by the Model.
	 */
	public boolean isSatisfiedBy(Model model) {
		return lhs.isSatisfiedBy(model) || rhs.isSatisfiedBy(model);
	}

}
