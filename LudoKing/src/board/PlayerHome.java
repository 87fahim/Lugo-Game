package board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.LudoKing;

public class PlayerHome extends Tile {
	// Tile pane1;
	Tile pane2;
	Tile[] tiles;
	private Color color;
	private Rectangle rect;

	public PlayerHome(Color c) {
		super(6 * LudoKing.TILE_SIZE);
		//this.setArc(100,100);
		this.color = c;

		rect = new Rectangle(6 * LudoKing.TILE_SIZE, 6 * LudoKing.TILE_SIZE);
		rect.setFill(this.color);
		rect.setStroke(null);

		this.pane2 = Tile.makeTile(4 * LudoKing.TILE_SIZE, Color.WHITE);
		this.pane2.setLayoutX(LudoKing.TILE_SIZE);
		this.pane2.setLayoutY(LudoKing.TILE_SIZE);

		createTiles();
		setTilesLocation();

		// this.setColor(this.color);

		this.getChildren().addAll(pane2);
		this.getChildren().addAll(this.tiles);

	}

	public Shape getTileShape() {
		return this.rect;
	}

	public void playAnimation(Board board) {
		this.playAnimation();

	}


	public void setTileShapeColor(Color color) {
		this.rect.setFill(color);
	}

	public void setPlayerColor(Color c) {
		this.color = c;
		setColor(this.color);
		for (int i = 0; i < tiles.length; i++) {
			this.tiles[i].setColor(this.color);
		}
	}

	private void createTiles() {
		this.tiles = new Tile[4];
		for (int i = 0; i < tiles.length; i++) {
			Tile tile = new Tile(LudoKing.TILE_SIZE);
			tile.setColor(this.color);
			tile.setArc(10, 10);
			
			
			this.tiles[i] = tile;
		}

	}

	public Tile[] getHomeTile() {
		return this.tiles;
	}

	public Color getPhayerHomeColor() {
		return this.color;
	}

	private void setTilesLocation() {
		double xTransform = this.getLayoutX();
		double yTransform = this.getLayoutY();

		this.tiles[0].setLayoutX(xTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		this.tiles[0].setLayoutY(yTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

		this.tiles[1].setLayoutX(xTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		this.tiles[1].setLayoutY(yTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

		this.tiles[2].setLayoutX(xTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		this.tiles[2].setLayoutY(yTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

		this.tiles[3].setLayoutX(xTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		this.tiles[3].setLayoutY(yTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

	}

}
