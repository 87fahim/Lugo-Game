package piece;

import board.Tile;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import main.LudoKing;
import player.Player;

public class Piece extends VBox {
	private final int SIZE;
	private Color color;
	private Tile pieceInTile;
	private final Player player;
	private Shape pieceShape;
	private Shape innerCircle;
	private Tile orginalTile;
	private FillTransition fill;
	private boolean wins;

	public Piece(int size, Player player) {
		this.pieceInTile = null;
		this.color = LudoKing.BACKGROUND_COLOR;
		this.SIZE = size / 3;
		this.player = player;
		this.orginalTile = null;
		this.wins = false;

		fill = new FillTransition();

		Pane pane = new Pane();

		Rectangle r = createRectangle();

		Circle c = createOuterCircle(size);

		pieceShape = Shape.union(r, c);
		pieceShape.setFill(LudoKing.BACKGROUND_COLOR);
		pieceShape.setRotate(-135);
		pieceShape.setStroke(LudoKing.BORDOR_COLOR);
		pieceShape.setStrokeWidth(1);

		innerCircle = createCenterCirle();

		Ellipse shadow = createSh();

		pane.getChildren().addAll(shadow, this.pieceShape, innerCircle);
		this.getChildren().addAll(pane);

	}

	public void setPieceWins(boolean b) {
		this.wins = b;
	}

	public boolean getPieceWins() {
		return this.wins;
	}

	private Ellipse createSh() {
		Ellipse el = new Ellipse(this.SIZE / 1.5, this.SIZE / 3);
		el.setStroke(Color.GRAY);
		el.setLayoutX(this.SIZE);
		el.setLayoutY(2.5 * this.SIZE);

		Shadow sh = new Shadow();
		sh.setBlurType(BlurType.ONE_PASS_BOX);
		sh.setColor(Color.BLACK);
		sh.setRadius(10);
		sh.setWidth(3);
		el.setEffect(sh);
		el.setOpacity(0.5);

		return el;
	}

	private Rectangle createRectangle() {
		Rectangle r = new Rectangle(this.SIZE, this.SIZE);
		r.setLayoutX(0);
		r.setLayoutY(0);
		return r;
	}

	private Circle createOuterCircle(int size) {
		Circle c = new Circle(this.SIZE);
		c.setLayoutX(size / 3);
		c.setLayoutY(size / 3);
		return c;
	}

	private Circle createCenterCirle() {
		Circle cc = new Circle(this.SIZE / 1.4);
		cc.setFill(this.color);
		cc.setStroke(LudoKing.BORDOR_COLOR);
		cc.setCenterX(this.SIZE);
		cc.setCenterY(this.SIZE);
		return cc;
	}

	public void setOriginalTile(Tile tile) {
		this.orginalTile = tile;
	}

	public Tile getOriginalTile() {
		return this.orginalTile;
	}

	public static Shape createPiece(int size, Player player) {
		return new Piece(size, player).getPieceShape();
	}

	public void setStroke(Color color) {
		this.pieceShape.setStroke(color);
	}

	public Shape getPieceShape() {
		return this.pieceShape;
	}

	public void setColor(Color c) {
		this.color = c;
		this.innerCircle.setFill(this.color);

	}

	public Shape getInnerCircle() {
		return this.innerCircle;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Tile getCurrentTile() {
		return this.pieceInTile;
	}

	public void setCurrentTile(Tile tile) {
		this.pieceInTile = tile;
	}

	public Color getColor() {
		return this.color;
	}

	public void playAnimation() {
		Color color = (Color) this.pieceShape.getFill();
		fill.setAutoReverse(true);
		fill.setCycleCount(Timeline.INDEFINITE);
		fill.setDuration(Duration.millis(250));
		fill.setFromValue(color);
		fill.setToValue(color.darker());
		fill.setShape(this.getPieceShape());
		fill.play();
		fill.setOnFinished(even -> {
			this.setColor(color);
		});
	}

	public Shape getPieceShape1() {
		return this.pieceShape;
	}

	public void stopAnimation() {
		fill.stop();
		this.pieceShape.setFill(Color.WHITESMOKE);
	}

	public void resetPiece() {
		setX_Cordinates(orginalTile.getCenterX());
		setY_Cordinates(orginalTile.getCenterY());
	}

//================================================================================
	public double getX_Cordinates() {
		return this.getLayoutX() + this.getBoundsInParent().getWidth() / 2;
	}

	public double getY_Cordinates() {
		return this.getLayoutY() + this.getBoundsInParent().getWidth();
	}

	public void setX_Cordinates(double x) {
		this.setLayoutX(x - this.getBoundsInParent().getWidth() / 2);
	}

	public void setY_Cordinates(double y) {
		this.setLayoutY(y - this.getBoundsInParent().getHeight() + (this.SIZE * 3) / 4);
	}

}
