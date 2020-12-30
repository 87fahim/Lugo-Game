package board;

import die.Cube;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import main.LudoKing;

public class Board extends Pane {

	private StartPosition[] startPosition;
	private Tile[] tilesList;
	private WinTile[] winTile;

	private final int NUM_OF_TOTAL_TILES = 72;

	private static final int PLAYER_ONE = 0;
	private static final int PLAYER_TWO = 1;
	private static final int PLAYER_THREE = 2;
	private static final int PLAYER_FOUR = 3;
	private static final int[] ARROW_ANGLE = new int[] {-90,0, 90, 180};
	private static final int[] SAFE_TILES = new int[] { 1, 9, 14, 22, 27, 35, 40, 48 };
	private static final int[] ARROW_TILE_INDEX = new int[] { 12, 25, 38, 51 };

	public Board(int cellSize) {

		createStartPosition();

		createTiles();
		
		setSafeTiles();
		
		setPlayerHomeCoordinates();

		createWinTile();

		addArrows();

		getChildren().addAll(tilesList);
		getChildren().addAll(winTile);
	}

	public Tile getTile(int index) {
		return tilesList[index];
	}
	

	public Tile[] getMoveTile() {
		return tilesList;
	}

	public StartPosition getPlayerHome(int index) {
		return startPosition[index];
	}

	public Tile[] getHomeTiles(int index) {
		return startPosition[index].getHomeTile();
	}

	public void setDie(Cube cube) {
		cube.setLayoutX(tilesList[56].getLayoutX() + 2 * LudoKing.TILE_SIZE + LudoKing.TILE_SIZE / 2);
		cube.setLayoutY(tilesList[56].getLayoutY() + LudoKing.TILE_SIZE / 2);
		getChildren().add(cube);
	}

	private void createWinTile() {
		winTile = new WinTile[4];
		for (int i = 0; i < winTile.length; i++) {
			WinTile tile = new WinTile(LudoKing.TILE_SIZE);
			setTilePosition(i, tile);
			winTile[i] = tile;

		}

	}

	public WinTile getWinTile(int index) {
		return winTile[index];
	}

	private void setTilePosition(int i, Tile tile) {
		switch (i) {
		case 0:
			tile.setRotate(ARROW_ANGLE[i]);
			tile.setLayoutX(tilesList[56].getLayoutX());
			tile.setLayoutY(tilesList[56].getLayoutY());
			break;
		case 1:
			tile.setRotate(ARROW_ANGLE[i]);
			tile.setLayoutX(tilesList[61].getLayoutX() - LudoKing.TILE_SIZE);
			tile.setLayoutY(tilesList[61].getLayoutY() + LudoKing.TILE_SIZE);
			break;
		case 2:
			tile.setRotate(ARROW_ANGLE[i]);

			tile.setLayoutX(tilesList[66].getLayoutX() - 2 * LudoKing.TILE_SIZE);
			tile.setLayoutY(tilesList[66].getLayoutY());
			break;
		default:
			tile.setRotate(ARROW_ANGLE[ARROW_ANGLE.length-1]);
			tile.setLayoutX(tilesList[71].getLayoutX() - LudoKing.TILE_SIZE);
			tile.setLayoutY(tilesList[71].getLayoutY() - LudoKing.TILE_SIZE);
			break;

		}

	}

	private void createStartPosition() {
		startPosition = new StartPosition[4];

		startPosition[PLAYER_ONE] = new StartPosition();
		startPosition[PLAYER_TWO] = new StartPosition();
		startPosition[PLAYER_THREE] = new StartPosition();
		startPosition[PLAYER_FOUR] = new StartPosition();

		getChildren().addAll(startPosition);

	}


	private void createTiles() {
		tilesList = new Tile[NUM_OF_TOTAL_TILES];
		for (int i = 0; i < NUM_OF_TOTAL_TILES; i++) {
			Tile tile = new Tile(LudoKing.TILE_SIZE);
			tilesList[i] = tile;

			setTilesLocation(i);
			
		}

	}

	private void setSafeTiles() {
		
		for (int j = 0; j < SAFE_TILES.length; j++) {
			//create a start to mark safe tiles
			Star s = new Star(LudoKing.TILE_SIZE / 2);
			s.setX_Coordinate(LudoKing.TILE_SIZE / 2);
			s.setY_Coordinate(LudoKing.TILE_SIZE / 2);
			
			tilesList[SAFE_TILES[j]].setSafeTile(true);
			
			if(j % 2 != 0 )
				tilesList[SAFE_TILES[j]].getChildren().add(s);
		}


	}

