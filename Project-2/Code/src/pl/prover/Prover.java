package pl.prover;

import pl.core.KB;
import pl.core.Sentence;

public interface Prover {
	
	/**
	 * Return true if the given KB entails the given Sentence.
	 */
	public boolean entails(KB kb, Sentence alpha);

}
