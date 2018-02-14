package model;

public class Action {

	private int gridPos;//which grid will the action take
	private int boardPos;//which position in the grid will the action take
	
	public Action(){
		setGridPos(0);	// set to invalid positions by default
		setBoardPos(0);
	}

	public Action(int gridPos, int boardPos) {
		this.setGridPos(gridPos);
		this.setBoardPos(boardPos);
	}

	public Action(Action act) {
		gridPos = act.getGridPos();
		boardPos = act.getBoardPos();
	}
	
	public int getGridPos() {
		return gridPos;
	}

	public void setGridPos(int gridPos) {
		this.gridPos = gridPos;
	}

	public int getBoardPos() {
		return boardPos;
	}

	public void setBoardPos(int boardPos) {
		this.boardPos = boardPos;
	}
	
	public boolean validate() {
		if(gridPos>0 && gridPos<10) {
			if (boardPos>0 && boardPos<10) {
				return true;
			}
		}
		return false;
	}
	
}
