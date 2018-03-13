package pl.examples;

import java.util.ArrayList;

import pl.core.Biconditional;
import pl.core.Conjunction;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class MoreLiarsTruthTellersKB extends KB {
	
	public MoreLiarsTruthTellersKB () {
		super();
		Symbol a = intern("Amy is a truth-teller.");
		Symbol b = intern("Bob is a truth-teller.");
		Symbol c = intern("Cal is a truth-teller.");
		Symbol d = intern("Dee is a truth-teller.");
		Symbol e = intern("Eli is a truth-teller.");
		Symbol f = intern("Fay is a truth-teller.");
		Symbol g = intern("Gil is a truth-teller.");
		Symbol h = intern("Hal is a truth-teller.");
		Symbol i = intern("Ida is a truth-teller.");
		Symbol j = intern("Jay is a truth-teller.");
		Symbol k = intern("Kay is a truth-teller.");
		Symbol l = intern("Lee is a truth-teller.");
		
		add(new Biconditional(a, new Conjunction(h, i)));
		add(new Biconditional(b, new Conjunction(a, l)));
		add(new Biconditional(c, new Conjunction(b, g)));
		add(new Biconditional(d, new Conjunction(e, l)));
		add(new Biconditional(e, new Conjunction(c, h)));
		add(new Biconditional(f, new Conjunction(d, i)));
		add(new Biconditional(g, new Conjunction(new Negation(e), new Negation(j))));
		add(new Biconditional(h, new Conjunction(new Negation(f), new Negation(k))));
		add(new Biconditional(i, new Conjunction(new Negation(g), new Negation(k))));
		add(new Biconditional(j, new Conjunction(new Negation(a), new Negation(c))));
		add(new Biconditional(k, new Conjunction(new Negation(d), new Negation(f))));
		add(new Biconditional(l, new Conjunction(new Negation(b), new Negation(j))));
	}

	public static void main(String[] argv) {
		new MoreLiarsTruthTellersKB().dump();
		
		MoreLiarsTruthTellersKB mlttkb = new MoreLiarsTruthTellersKB();
		ArrayList<Sentence> queries = new ArrayList<Sentence>();
		queries.add(mlttkb.intern("Amy is a truth-teller."));
		queries.add(mlttkb.intern("Bob is a truth-teller."));
		queries.add(mlttkb.intern("Cal is a truth-teller."));
		queries.add(mlttkb.intern("Dee is a truth-teller."));
		queries.add(mlttkb.intern("Eli is a truth-teller."));
		queries.add(mlttkb.intern("Fay is a truth-teller."));
		queries.add(mlttkb.intern("Gil is a truth-teller."));
		queries.add(mlttkb.intern("Hal is a truth-teller."));
		queries.add(mlttkb.intern("Ida is a truth-teller."));
		queries.add(mlttkb.intern("Jay is a truth-teller."));
		queries.add(mlttkb.intern("Kay is a truth-teller."));
		queries.add(mlttkb.intern("Lee is a truth-teller."));
		
		System.out.println("\n");
		
		System.out.println("Test using model checking:");
		for (Sentence s : queries) {
			TTEnum ttenum = new TTEnum();
			System.out.println(s + " : " + ttenum.TT_Entails(mlttkb, s));
		}
		
		System.out.println("\n");
		
		System.out.println("Test using propositional inference:");
		for (Sentence s : queries) {
			PLProver plprover = new PLProver();
			System.out.println(s + " : " + plprover.entails(mlttkb, s));
		}
	}
}
