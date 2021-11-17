package algorithm;

import java.util.ArrayList;
import java.util.List;

import astarfx.AStarVisualizationController;
import astarfx.Settings;

public class AStar {
	private static List<Cell> open = new ArrayList<>(); 
	private static List<Cell> closed = new ArrayList<>();
	
	public static void runAStarAlgorithm(Cell[][] cellGrid, int startX, int startY, int endX, int endY) {
		Cell.placement = false;
		Cell start = cellGrid[startX][startY];
		Cell end = cellGrid[endX][endY];
		
		open.clear();
		closed.clear();
		
		resetCells(cellGrid);
		
		start.setF(start.getH());
		open.add(start);
		
		while(!open.isEmpty()) {
			setFOpenList();
			int cellToProcessIndex = findMinimum();
			Cell currentNode = open.get(cellToProcessIndex);
			
			if(currentNode.getX() == endX && currentNode.getY() == endY)
				break;
			
			List<Cell> sucessorList = generateSuccessorList(cellGrid, currentNode.getX(), currentNode.getY());
			
			for(Cell currentChild : sucessorList) {
				boolean isOpen = isOpenOrClosed(currentChild, open);
				boolean isClosed = isOpenOrClosed(currentChild, closed);
				
				double currentChildCost = currentNode.getG() + distBetweenNodes(currentNode, currentChild);
				
				if(isOpen)
					if(currentChild.getG() <= currentChildCost)
						continue;
				
				if(isClosed) {
					if(currentChild.getG() <= currentChildCost)
						continue;
					closed.remove(currentChild);
					open.add(currentChild);
				}
				
				if(!isOpen && !isClosed) {
					open.add(currentChild);
					double hDist = distBetweenNodes(end, currentChild);
					currentChild.setH(hDist);
				}
				
				currentChild.setG(currentChildCost);
				currentChild.setOnPath(true);
				currentChild.setParent(currentNode);
			}
			
			open.remove(currentNode);
			closed.add(currentNode);
		}
		
		Cell.placement = true;
	}
	
	private static void resetCells(Cell[][] cellGrid) {
		for(int i=0; i<Settings.NUMBER_OF_ROWS; i++) {
			for(int j=0; j<Settings.NUMBER_OF_COLS; j++) {
				cellGrid[i][j].setF(0.0);
				cellGrid[i][j].setG(0.0);
				cellGrid[i][j].setParent(null);
				cellGrid[i][j].setOnPath(false);
			}
		}
	}
	
	private static int findMinimum() {
		int min = 0;
		double data = open.get(0).getF();
		for(int i=0; i<open.size(); i++) {
			if(open.get(i).getF() < data) {
				min = i;
				data = open.get(i).getF();
			}
		}
		return min;
	}
	
	private static void setFOpenList() {
		for(Cell currentCell : open) {
			double h = currentCell.getH();
			double g = currentCell.getG();
			double f = h + g;
			currentCell.setF(f);
		}
	}
	
	private static List<Cell> generateSuccessorList(Cell[][] cellGrid, int x, int y) {
		List<Cell> list = new ArrayList<>();
		int tempX = 0, tempY = 0;
		
		//1
		tempX = x - 1;
		tempY = y + 1;
		checkPositions(cellGrid, list, tempX, tempY);
		
		//2
		tempX = x;
		tempY = y+1;
		checkPositions(cellGrid, list, tempX, tempY);
		
		//3
		tempX = x+1;
		tempY = y+1;
		checkPositions(cellGrid, list, tempX, tempY);
		
		//4
		tempX = x-1;
		tempY = y;
		checkPositions(cellGrid, list, tempX, tempY);
		
		//5
		tempX = x-1;
		tempY = y-1;
		checkPositions(cellGrid, list, tempX, tempY);
		
		//6
		tempX = x;
		tempY = y-1;
		checkPositions(cellGrid, list, tempX, tempY);
		
		//7
		tempX = x+1;
		tempY = y-1;
		checkPositions(cellGrid, list, tempX, tempY);
		
		//8
		tempX = x+1;
		tempY = y;
		checkPositions(cellGrid, list, tempX, tempY);
		
		return list;
	}
	
	private static void checkPositions(Cell[][] cellGrid, List<Cell> list, int tempX, int tempY) {
		if(tempX>=0 && tempX<=Settings.NUMBER_OF_ROWS - 1) {
			if(tempY>=0 && tempY<=Settings.NUMBER_OF_COLS-1)
				if(cellGrid[tempX][tempY].isTraverse())
					list.add(cellGrid[tempX][tempY]);
		}
	}
	
	private static boolean isOpenOrClosed(Cell cell, List<Cell> selectedList) {
		int x = cell.getX();
		int y = cell.getY();
		
		for(Cell currentCell : selectedList)
			if(x == currentCell.getX() && y == currentCell.getY())
				return true;
		return false;
	}
	
	private static double distBetweenNodes(Cell currentNode, Cell cell) {
		int x1 = currentNode.getX();
		int y1 = currentNode.getY();
		int x2 = cell.getX();
		int y2 = cell.getY();
		
		int xDist = x1 - x2;
		int yDist = y1 - y2;
		
		xDist = (int) Math.pow(2, xDist);
		yDist = (int) Math.pow(2, yDist);
		
		double inner = xDist + yDist;
		return Math.sqrt(inner);
	}
	
	public static void calcHeuristic(Cell[][] cellGrid) {
		for(int i=0; i<Settings.NUMBER_OF_ROWS; i++) {
			for(int j=0; j<Settings.NUMBER_OF_COLS; j++) {
				double hc = 0;
				double xDist = AStarVisualizationController.endXPos - i;
				double yDist = AStarVisualizationController.endYPos - j;
				xDist = Math.pow(2, xDist);
				yDist = Math.pow(2, yDist);
				double inner = xDist + yDist;
				
				hc = Math.sqrt(inner);
				cellGrid[i][j].setH(hc);
			}
		}
	}
}
