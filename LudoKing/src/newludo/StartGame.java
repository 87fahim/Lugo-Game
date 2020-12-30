package newludo;

import java.util.List;

import board.Board;
import die.Cube;
import die.Die;
import javafx.scene.layout.Pane;
import main.LudoKing;
import player.Player;
import player.PlayerFour;
import player.PlayerOne;
import player.PlayerThree;
import player.PlayerTwo;

public class StartGame extends Pane {

	public StartGame(Board board, List<Player> players, Die die) {

		createPlayers(players, board);
		
		setPlayersWinTiles(board, players);
		

		setCubeCoordinates(die, board);
		
		this.getChildren().addAll(board);

		this.getChildren().add(die);
		
		this.getChildren().addAll(players.get(0).getAllPieces());
		this.getChildren().addAll(players.get(1).getAllPieces());
		this.getChildren().addAll(players.get(2).getAllPieces());
		this.getChildren().addAll(players.get(3).getAllPieces());

	}

	private void setCubeCoordinates(Die cube, Board board) {
		float x = (float) board.getTile(56).getLayoutX() + 2 * LudoKing.TILE_SIZE;
		float y = (float) board.getTile(56).getLayoutY();

		cube.setLayoutX(x);
		cube.setLayoutY(y);

	}

	public void removeCube(Cube cube) {
		this.getChildren().remove(cube);
	}

	public void setCube(Cube cube) {
		this.getChildren().add(cube);
	}


	private void setPlayersWinTiles(Board board, List<Player> players) {

		players.get(0).setWinTile(board.getWinTile(0));
		players.get(1).setWinTile(board.getWinTile(1));
		players.get(2).setWinTile(board.getWinTile(2));
		players.get(3).setWinTile(board.getWinTile(3));

	}

	private void createPlayers(List<Player> players, Board board) {
		players.add(new PlayerOne(board.getHomeTiles(0), board.getMoveTile(), false, board.getWinTile(0)));
		players.add(new PlayerTwo(board.getHomeTiles(1), board.getMoveTile(), false, board.getWinTile(1)));
		players.add(new PlayerThree(board.getHomeTiles(2), board.getMoveTile(), false, board.getWinTile(2)));
		players.add(new PlayerFour(board.getHomeTiles(3), board.getMoveTile(), false, board.getWinTile(3)));

	}

}
