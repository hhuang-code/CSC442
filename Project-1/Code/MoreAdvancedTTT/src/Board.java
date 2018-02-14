package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	
	private int[][] board;		// set all position to 0 (empty) by default
	private int gridIdx;		// 1~9, set to 0 by default
	private int xEvaluation;	// the higher the value, the more likely X will win; set to 0 by default
	private int oEvaluation;	// the higher the value, the more likely O will win; set to 0 by default
	
	// default constructor
	public Board() {
		board = new int[3][3];
		// initialize board
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = 0;
			}
		}
		// initialize gridIdx
		gridIdx = 0;	// set to an invalid value by default
		// initialize evaluation value
		xEvaluation = 0;
		oEvaluation = 0;

	}
	
	// copy constructor
	public Board(Board b) {
		board = new int[3][3];
		for (int i = 0; i < b.getBoard().length; i++) {
			board[i] = Arrays.copyOf(b.getBoard()[i], b.getBoard()[i].length);
		}
		gridIdx = b.getGridIdx();
		xEvaluation = b.getxEvaluation();
		oEvaluation = b.getoEvaluation();

	}
	
	// check whether an action is valid
	public boolean isValidAction(Action action) {
		int gridPos = action.getGridPos();
		if (gridPos != gridIdx) {
			return false;
		}
		
		int boardPos = action.getBoardPos();
		int row = (boardPos - 1) / 3;
		int col = (boardPos - 1) % 3;
		
		return board[row][col] == 0 ? true : false;
	}
	
	// get all applicable actions under this state
	public ArrayList<Action> getAvlActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {	// empty position
					actions.add(new Action(gridIdx, 3 * i + j + 1));
				}
			}
		}
		
		return actions;
	}
	
	// update state according to the action and the turn
	public void update (Action action, int turn) {
		int pos = action.getBoardPos();
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

	// check whether there's a winner at current state
	public boolean findWinner() {
		if (getRowSum(0) == 3 || getRowSum(1) == 3 || getRowSum(2) == 3 || getColSum(0) == 3 || getColSum(1) == 3 || getColSum(2) == 3 ||
					board[0][0] + board[1][1] + board[2][2] == 3 || board[0][2] + board[1][1] + board[2][0] == 3 ||
					getRowSum(0) == -3 || getRowSum(1) == -3 || getRowSum(2) == -3 || getColSum(0) == -3 || getColSum(1) == -3 || getColSum(2) == -3 ||
					board[0][0] + board[1][1] + board[2][2] == -3 || board[0][2] + board[1][1] + board[2][0] == -3) {
			return true;
		} else {
			return false;
		}
	}

	// check whether it is a tie
	public boolean isTie() {
		return isFull() & !findWinner();
	}
	
	// check whether this state is terminal
	public boolean isTerminal() {
		return isFull() || findWinner();
	}
	
	// compute evaluation value
	public void calEvaluation() {
		int xTmp = 0;
		int oTmp = 0;
		
		// compute row value
		for (int i = 0; i < 3; i++) {
			switch(getRowSum(i)) {
				case 3:
					xTmp += 100;	
					break;
				case 2:
					xTmp += 1;	
					break;
				case -3:
					xTmp -= -100;	
					break;
				case -2:
					xTmp -= -1;
					break;
				default:
					break;
			}
		}
		
		// compute column value
		for (int i = 0; i < 3; i++) {
			switch(getColSum(i)) {
				case 3:
					xTmp += 100;
					break;
				case 2:
					xTmp += 1;
					break;
				case -3:
					xTmp -= 100;
					break;
				case -2:
					xTmp -= 1;
					break;
				default:
					break;
			}
		}
		
		// compute diagonal value
		switch(board[0][0] + board[1][1] + board[2][2]) {
			case 3:
				xTmp += 100;
				break;
			case 2:
				xTmp += 1;
				break;
			case -3:
				xTmp -= 100;
				break;
			case -2:
				xTmp -= 1;
				break;
			default:
				break;
		}

		// compute inverse diagonal value
		switch(board[0][2] + board[1][1] + board[2][0]) {
			case 3:
				xTmp += 100;
				break;
			case 2:
				xTmp += 1;
				break;
			case -3:
				xTmp -= 100;
				break;
			case -2:
				xTmp -= 1;
				break;
			default:
				break;
		}
		
		xTmp += (getxCnt() - getoCnt()) * 5;
		
		xEvaluation = xTmp - oTmp;
		oEvaluation = oTmp - xTmp;
	}

	// getters and setters
	public int[][] getBoard() {
		return board;
	}

	public int getGridIdx() {
		return gridIdx;
	}

	public void setGridIdx(int gridIdx) {
		this.gridIdx = gridIdx;
	}

	public int getxEvaluation() {
		return xEvaluation;
	}
	
	public int getoEvaluation() {
		return oEvaluation;
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
	
	private int getxCnt() {
		int cnt = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					cnt++;
				}
			}
		}
		
		return cnt;
	}
	
	private int getoCnt() {
		int cnt = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == -1) {
					cnt++;
				}
			}
		}
		
		return cnt;
	}

}
