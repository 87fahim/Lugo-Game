package board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import main.LudoKing;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

public class Tile extends Pane {
	private final int CELL_SIZE;
	private Color color;
	private boolean isSafeTile;
	private int tileNumber;
	private FillTransition fill;
	private Shape shape;
	
	public void setTileShape(Shape s) {
		this.shape = s;
	}
	
	public Tile(int size){
		this.CELL_SIZE = size;
		this.color = LudoKing.BACKGROUND_COLOR;
		this.isSafeTile = false;
		
		this.shape = new Rectangle(this.CELL_SIZE, this.CELL_SIZE);
		
		this.shape.setFill(color);
		
		this.shape.setStroke(LudoKing.BORDOR_COLOR);
		
		this.setPrefSize(this.CELL_SIZE, this.CELL_SIZE);
		
		fill = new FillTransition();
		
		this.getChildren().add(shape);

	}
	
	public static Tile createTile(int size, Color color) {
		Tile t = new Tile(size);
		t.setColor(color);
		return t;
	}
	
	public FillTransition getFillTransition() {
		return this.fill;
	}
	
	public Shape getTileShape() {
		
		return this.shape;
	}
	
	public void setArc(double h, double w) {
		((Rectangle)this.shape).setArcHeight(h);
		((Rectangle)this.shape).setArcWidth(h);
	}
	public void playAnimation() {	
		
		fill.setAutoReverse(true);
		fill.setCycleCount(Timeline.INDEFINITE);
		fill.setDuration(Duration.millis(250));
		fill.setFromValue(this.getColor());
		fill.setToValue(this.getColor().darker());
		fill.setShape(this.shape);
		fill.play();
		fill.setOnFinished(even->{
			this.setColor(this.color);
		});
	}
	
	public boolean determineColor(Color color) {
		System.out.println(color.getBlue() + color.getGreen() + color.getRed());
		return color.getBlue() + color.getGreen() + color.getRed() < 1.5;
	}
	
	public void stopAnimation() {
		fill.stop();
		this.shape.setFill(this.color);
	}
	public void setTileNumber(int num) {
		this.tileNumber = num;
	}
	public int getTileNumber() {
		return this.tileNumber;
	}
	public void setSafeTile(boolean b) {
		this.isSafeTile = b;
	}
	public boolean isSafeTile() {
		return this.isSafeTile;
	}
	public Color getColor() {
		return this.color;
	}
	
	public static Tile makeTile(int size, Color c){
		Tile tile = new Tile(size);
		tile.setColor(c);
		return tile;
		
	}
	
	
	public void setColor(Color c) {
		this.color = c;
		this.shape.setFill(this.color);
		//this.shape.setStroke(this.color.invert());
	}

	public double getCenterX() {		
		return this.getLayoutX() + (this.getPrefWidth()/2) + this.getParent().getLayoutX(); 
	}
	public double getCenterY() {
		return this.getLayoutY() + (this.getPrefHeight()/2) + this.getParent().getLayoutY(); 
	}

	public int getCELL_SIZE() {
		return CELL_SIZE;
	}

	public String toString() {
		return "FFFFFFFF";
	}

	
}
