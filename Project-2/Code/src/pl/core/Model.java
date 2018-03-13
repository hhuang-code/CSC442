package pl.core;

/**
 * A Model is an assignment of boolean values (true or false) to
 * PropositionalSymbols.
 */
public interface Model {

	/**
	 * Set the value assigned to the given PropositionSymbol in this
	 * Model to the given boolean VALUE.
	 */
	public void set(Symbol sym, boolean value);

	/**
	 * Returns the boolean value associated with the given PropositionalSymbol
	 * in this Model.
	 */
	public boolean get(Symbol sym);
	
	/**
	 * Return true if this Model satisfies (makes true) the given KB.
	 */
	public boolean satisfies(KB kb);

	/**
	 * Return true if this Model satisfies (makes true) the given Sentence.
	 */
	public boolean satisfies(Sentence sentence);
	
	/**
	 * Print the assignments in this Model to System.out.
	 */
	public void dump();

}
