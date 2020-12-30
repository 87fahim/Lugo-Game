package player;

import java.util.ArrayList;
import java.util.List;

import board.Tile;
import board.WinTile;


public class PlayerTwo extends PlayerBase {
	private static final int[] PATH = new int[] { 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
			31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 0, 1, 2, 3, 4, 5, 6, 7,
			8, 9, 10, 11, 12, 57, 58, 59, 60, 61 };


	public PlayerTwo(Tile[] homeTile, Tile[] moveTile, boolean turn, WinTile winTile) {
		super(homeTile, moveTile, turn, winTile);
		playerName = "Player_2";
	}

	public static final int PlAYER_NUM = 1;

	@Override
	public void setHomeTiles(Tile[] tiles) {
		Tile[] tile = tiles;
		for (int i = 0; i < Player.NUM_OF_HOME_TILES; i++) {
			this.playerHomeTiles[i] = tile[i];
		}
	}


	@Override
	public Tile getMoveTile(int index) {

		return this.moveTiles[PATH[index]];
	}

	public List<Tile> getMoveTiles() {
		List<Tile> move = new ArrayList<>();
		for (int i = 0; i < PATH.length; i++) {
			move.add(this.moveTiles[PATH[i]]);
		}

		return move;
	}


}
