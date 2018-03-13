package pl.core;

public class Symbol implements Sentence {
	
	protected String name;

	public Symbol(String name) {
		super();
		this.name = name;
	}
	
	public String toString() {
		return name;
	}

	/**
	 * Return true if this Symbol is satisfied by the given Model.
	 * That is, if it is assigned true by the model.
	 */
	public boolean isSatisfiedBy(Model model) {
		return model.get(this);
	}
	
	//
    // equals() and hashCode(), for use in hashtables
	//
	
	/**
	 * Two PropositionSymbols are equals iff they have the same name.
	 * Note that this is expensive to test. You are much better off using
	 * {@code intern} to convert names to symbols, in which case all symbols
	 * with the same name are the same object, in which case you can use
	 * {@code ==} to compare them.
	 * But this method is here for methods that use {@code equals}, such
	 * as from Collection classes.
	 */
	@Override
	public boolean equals(Object other) {
		// Per Object.equals(): x.equals(null) should return false
		if (other == null) {
			return false;
		} else if (this == other) {
			return true;
		} else if (!(other instanceof Symbol)) {
			return false;
		} else {
			Symbol otherp = (Symbol)other;
			return name.equals(otherp.name);
		}
	}

	/**
	 * Returns a hash code value for the object. <q>If two objects
	 * are equal according to the {@link Object#equals} method, then calling
	 * the hashCode method on each of the two objects must produce the
	 * same integer result.</q>
	 * <p>
	 * We return the hashCode() of the PropositionSymbol's name, since
	 * two PropositionSymbols are equals() iff they have the same name.
	 */
	public int hashCode() {
		return name.hashCode();
	}

}
