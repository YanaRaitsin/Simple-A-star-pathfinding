package astarfx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import algorithm.AStar;
import algorithm.Cell;
import algorithm.MapState;
import input.MouseHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class AStarVisualizationController {
	/*TODO - another screen when opening the a star algo (with instructions)
	 * another screen with "no path found"*/
	
	public static int startXPos, startYPos;
	public static int endXPos, endYPos;
	
	private static Cell[][] cellGrid;
	
	private GridPane root;
	private Scene pathScene;
	
	public void initVisualization() {
		root = new GridPane();
		root.setGridLinesVisible(true);
		
		cellGrid = new Cell[Settings.NUMBER_OF_ROWS][Settings.NUMBER_OF_COLS];
		
		Cell.placement = true;
		Cell.mapState = MapState.PLACE_OBSTACLE;
		
		//Col and row constraints - the size of col and row need to remain the same.
		for(int i=0; i<Settings.NUMBER_OF_COLS; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0 / Settings.NUMBER_OF_COLS);
			root.getColumnConstraints().add(colConst);
		}
		
		for(int i=0; i<Settings.NUMBER_OF_ROWS; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0 / Settings.NUMBER_OF_ROWS);
			root.getRowConstraints().add(rowConst);
		}
		
		try {
			initCellGrid();
		} catch (FileNotFoundException e) {
			Logger.getLogger(AStarVisualizationController.class.getName()).log(Level.SEVERE, null, e);
		}
		
		AStar.calcHeuristic(cellGrid);
		
		pathScene = new Scene(root, Settings.SCENE_X_POS, Settings.SCENE_Y_POS);
		
		/*
		 * Keys:
		 * R - reset all the screen
		 * S - to place the starting point
		 * E - to place the ending point
		 * O - to place the obstacle
		 * ENTER - to start the algorithm*/
		
		pathScene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.S) {
				if(Cell.mapState.equals(MapState.PLACE_OBSTACLE))
					Cell.mapState = MapState.PLACE_START;
				if(Cell.mapState.equals(MapState.PLACE_END))
					Cell.mapState = MapState.PLACE_START;
			}
			
			if(e.getCode() == KeyCode.O) {
				if(Cell.mapState.equals(MapState.PLACE_START))
					Cell.mapState = MapState.PLACE_OBSTACLE;
				if(Cell.mapState.equals(MapState.PLACE_END))
					Cell.mapState = MapState.PLACE_OBSTACLE;
			}
			
			if(e.getCode() == KeyCode.E) {
				if(Cell.mapState.equals(MapState.PLACE_OBSTACLE))
					Cell.mapState = MapState.PLACE_END;
				if(Cell.mapState.equals(MapState.PLACE_START))
					Cell.mapState = MapState.PLACE_END;
			}
			
			if(e.getCode() == KeyCode.ENTER) {
				AStar.runAStarAlgorithm(cellGrid, startXPos, startYPos, endXPos, endYPos);
				try {
					drawPath();
				} catch (FileNotFoundException e1) {
					Logger.getLogger(AStarVisualizationController.class.getName()).log(Level.SEVERE, null, e1);
				}
			}
			
			if(e.getCode() == KeyCode.R) {
				try {
					reset();
				} catch (FileNotFoundException e1) {
					Logger.getLogger(AStarVisualizationController.class.getName()).log(Level.SEVERE, null, e1);
				}
				AStar.calcHeuristic(cellGrid);
			}
		});
	}
	
	private void initCellGrid() throws FileNotFoundException {
		for(int i=0; i<Settings.NUMBER_OF_COLS; i++) {
			for(int j=0; j<Settings.NUMBER_OF_ROWS; j++) {
				cellGrid[i][j] = new Cell(i, j);
				
				if(i==0 && j==0) {
					startXPos = 0;
					startYPos = 0;
					InputStream startPoint = new FileInputStream("assets\\start.png");
					Image image = new Image(startPoint);
					cellGrid[i][j].setImage(image);
					cellGrid[i][j].updateImageView();
					MouseHandler.createMouseListener(cellGrid[i][j], cellGrid);
					
					cellGrid[i][j].setConstraints(Settings.X_PIXEL_PER_TILE, Settings.Y_PIXEL_PER_TILE);
					root.add(cellGrid[i][j].getImageView(), i, j);
					continue;
				}
				
				if(i == Settings.NUMBER_OF_COLS - 1 && j == Settings.NUMBER_OF_ROWS - 1) {
					endXPos = Settings.NUMBER_OF_COLS - 1;
					endYPos = Settings.NUMBER_OF_ROWS - 1;
					InputStream endPoint = new FileInputStream("assets\\end.png");
					Image image = new Image(endPoint);
					cellGrid[i][j].setImage(image);
					cellGrid[i][j].updateImageView();
					MouseHandler.createMouseListener(cellGrid[i][j], cellGrid);
					
					cellGrid[i][j].setConstraints(Settings.X_PIXEL_PER_TILE, Settings.Y_PIXEL_PER_TILE);
					root.add(cellGrid[i][j].getImageView(), i, j);
					continue;
				}
				
				InputStream wallpaper = new FileInputStream("assets\\wallpaper.png");
				Image image = new Image(wallpaper);
				cellGrid[i][j].setImage(image);
				cellGrid[i][j].updateImageView();
				MouseHandler.createMouseListener(cellGrid[i][j], cellGrid);
				cellGrid[i][j].setConstraints(Settings.X_PIXEL_PER_TILE, Settings.Y_PIXEL_PER_TILE);
				root.add(cellGrid[i][j].getImageView(), i, j);
			}
		}
	}
	
	public static void placeInOldLoc(int tempX, int tempY) throws FileNotFoundException {
		InputStream wallpaper = new FileInputStream("assets\\wallpaper.png");
        Image image = new Image(wallpaper);
        cellGrid[tempX][tempY].setTraverse(true);
        cellGrid[tempX][tempY].setImage(image);
        cellGrid[tempX][tempY].updateImageView();
	}

	public Scene getPathScene() {
		return pathScene;
	}
	
	private void reset() throws FileNotFoundException {
		if(!Cell.placement)
			return;
		
		for(int i=0; i<Settings.NUMBER_OF_ROWS; i++) {
			for(int j=0; j<Settings.NUMBER_OF_COLS; j++) {
				if(i==0 && j==0) {
					startXPos = 0;
					startYPos = 0;
					InputStream startPoint = new FileInputStream("assets\\start.png");
					Image image = new Image(startPoint);
					cellGrid[i][j].setImage(image);
					cellGrid[i][j].updateImageView();
					cellGrid[i][j].setTraverse(true);
					cellGrid[i][j].setParent(null);
					continue;
				}
				
				if(i == Settings.NUMBER_OF_ROWS - 1 && j == Settings.NUMBER_OF_COLS - 1) {
					endXPos = Settings.NUMBER_OF_ROWS - 1;
					endYPos = Settings.NUMBER_OF_COLS - 1;
					InputStream endPoint = new FileInputStream("assets\\end.png");
					Image image = new Image(endPoint);
					cellGrid[i][j].setImage(image);
					cellGrid[i][j].updateImageView();
					cellGrid[i][j].setTraverse(true);
					cellGrid[i][j].setParent(null);
					continue;
				}
				
				InputStream wallpaper = new FileInputStream("assets\\wallpaper.png");
				Image image = new Image(wallpaper);
				cellGrid[i][j].setImage(image);
				cellGrid[i][j].updateImageView();
				cellGrid[i][j].setTraverse(true);
				cellGrid[i][j].setParent(null);
			}
		}
	}
	
	private void drawPath() throws FileNotFoundException {
        Cell start = cellGrid[endXPos][endYPos];
        start = start.getParent();
        if(start == null){System.out.println("NO PATH FOUND! :D");} //TODO - create an window.
        while(start != null){
            int x = start.getX();
            int y = start.getY();
            InputStream input = new FileInputStream("assets\\path.png");
            Image path = new Image(input);
            cellGrid[x][y].setImage(path);
            cellGrid[x][y].updateImageView();
            start = start.getParent();
        }
	}
}
