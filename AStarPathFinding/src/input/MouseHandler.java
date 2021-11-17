package input;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import algorithm.Cell;
import astarfx.CellUtil;

public class MouseHandler {

	public static void createMouseListener(Cell cell, Cell[][] cellGrid) {
		cell.getImageView().setOnMousePressed(e -> {
			try {
				handleMousePress(cell, cellGrid);
			} catch(FileNotFoundException ex) {
				Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}
	
	public static void handleMousePress(Cell cell, Cell[][] cellGrid) throws FileNotFoundException {
		if(!Cell.placement)
			return;
		
		switch(Cell.mapState) {
			case PLACE_OBSTACLE:
				CellUtil.placeWallpaperOrObstacle(cell, "obstacle");
				break;
			case PLACE_WALLPAPER:
				CellUtil.placeWallpaperOrObstacle(cell, "wallpaper");
				break;
			case PLACE_START:
				CellUtil.placeStartPoint(cell, cellGrid);
				break;
			case PLACE_END:
				CellUtil.placeEndPoint(cell, cellGrid);
				break;
			default:
				System.err.println("Unsupported enum");
				System.exit(1);
		}
	}
}
