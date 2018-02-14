// written by my partner Wei Zhang

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Action;
import model.Board;
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
		String board_dis = "-----------------------\n";
		Board[][] grids = state.getGrid();
		//The default of board size is 9*9
		for(int i=0;i<3;i++) {//iterate over each row of grids
			board_dis+="|---------------------|\n";
			for (int j=0;j<3;j++) {//iterate over each row in the grids
				board_dis+="|";
				for (int k=0;k<3;k++) {//iterate over each grids in a row
					int[][] grid = grids[i][k].getBoard();
					for (int l=0;l<3;l++) {//iterate over each column in the grids
						board_dis+="|";
						if(grid[j][l]==1) {
							board_dis+="X";
						}else if(grid[j][l]==-1){
							board_dis+="O";
						}else {
							board_dis+=" ";
						}
					}
					board_dis+="|";
				}
				board_dis+="|\n";
			}

		}
		
		board_dis+="|---------------------|\n-----------------------";
		System.err.println(board_dis);

	}
	
	public static Action play()  {
		System.err.println("Please select your next move:");
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			String input = stdIn.readLine();
			String[] figures=input.split(" ");
			if(figures.length>=2)
			{
				int gridpos=Integer.parseInt(figures[0]);
				int boardpos=Integer.parseInt(figures[1]);
				return new Action(gridpos,boardpos);
			}
			return new Action(0,0);
			
			
		} catch (NumberFormatException e) {


			return new Action(0,0);
		} catch (IOException e) {


			return new Action(0,0);
		}
		
	}
	
	public static void displayMsg(String msg) {
		System.err.println(msg);
	}
	
	public static void displayMove(Action act) {
		System.out.print(act.getGridPos());
		System.out.print(" ");
		System.out.println(act.getBoardPos());
	}
}
