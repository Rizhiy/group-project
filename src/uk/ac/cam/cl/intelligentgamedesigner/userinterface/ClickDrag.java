package uk.ac.cam.cl.intelligentgamedesigner.userinterface;

import java.util.TimerTask;

public class ClickDrag extends TimerTask{
	
	private CustomBoard board;
	
	public ClickDrag(CustomBoard observer){
		board = observer;
	}

	@Override
	public void run() {
		board.changeTile();
	}

}