// written by my partner Wei Zhang

package model;



public class Action {



	private int position;

	/**
	 * @return position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param postion
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	public Action(Action act){
		this.setPosition(act.getPosition());
	}
	
	public Action(int position) {
		this.setPosition(position);
	}
	
	Action(){}
	public boolean validate() {
		if(position>0 && position<10) {
			return true;
			
		}
		return false;
	}
}
