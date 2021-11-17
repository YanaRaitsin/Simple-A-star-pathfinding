package astarfx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import algorithm.AStar;
import algorithm.Cell;
import javafx.scene.image.Image;

public class CellUtil {

	public static void placeWallpaperOrObstacle(Cell cell, String fileName) throws FileNotFoundException {
		int x = cell.getX();
		int y = cell.getY();
		
		if(x == AStarVisualizationController.startXPos && y == AStarVisualizationController.startYPos)
			if(x == AStarVisualizationController.endXPos && y == AStarVisualizationController.endYPos)
				return;
		
		if(fileName.equals("obstacle"))
			cell.setTraverse(false);
		else
			cell.setTraverse(true); //is wallpaper and not obstacle
		
		InputStream input = new FileInputStream("assets\\"+fileName+".png");
		Image image = new Image(input);
		cell.setImage(image);
		cell.updateImageView();
	}
	
	public static void placeEndPoint(Cell cell, Cell[][] cellGrid) throws FileNotFoundException {
		int x = cell.getX();
		int y = cell.getY();
		
		if(x == AStarVisualizationController.startXPos && y == AStarVisualizationController.startYPos)
			if(x == AStarVisualizationController.endXPos && y == AStarVisualizationController.endYPos)
				return;
		
		int tempX = AStarVisualizationController.endXPos;
		int tempY = AStarVisualizationController.endYPos;
		
		AStarVisualizationController.endXPos = x;
		AStarVisualizationController.endYPos = y;
		
		InputStream endPoint = new FileInputStream("assets\\end.png");
		Image image = new Image(endPoint);
		cell.setImage(image);
		cell.updateImageView();
		cell.setTraverse(true);
		AStarVisualizationController.placeInOldLoc(tempX, tempY);
		AStar.calcHeuristic(cellGrid);
	}
	
	public static void placeStartPoint(Cell cell, Cell[][] cellGrid) throws FileNotFoundException {
		int x = cell.getX();
		int y = cell.getY();
		
		if(x == AStarVisualizationController.startXPos && y == AStarVisualizationController.startYPos)
			if(x == AStarVisualizationController.endXPos && y == AStarVisualizationController.endYPos)
				return;
		
		int tempX = AStarVisualizationController.startXPos;
		int tempY = AStarVisualizationController.startYPos;
		
		AStarVisualizationController.startXPos = x;
		AStarVisualizationController.startYPos = y;
		InputStream startPoint = new FileInputStream("assets\\start.png");
		Image image = new Image(startPoint);
		cell.setImage(image);
		cell.updateImageView();
		cell.setTraverse(true);
		AStarVisualizationController.placeInOldLoc(tempX, tempY);
		AStar.calcHeuristic(cellGrid);
	}
}
