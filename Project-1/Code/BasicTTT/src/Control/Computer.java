package control;

import model.State;
import model.Action;

import java.util.ArrayList;

public class Computer {
	
	// take the computer's input and its role, and return an action
	public static Action play(State currentState, int role) {
		// copy current state
		State state = new State(currentState);
		
		return minimaxDecision(state, role);
	} 
	
	private static Action minimaxDecision(State state, int role) {
		int utility = Integer.MIN_VALUE;
		Action bestAction = null;
		ArrayList<Action> actions = state.getAvlActions();	// all applicable actions for this state
		for (Action a : actions) {
			State s = new State(state);
			s.update(a);
			int curUtility = minValue(s, role);
			if (curUtility > utility) {
				utility = curUtility;
				bestAction = a;
			}
		}
		return bestAction;
	}
	
	private static int maxValue(State state, int role) {
		if (state.isTerminal()) {
			state.calUtility();
			return role == 1 ? state.getxUtility() : state.getoUtility();	// computer's role is X
		} else {
			int utility = Integer.MIN_VALUE;
			ArrayList<Action> actions = state.getAvlActions();
			for (Action a : actions) {
				State s = new State(state);
				s.update(a);
				utility = Math.max(utility, minValue(s, role));
			}
			
			return utility;
		}
	}
	
	private static int minValue(State state, int role) {
		if (state.isTerminal()) {
			state.calUtility();
			return role == 1 ? state.getxUtility() : state.getoUtility();	// computer's role is O
		} else {
			int utility = Integer.MAX_VALUE;
			ArrayList<Action> actions = state.getAvlActions();
			for (Action a : actions) {
				State s = new State(state);
				s.update(a);
				utility = Math.min(utility, maxValue(s, role));
			}
			
			return utility;
		}
	}

}
