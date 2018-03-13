package pl.core;

abstract public class BinaryCompoundSentence extends CompoundSentence {

	protected BinaryConnective connective;
	protected Sentence lhs;
	protected Sentence rhs;

	public BinaryCompoundSentence(BinaryConnective connective, Sentence lhs, Sentence rhs) {
		super();
		this.connective = connective;
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public BinaryConnective getConnective() {
		return connective;
	}

	public Sentence getLhs() {
		return lhs;
	}

	public Sentence getRhs() {
		return rhs;
	}

	public String toString() {
		return "(" + connective.toString() + " " + lhs.toString() + " " + rhs.toString() + ")"; 
	}
	
	/**
	 * If the given connective, lhs, and rhs are equal to the
	 * corresponding components of this BinaryCompoundSentence, then
	 * simply return it, otherwise return a new BinaryCompoundSentence
	 * with those components.  That is, reuse this object if possible,
	 * otherwise return a new object.
	 * This was code was more elegant before I had subclasses of
	 * UnaryCompoundSentence...
	 */
	public BinaryCompoundSentence rebuild(BinaryConnective connective, Sentence lhs, Sentence rhs) {
		if (getConnective().equals(connective) &&
				getLhs().equals(lhs) &&
				getRhs().equals(rhs)) {
			return this;
		} else {
			switch (getConnective()) {
			case AND:
				return new Conjunction(lhs, rhs);
			case OR:
				return new Disjunction(lhs, rhs);
			case IMPLIES:
				return new Implication(lhs, rhs);
			case IFF:
				return new Biconditional(lhs, rhs);
			default:
				throw new IllegalArgumentException();
			}
		}
	}


}
