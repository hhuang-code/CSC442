package control;

import model.State;
import model.Action;

import java.util.ArrayList;

public class Computer {
	
	// take the computer's input and its role, and return an action
	public static Action play(State currentState, int role) {
		// copy current state
		State state = new State(currentState);

		state.setMaxDepth(7);
		
		return alphaBetaSearch(state, role);
	}
	
	private static Action alphaBetaSearch(State state, int role) {
		int evaluation = Integer.MIN_VALUE;
 		Action bestAction = null;
		ArrayList<Action> actions = state.getAvlActions();	// all applicable actions for this state
 		for (Action a : actions) {
 			State s = new State(state);
 			s.update(a);
			int curEvaluation = minValue(s, role, Integer.MIN_VALUE, Integer.MAX_VALUE);
			if (curEvaluation > evaluation) {
				evaluation = curEvaluation;
 				bestAction = a;
 			}
 		}

 		return bestAction;
	}
	
	private static int maxValue(State state, int role, int alpha, int beta) {
		if (state.isCutoff()) {
			state.calEvaluation();
			return role == 1 ? state.getxEvaluation() : state.getoEvaluation();	// computer's role is X
		} else {
			int evaluation = Integer.MIN_VALUE;
			ArrayList<Action> actions = state.getAvlActions();
			for (Action a : actions) {
				State s = new State(state);
				s.update(a);
				evaluation = Math.max(evaluation, minValue(s, role, alpha, beta));
				if (evaluation >= beta) {
					return evaluation;
				}
				alpha = Math.max(alpha, evaluation);
			}
			
			return evaluation;
		}
	}
	
	private static int minValue(State state, int role, int alpha, int beta) {
		if (state.isCutoff()) {
			state.calEvaluation();
			return role == 1 ? state.getxEvaluation() : state.getoEvaluation();	// computer's role is O
		} else {
			int evaluation = Integer.MAX_VALUE;
			ArrayList<Action> actions = state.getAvlActions();
			for (Action a : actions) {
				State s = new State(state);
				s.update(a);
				evaluation = Math.min(evaluation, maxValue(s, role, alpha, beta));
				if (evaluation <= alpha) {
					return evaluation;
				}
				beta = Math.min(beta, evaluation);
			}
			
			return evaluation;
		}
	}

}
