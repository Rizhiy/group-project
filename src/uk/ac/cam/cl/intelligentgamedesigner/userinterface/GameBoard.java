package uk.ac.cam.cl.intelligentgamedesigner.userinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import uk.ac.cam.cl.intelligentgamedesigner.coregame.CandyType;
import uk.ac.cam.cl.intelligentgamedesigner.coregame.Cell;
import uk.ac.cam.cl.intelligentgamedesigner.coregame.CellType;

public abstract class GameBoard extends JComponent {

	protected int tile_size;
	protected int width;
	protected int height;
	protected Cell[][] board;
	
	public GameBoard(int width, int height) {
		super();
		
		this.width = width;
		this.height = height;
		board = new Cell[width][height];

		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				board[x][y] = new Cell(CellType.UNUSABLE);
			}
		}
		
		tile_size = InterfaceManager.screenHeight()/15;
		
		setPreferredSize(new Dimension(width*tile_size,height*tile_size));
		
	}
	
	public Cell[][] getBoard(){
		return board;
	}
	
	public void clearBoard(){
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				board[x][y] = new Cell(CellType.UNUSABLE);				
			}
		}
	}

	//drawing the screen
	private void draw_cell(int x, int y, Graphics g){
		
		//draw the cell
		switch(board[x][y].getCellType()){
		case UNUSABLE:
			g.setColor(Color.GRAY);
			break;
		case ICING:
			g.setColor(Color.WHITE);
			break;
		case LIQUORICE:
			g.setColor(Color.BLACK);
			break;
		case DONT_CARE:
			g.setColor(Color.PINK);
			break;
		default:
			g.setColor(Color.LIGHT_GRAY);
			break;
		}
		g.fillRect(x*tile_size, y*tile_size, tile_size, tile_size);
		
		//draw the jelly
		int jelly_level = board[x][y].getJellyLevel();
		if(jelly_level>0){
			g.setColor(Color.CYAN);
			g.fillOval(x*tile_size, y*tile_size, tile_size, tile_size);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(jelly_level), x*tile_size + tile_size/2, y*tile_size + tile_size/2);
		}
		
		//draw the ingredients
		if(board[x][y].getCandy()!=null &&
				board[x][y].getCandy().getCandyType() == CandyType.INGREDIENT){
			g.setColor(Color.RED);
			g.fillOval(x*tile_size, y*tile_size, tile_size, tile_size);			
		}
		
		//outline the tiles
		g.setColor(Color.BLACK);
		g.drawRect(x*tile_size, y*tile_size, tile_size, tile_size);		
	}
	
	public void paint(Graphics g){
		setBorder(BorderFactory.createLineBorder(Color.black));
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				draw_cell(x,y,g);
			}
		}
	}
	
	public void minimumBoundingBox() {
		int min_x = width-1;
		int min_y = height-1;
		int max_x = 0;
		int max_y = 0;
		
		//scan for min and max pixels
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				if(board[x][y].getCellType() != CellType.UNUSABLE){
					if(x<min_x)min_x = x;
					if(y<min_y)min_y = y;
					if(x>max_x)max_x = x;
					if(y>max_y)max_y = y;
				}
			}
		}
		
		//make new board from this
		width = max_x - min_x + 1;
		height = max_y - min_y + 1;
		if(width<5){
			width = 5;
			if(min_x>5)min_x = 5;
		}
		if(height<5){
			height = 5;
			if(min_y>5)min_y = 5;
		}
		Cell[][] new_board = new Cell[width][height];
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				new_board[x][y] = board[x+min_x][y+min_y];
			}
		}
		
		//replace the board
		board = new_board;
	}
}
