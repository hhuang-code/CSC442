package pl.sln;

import java.util.ArrayList;
import pl.core.KB;
import pl.core.Sentence;
import pl.core.Symbol;

public class TTEnum {
	
	public boolean TT_Entails (KB kb, Sentence alpha) {
		
		ArrayList<Symbol> symbols = new ArrayList<Symbol>(kb.symbols());
		
		return TT_Check_All(kb, alpha, symbols, 0, new TTModel());
	}

	public boolean TT_Check_All (KB kb, Sentence alpha, ArrayList<Symbol> symbols, int idx, TTModel model) {
		if (idx >= symbols.size()) {
			if (PL_True(kb, model)) {
				return PL_True(alpha, model);
			} else {
				return true;
			}
		} else {
			idx++;
			TTModel model_copy = new TTModel(model);
			model.set(symbols.get(idx - 1), true);
			model_copy.set(symbols.get(idx - 1), false);
			return TT_Check_All(kb, alpha, symbols, idx, model) && TT_Check_All(kb, alpha, symbols, idx, model_copy);
		}
	} 
	
	public boolean PL_True (KB kb, TTModel model) {
		return model.satisfies(kb);
	}
	
	public boolean PL_True (Sentence alpha, TTModel model) {
		return model.satisfies(alpha);
	}
	
}
