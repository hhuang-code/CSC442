package pl.core;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A KB is a set (actually a List) of Sentences and a SymbolTable
 * holding the PropositionalSymbols used in those sentences.
 */
public class KB {
	
	protected List<Sentence>sentences;
	protected SymbolTable symtab;
	
	public KB(List<Sentence> sentences, SymbolTable symtab) {
		this.sentences = sentences;
		this.symtab = symtab;
	}

	public KB() {
		this(new LinkedList<Sentence>(), new SymbolTable());
	}
	
	/**
	 * Return the Symbols interned in this KB's SymbolTable
	 * as a Collection.
	 */
	public Collection<Symbol> symbols() {
		return symtab.symbols();
	}

	/**
	 * Return this KB's Sentences as a Collection. 
	 */
	public Collection<Sentence> sentences() {
		return sentences;
	}
	
	/**
	 * Intern the given name in this KB's SymbolTable and return
	 * the corresponding Symbol.
	 */
	public Symbol intern(String name) {
		return symtab.intern(name);
	}
	
	/**
	 * Add the given Sentence to this KB.
	 */
	public void add(Sentence s) {
		sentences.add(s);
	}
	
	/**
	 * Print the contents of this KB to System.out.
	 */
	public void dump() {
		for (Sentence s : sentences()) {
			System.out.println(s);
		}
	}

}
