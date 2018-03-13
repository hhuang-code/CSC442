package pl.core;

abstract public class UnaryCompoundSentence extends CompoundSentence {
	
	protected UnaryConnective connective;
	protected Sentence argument;

	public UnaryCompoundSentence(UnaryConnective connective, Sentence argument) {
		super();
		this.connective = connective;
		this.argument = argument;
	}
	
	public UnaryConnective getConnective() {
		return connective;
	}

	public Sentence getArgument() {
		return argument;
	}
	
	public String toString() {
		return "(" + connective.toString() + " " + argument.toString() + ")"; 
	}
	
	/**
	 * If the given connective and argument are equal to the
	 * corresponding components of this UnaryCompoundSentence, then
	 * simply return it, otherwise return a new UnaryCompoundSentence
	 * with those components.  That is, reuse this object if possible,
	 * otherwise return a new one.
	 * This was code was more elegant before I had subclasses of
	 * UnaryCompoundSentence...
	 */
	public UnaryCompoundSentence rebuild(UnaryConnective connective, Sentence argument) {
		if (getConnective().equals(connective) &&
				getArgument().equals(argument)) {
			return this;
		} else {
			switch (getConnective()) {
			case NOT:
				return new Negation(argument);
			default:
				throw new IllegalArgumentException();
			}
		}
	}
}
