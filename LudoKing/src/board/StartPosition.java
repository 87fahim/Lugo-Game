package board;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import main.LudoKing;

public class StartPosition extends Tile {
	private Tile[] tiles;

	public StartPosition() {
		super(6 * LudoKing.TILE_SIZE);

		createTiles();
		setTilesLocation();

		getChildren().addAll(createInnerTile());
		getChildren().addAll(tiles);

	}

	private Tile createInnerTile() {
		Tile whiteTile = Tile.makeTile(4 * LudoKing.TILE_SIZE, Color.WHITE);

		whiteTile.setLayoutX(LudoKing.TILE_SIZE);
		whiteTile.setLayoutY(LudoKing.TILE_SIZE);
		return whiteTile;
	}

	public Shape getTileShape() {
		return new Circle(10);
	}

	public void playAnimation(Board board) {
		playAnimation();

	}

	public void setPlayerColor(Color c) {
		setColor(c);
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].setColor(c);
		}
	}

	private void createTiles() {
		tiles = new Tile[4];
		for (int i = 0; i < tiles.length; i++) {
			Tile tile = new Tile(LudoKing.TILE_SIZE);
			tile.setColor(getColor());
			tile.setArc(10, 10);

			tiles[i] = tile;
		}

	}

	public Tile[] getHomeTile() {
		return tiles;
	}

	public Color getPhayerHomeColor() {
		return getColor();
	}

	private void setTilesLocation() {
		double xTransform = getLayoutX();
		double yTransform = getLayoutY();

		tiles[0].setLayoutX(xTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		tiles[0].setLayoutY(yTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

		tiles[1].setLayoutX(xTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		tiles[1].setLayoutY(yTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

		tiles[2].setLayoutX(xTransform + 2 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		tiles[2].setLayoutY(yTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

		tiles[3].setLayoutX(xTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);
		tiles[3].setLayoutY(yTransform + 4 * LudoKing.TILE_SIZE - LudoKing.TILE_SIZE / 2);

	}

}
