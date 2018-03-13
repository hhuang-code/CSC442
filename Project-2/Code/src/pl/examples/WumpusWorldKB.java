package pl.examples;

import pl.core.Biconditional;
import pl.core.Disjunction;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class WumpusWorldKB extends KB {
	
	public WumpusWorldKB() {
		super();
		Symbol p11 = intern("P1,1");
		Symbol p12 = intern("P1,2");
		Symbol p21 = intern("P2,1");
		Symbol p22 = intern("P2,2");
		Symbol p31 = intern("P3,1");
		Symbol b11 = intern("B1,1");
		Symbol b21 = intern("B2,1");

		add(new Negation(p11));
		add(new Biconditional(b11, new Disjunction(p12, p21)));
		add(new Biconditional(b21, new Disjunction(p12, new Disjunction(p22, p31))));
		add(new Negation(b11));
		add(b21);
	}

	public static void main(String[] argv) {
		new WumpusWorldKB().dump();
		
		WumpusWorldKB wwkb = new WumpusWorldKB();
		Sentence alpha = new Symbol("P1,2");
		
		System.out.println("\n");
		
		TTEnum ttenum = new TTEnum();
		System.out.println("Test using model checking:");
		System.out.println(alpha + " is " + ttenum.TT_Entails(wwkb, alpha));
		
		System.out.println("\n");
		
		PLProver plprover = new PLProver();
		System.out.println("Test using propositional inference:");
		System.out.println(alpha + " is " + plprover.entails(wwkb, alpha));
	}

}