package pl.examples;
import pl.core.*;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class HornClauseKB extends KB {
			
		public HornClauseKB() {
			super();
			Symbol myth = intern("Unicorn is mythical");
			Symbol mor = intern("Unicorn is mortal");
			Symbol mam = intern("Unicorn is mammal");
			Symbol horn = intern("Unicorn is horned");
			Symbol magic = intern("Unicorn is magical");
			
			add(new Implication(myth, new Negation(mor)));
			add(new Implication(new Negation(myth), new Conjunction(mor, mam)));
			add(new Implication(new Disjunction(new Negation(mor), mam), horn));
			add(new Implication(horn,magic));
		}
		
		public static void main(String[] argv) {
			new HornClauseKB().dump();
			
			HornClauseKB hckb = new HornClauseKB();
			Sentence q1 = new Symbol("Unicorn is mythical");
			Sentence q2 = new Symbol("Unicorn is magical");
			Sentence q3 = new Symbol("Unicorn is horned");
			
			System.out.println("\n");
			
			TTEnum ttenum = new TTEnum();
			System.out.println("Test using model checking:");
			System.out.println(q1 + " : " + ttenum.TT_Entails(hckb, q1));
			System.out.println(q2 + " : " + ttenum.TT_Entails(hckb, q2));
			System.out.println(q3 + " : " + ttenum.TT_Entails(hckb, q3));
			
			System.out.println("\n");
			
			PLProver plprover = new PLProver();
			System.out.println("Test using propositional inference:");
			System.out.println(q1 + " : " + plprover.entails(hckb, q1));
			System.out.println(q2 + " : " + plprover.entails(hckb, q2));
			System.out.println(q3 + " : " + plprover.entails(hckb, q3));
		}

	}

