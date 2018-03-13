package pl.core;

public class Implication extends BinaryCompoundSentence {

	public Implication(Sentence lhs, Sentence rhs) {
		super(BinaryConnective.IMPLIES, lhs, rhs);
	}

	/**
	 * Return true if this Implication is satisfied by the given Model.
	 * That is, if either its lhs is not satisfied by the Model, or
	 * its rhs is satisified by the Model.
	 */
	public boolean isSatisfiedBy(Model model) {
		return !lhs.isSatisfiedBy(model) || rhs.isSatisfiedBy(model);
	}

}
