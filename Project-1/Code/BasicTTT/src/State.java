package model;

import java.util.ArrayList;
import java.util.Arrays;

public class State {

	private int[][] board;	// set all position to 0 (empty) by default
	private int turn;		// +1 for X, -1 for O; set to 1 by default
	private int xUtility;	// +1 for win, -1 for lose, 0 for tie (in X view); set to 0 by default
	private int oUtility;	// +1 for win, -1 for lose, 0 for tie (in O view); set to 0 by default
	
	// default constructor
	public State() {
		board = new int[3][3];
		// initialize board
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = 0;
			}
		}
		// initialize turn
		turn = 1;
		// initialize utility
		xUtility = 0;
		oUtility = 0;

	}
	
	// copy constructor
	public State(State s) {
		board = new int[3][3];
		for (int i = 0; i < s.getBoard().length; i++) {
			board[i] = Arrays.copyOf(s.getBoard()[i], s.getBoard()[i].length);
		}
		turn = s.getTurn();
		xUtility = s.getxUtility();
		oUtility = s.getoUtility();

	}
	
	// check whether an action is valid
	public boolean isValidAction(Action action) {
		int pos = action.getPosition();
		int row = (pos - 1) / 3;
		int col = (pos - 1) % 3;
		
		return board[row][col] == 0 ? true : false;
	}
	
	// get all applicable actions under this state
	public ArrayList<Action> getAvlActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {	// empty position
					actions.add(new Action(3 * i + j + 1));
				}
			}
		}
		
		return actions;
	}
	
	// update state according to the action
	public void update (Action action) {
		int pos = action.getPosition();
		int row = (pos - 1) / 3;
		int col = (pos - 1) % 3;
		
		if (turn == 1) {
			board[row][col] = 1;
			turn = -1;
		} else if (turn == -1) {
			board[row][col] = -1;
			turn = 1;
		} else {
			try {
				throw new Exception("No such turn: " + turn);
			} catch (Exception e) {
				System.err.print(e.getMessage());
			}
		}
	}
	
	// check whether this state is terminal
	public boolean isTerminal() {
		if (isFull()) {
			return true;
		} else if (getRowSum(0) == 3 || getRowSum(1) == 3 || getRowSum(2) == 3 || getColSum(0) == 3 || getColSum(1) == 3 || getColSum(2) == 3 ||
					board[0][0] + board[1][1] + board[2][2] == 3 || board[0][2] + board[1][1] + board[2][0] == 3 ||
					getRowSum(0) == -3 || getRowSum(1) == -3 || getRowSum(2) == -3 || getColSum(0) == -3 || getColSum(1) == -3 || getColSum(2) == -3 ||
					board[0][0] + board[1][1] + board[2][2] == -3 || board[0][2] + board[1][1] + board[2][0] == -3) {
			return true;
		} else {
			return false;
		}
	}
	
	// compute utility when the state is terminal
	public void calUtility() {
		if (getRowSum(0) == 3 || getRowSum(1) == 3 || getRowSum(2) == 3 || getColSum(0) == 3 || getColSum(1) == 3 || getColSum(2) == 3 ||
				board[0][0] + board[1][1] + board[2][2] == 3 || board[0][2] + board[1][1] + board[2][0] == 3) {
			xUtility = 1;	// X win
			oUtility = -1;	// O win
		} else if (getRowSum(0) == -3 || getRowSum(1) == -3 || getRowSum(2) == -3 || getColSum(0) == -3 || getColSum(1) == -3 || getColSum(2) == -3 ||
				board[0][0] + board[1][1] + board[2][2] == -3 || board[0][2] + board[1][1] + board[2][0] == -3) {
			xUtility = -1;
			oUtility = 1;
		} else {
			xUtility = 0;
			oUtility = 0;
		}
	}

	// getters
	public int[][] getBoard() {
		return board;
	}

	public int getTurn() {
		return turn;
	}

	public int getxUtility() {
		return xUtility;
	}
	
	public int getoUtility() {
		return oUtility;
	}
	
	// private functions
	private int getRowSum(int row) {
		return board[row][0] + board[row][1] + board[row][2];
	}
	
	private int getColSum(int col) {
		return board[0][col] + board[1][col] + board[2][col];
	}
	
	// check whether the board is full
	private boolean isFull() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
}
