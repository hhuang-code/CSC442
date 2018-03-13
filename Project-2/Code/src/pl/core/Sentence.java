package pl.core;

public interface Sentence {
	
	/**
	 * Return true if this Sentence is satisfied by the given Model.
	 */
	public boolean isSatisfiedBy(Model model);

}
