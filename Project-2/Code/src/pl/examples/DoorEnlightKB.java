package pl.examples;

import java.util.ArrayList;

import pl.core.Biconditional;
import pl.core.Conjunction;
import pl.core.Disjunction;
import pl.core.Implication;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class DoorEnlightKB extends KB {
	
	public DoorEnlightKB (int version) {
		Symbol x = intern("X is a good door.");
		Symbol y = intern("Y is a good door.");
		Symbol z = intern("Z is a good door.");
		Symbol w = intern("W is a good door.");
		
		Symbol a = intern("A is a knight.");
		Symbol b = intern("B is a knight.");
		Symbol c = intern("C is a knight.");
		Symbol d = intern("D is a knight.");
		Symbol e = intern("E is a knight.");
		Symbol f = intern("F is a knight.");
		Symbol g = intern("G is a knight.");
		Symbol h = intern("H is a knight.");
		
		
		if (version == 1) {
			add(new Biconditional(a, x));
			add(new Biconditional(b, new Disjunction(y, z)));
			add(new Biconditional(c, new Conjunction(a, b)));
			add(new Biconditional(d, new Conjunction(x, y)));
			add(new Biconditional(e, new Conjunction(x, z)));
			add(new Biconditional(f, new Disjunction(new Conjunction(d, new Negation(e)), new Conjunction(e, new Negation(d)))));
			add(new Biconditional(g, new Implication(c, f)));
			add(new Biconditional(h, new Implication(new Conjunction(g, h), a)));
		} else if (version == 2) {
			add(new Biconditional(a, x));
			add(a);
			add(new Biconditional(h, new Implication(new Conjunction(g, h), a)));
		} else {
			System.out.println("No such case!");
		}
	}

	public static void main(String[] args) {
		
		System.out.println("Case 6-(a):");
		DoorEnlightKB dfkb = new DoorEnlightKB(1);
		dfkb.dump();
		ArrayList<Sentence> queries = new ArrayList<Sentence>();
		queries.add(dfkb.intern("X is a good door."));
		queries.add(dfkb.intern("Y is a good door."));
		queries.add(dfkb.intern("Z is a good door."));
		queries.add(dfkb.intern("W is a good door."));
		
		System.out.println("\n");
		
		System.out.println("Test using model checking:");
		for (Sentence s : queries) {
			TTEnum ttenum = new TTEnum();
			System.out.println(s + " : " + ttenum.TT_Entails(dfkb, s));
		}
		
		System.out.println("\n");
		
		System.out.println("Test using propositional inference:");
		for (Sentence s : queries) {
			PLProver plprover = new PLProver();
			System.out.println(s + " : " + plprover.entails(dfkb, s));
		}
		
		System.out.println("\n--------------------------------------------------------------");
		
		System.out.println("\nCase 6-(b):");
		dfkb = new DoorEnlightKB(2);
		dfkb.dump();
		queries = new ArrayList<Sentence>();
		queries.add(dfkb.intern("X is a good door."));
		queries.add(dfkb.intern("Y is a good door."));
		queries.add(dfkb.intern("Z is a good door."));
		queries.add(dfkb.intern("W is a good door."));
		
		System.out.println("\n");
		
		System.out.println("Test using model checking:");
		for (Sentence s : queries) {
			TTEnum ttenum = new TTEnum();
			System.out.println(s + " : " + ttenum.TT_Entails(dfkb, s));
		}
		
		System.out.println("\n");
		
		System.out.println("Test using propositional inference:");
		for (Sentence s : queries) {
			PLProver plprover = new PLProver();
			System.out.println(s + " : " + plprover.entails(dfkb, s));
		}
	}

}
