package algorithm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell {
	
	private int x,y; //location on the grid
	private Cell parent; //for draw the path
	private double h,f,g; //cost values to calc the path
	private boolean onPath, isTraverse; //boolean values for the algorithm
	
	public static boolean placement;
	public static MapState mapState;
	
	private Image image;
	private ImageView imageView;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.h = 0;
		this.g = 0;
		this.f = 0;
		this.parent = null;
		this.onPath = false;
		this.isTraverse = true;
		imageView = new ImageView();
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Cell getParent() {
		return parent;
	}

	public double getH() {
		return h;
	}

	public double getF() {
		return f;
	}

	public double getG() {
		return g;
	}

	public boolean isOnPath() {
		return onPath;
	}

	public boolean isTraverse() {
		return isTraverse;
	}

	public Image getImage() {
		return image;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setParent(Cell parent) {
		this.parent = parent;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void setF(double f) {
		this.f = f;
	}

	public void setG(double g) {
		this.g = g;
	}
	
	public void setOnPath(boolean onPath) {
		this.onPath = onPath;
	}

	public void setTraverse(boolean isTraverse) {
		this.isTraverse = isTraverse;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

	public void updateImageView() {
		imageView.setImage(image);
	}
	
	public void setConstraints(int x, int y) {
		imageView.setFitWidth(x);
		imageView.setFitHeight(y);
	}

}
