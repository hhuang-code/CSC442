package pl.core;

public class Conjunction extends BinaryCompoundSentence {

	public Conjunction(Sentence lhs, Sentence rhs) {
		super(BinaryConnective.AND, lhs, rhs);
	}

	/**
	 * Return true if this Conjunction is satisfied by the given Model.
	 * That is, if both its arguments are satisfied by the Model.
	 */
	public boolean isSatisfiedBy(Model model) {
		return lhs.isSatisfiedBy(model) && rhs.isSatisfiedBy(model);
	}

}
