package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Action;
import model.State;

public class Interaction {
	public static int selectRole() {
		System.err.println("Welcome to Tic-Tac-Toe");
		System.err.println("Please select your side (  X or O ):");
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			if(stdIn.readLine().toLowerCase().contains("x")) {
				return 1;
			}
			else {
				return -1;
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
			return 0;
		} catch (IOException e) {

			e.printStackTrace();
			return 0;
		}
	}
	public static void displayBoard(State state) {
		System.err.println("The current board status is:");
		String board_dis = "";
		int[][] board = state.getBoard();
		//The default of board size is 3*3
		for(int i=0;i<3;i++) {
			board_dis+="-------\n";
			for (int j=0;j<3;j++) {
				board_dis+="|";
				if(board[i][j]==1) {
					board_dis+="X";
				}else if(board[i][j]==-1){
					board_dis+="O";
				}else {
					board_dis+=" ";
				}
			}
			board_dis+="|\n";
		}
		board_dis+="-------\n";
		System.err.println(board_dis);

	}
	
	public static Action play()  {
		System.err.println("Please select your next move:");
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			String input = stdIn.readLine();
			String[] figures=input.split(" ");
			if(figures.length>=1) {
				return new Action(Integer.parseInt(figures[0]));
				
			}else {
				return new Action(0);
			}
			
			
		} catch (NumberFormatException e) {
			return new Action(0);
		} catch (IOException e) {

			return new Action(0);
		}
		
	}
	
	public static void displayMsg(String msg) {
		System.err.println(msg);
	}
	
	public static void displayMove(Action act) {
		System.out.println(act.getPosition());
	}
}