	private void setPlayerHomeCoordinates() {
		// The coordinates is 0, 0. other is no reference tile to set the coordinated of
		// this tile
		startPosition[Board.PLAYER_ONE].setLayoutX(0);
		startPosition[Board.PLAYER_ONE].setLayoutY(0);

		// Reference cell is tile 13 for coordinates
		startPosition[Board.PLAYER_TWO].setLayoutX(tilesList[13].getLayoutX() + LudoKing.TILE_SIZE);
		startPosition[Board.PLAYER_TWO].setLayoutY(tilesList[13].getLayoutY());

		// Reference cell is tile 50 for coordinates
		startPosition[Board.PLAYER_FOUR].setLayoutX(tilesList[50].getLayoutX());
		startPosition[Board.PLAYER_FOUR].setLayoutY(tilesList[50].getLayoutY() + LudoKing.TILE_SIZE);

		// Reference cell is tile 32 for coordinates
		startPosition[Board.PLAYER_THREE].setLayoutX(tilesList[32].getLayoutX() + LudoKing.TILE_SIZE);
		startPosition[Board.PLAYER_THREE].setLayoutY(tilesList[32].getLayoutY());

	}

	private void addArrows() {
		
		double center = LudoKing.TILE_SIZE / 3;
		
		final int[] arrowDegree = new int[] { 225, -45, 45, 135 };
		
		for (int i = 0; i < ARROW_TILE_INDEX.length; i++) {

			Shape a = Arrow.makeArrow(LudoKing.TILE_SIZE, arrowDegree[i]);
			a.setLayoutX(center);
			a.setLayoutY(center);	
			tilesList[ARROW_TILE_INDEX[i]].getChildren().add(a);
		}
	}

	private void setTilesLocation(int i) {

		Text t = new Text(String.valueOf(i));
		t.setLayoutX(LudoKing.TILE_SIZE / 2);
		t.setLayoutY(LudoKing.TILE_SIZE / 2);

		//tilesList[i].getChildren().add(t);

		tilesList[i].setTileNumber(i);

		if (i == 0) {
			tilesList[i].setLayoutX(0);
			tilesList[i].setLayoutY(startPosition[0].getPrefHeight());
			return;
		}

		if (i > 0 && i < 6) {

			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() + LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;

		}

		if (i == 6) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() + LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() - LudoKing.TILE_SIZE);
			return;
		}

		if (i > 5 && i < 12) {

			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() - LudoKing.TILE_SIZE);
			return;

		}

		if (i > 11 && i < 14) {

			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() + LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;
		}

		if (i > 13 && i < 19) {

			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() + LudoKing.TILE_SIZE);
			return;

		}
		if (i == 19) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() + LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() + LudoKing.TILE_SIZE);
			return;
		}

		if (i > 18 && i < 25) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() + LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;
		}

		if (i > 24 && i < 27) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() + LudoKing.TILE_SIZE);
			return;
		}

		if (i > 26 && i < 32) {
//			if (i == 27) {
//				tile[i].setColor(startPosition[3].getPhayerHomeColor());
//			}
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;
		}

		if (i == 32) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() + LudoKing.TILE_SIZE);
			return;
		}

		if (i > 31 && i < 38) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() + LudoKing.TILE_SIZE);
			return;
		}

		if (i > 37 && i < 40) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;
		}

		if (i > 39 && i < 45) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() - LudoKing.TILE_SIZE);
			return;
		}

		if (i == 45) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() - LudoKing.TILE_SIZE);
			return;
		}

		if (i > 44 && i < 51) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;
		}

		if (i > 50 && i < 52) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() - LudoKing.TILE_SIZE);
			return;
		}
		// Colored Tiles, where players go toward thier center to win
		// =======================================================================================
		if (i > 51 && i < 57) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() + LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;
		}

		if (i == 57) {
			tilesList[i].setLayoutX(tilesList[10].getLayoutX() + LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[10].getLayoutY());
			return;
		}

		if (i > 56 && i < 62) {

			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() + LudoKing.TILE_SIZE);
			return;
		}

		if (i == 62) {
			tilesList[i].setLayoutX(tilesList[25].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[25].getLayoutY());
			return;
		}

		if (i > 61 && i < 67) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY());
			return;

		}

		if (i == 67) {
			tilesList[i].setLayoutX(tilesList[36].getLayoutX() - LudoKing.TILE_SIZE);
			tilesList[i].setLayoutY(tilesList[36].getLayoutY());
			return;
		}

		if (i > 66 && i < 72) {
			tilesList[i].setLayoutX(tilesList[i - 1].getLayoutX());
			tilesList[i].setLayoutY(tilesList[i - 1].getLayoutY() - LudoKing.TILE_SIZE);
		}

	}


}
