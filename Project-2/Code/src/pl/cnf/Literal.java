/*
 * File: Literal.java
 * Creator: George Ferguson
 * Created: Tue Mar 15 16:06:28 2011
 * Time-stamp: <Fri Mar 23 14:40:03 EDT 2012 ferguson>
 */

package pl.cnf;
import pl.core.*;

/**
 * A Literal is either a Symbol or the negation of an Symbol.
 */
public class Literal {

    /**
     * Enumeration of possible polarities of a Literal (positive or
     * negative).
     */
    public enum Polarity { POSITIVE, NEGATIVE };

    /**
     * The Symbol that is the <q>content</q> of this literal.
     */
    protected Symbol content;

    /**
     * The Polarity (positive or negative) of this Literal.
     */
    protected Polarity polarity;

    /**
     * Return the Symbol that is the <q>content</q> of this literal.
     */
    public Symbol getContent() {
    	return content;
    }

    /**
     * Return the Polarity (positive or negative) of this Literal.
     */
    public Polarity getPolarity() {
    	return polarity;
    }

    /**
     * Return a new Literal constructed from the given Sentence s,
     * which must be an Symbol or the negation of an Symbol
     * (i.e., a UnaryCompoundSentence whose connective is NOT and whose
     * argument is an Symbol).
     */
    public Literal(Sentence s) throws IllegalArgumentException {
    	if (s instanceof Symbol) {
    		this.content = (Symbol)s;
    		this.polarity = Polarity.POSITIVE;
    		return;
    	} else if (s instanceof UnaryCompoundSentence) {
    		UnaryCompoundSentence ucs = (UnaryCompoundSentence)s;
    		UnaryConnective conn = ucs.getConnective();
    		Sentence argument = ucs.getArgument();
    		if (conn == UnaryConnective.NOT && argument instanceof Symbol) {
    			this.content = (Symbol)argument;
    			this.polarity = Polarity.NEGATIVE;
    			return;
    		}
    	}
    	// Otherwise
    	throw new IllegalArgumentException(s.toString());
    }
    
    /**
     * Return the string representation of this Literal.
     */
    public String toString() {
    	String sign = "";
    	if (polarity == Polarity.NEGATIVE) {
    	    sign = "~";
    	}
    	return sign + content.toString();
    }
    
    /**
     * Return true if this Literal is satisfied by the given Model.
     * That is, if this is a positive Literal, its content must be assigned
     * true by the Model, or if it is a negative Literals, its content must
     * be assigned false by the Model.
     */
    public boolean isSatisfiedBy(Model model) {
    	System.out.println("Literal.iSatisfiedBy: this=" + this);
    	model.dump();
    	System.out.println("  model says: " + model.get(content));
    	if (polarity == Polarity.POSITIVE) {
    		return model.get(content);
    	} else {
    		return !model.get(content);
    	}
    }

    //
    // equals() and hashCode() for use in hashtables
    //
    
    /**
     * Indicates whether some other object is "equal to" this one.
     * Two Literals are equal iff they have the same polarity and content.
     * Implementing this requires also implementing {@link Object#hashCode}, per
     * the description of {@link Object#equals}.
     */
    @Override
    public boolean equals(Object other) {
    	// Per Object.equals(): x.equals(null) should return false
    	if (other == null) {
    		return false;
    	} else if (this == other) {
    		return true;
    	} else if (!(other instanceof Literal)) {
    		return false;
    	} else {
    		Literal otherl = (Literal)other;
    		return (polarity == otherl.polarity &&
    				content.equals(otherl.content));
    	}
    }

    /**
     * Returns a hash code value for the object. <q>If two objects
     * are equal according to the {@link Object#equals} method, then calling
     * the hashCode method on each of the two objects must produce the
     * same integer result.</q>
     * <p>
     * My implementation uses a product sum algorithm with a prime base
     * over the polarity and content of the Literal.
     * @see <a href="http://en.wikipedia.org/wiki/Java_hashCode()">Wikipedia entry for Java hashCode()</a>
     */
    public int hashCode() {
    	int prime = 17;
    	return polarity.hashCode() * prime +
    			content.hashCode() * prime * prime;
    }

}
