package pl.examples;

import pl.core.*;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class ModusPonensKB extends KB {
	
	public ModusPonensKB() {
		super();
		Symbol p = intern("P");
		Symbol q = intern("Q");
		add(p);
		add(new Implication(p, q));
	}
	
	public static void main(String[] argv) {
		new ModusPonensKB().dump();
		
		ModusPonensKB mpkb = new ModusPonensKB();
		Sentence alpha = new Symbol("Q");
		
		System.out.println("\n");
		
		TTEnum ttenum = new TTEnum();
		System.out.println("Test using model checking:");
		System.out.println(alpha + " is " + ttenum.TT_Entails(mpkb, alpha));
		
		System.out.println("\n");
		
		PLProver plprover = new PLProver();
		System.out.println("Test using propositional inference:");
		System.out.println(alpha + " is " + plprover.entails(mpkb, alpha));
	}

}
