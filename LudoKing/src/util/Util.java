package util;

import java.util.ArrayList;
import java.util.List;

import board.Tile;
import die.Die;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.LudoKing;
import newludo.StartGame;
import piece.Piece;
import player.Player;

public abstract class Util {
	static int x = 0;

	public static Button createButton(String str, int size) {
		Button button = new Button(str);
		button.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, size));
		button.setEffect(Util.dropShadow(LudoKing.SHADOW_COLOR, LudoKing.SHADOW_SIZE));

		return button;

	}

	public static boolean checkAutoMove(List<Piece> pieces) {
		if (pieces.size() < 1)
			return false;

		if (pieces.size() == 1) {
			return true;
		}

		if (pieces.size() == 4) {
			if (pieces.get(0).getCurrentTile().equals(pieces.get(0).getOriginalTile())
					&& pieces.get(1).getCurrentTile().equals(pieces.get(1).getOriginalTile())
					&& pieces.get(2).getCurrentTile().equals(pieces.get(2).getOriginalTile())
					&& pieces.get(3).getCurrentTile().equals(pieces.get(3).getOriginalTile()))
				return true;
		}

		for (int i = 0; i < pieces.size() - 1; i++) {
			if (!pieces.get(0).getCurrentTile().equals(pieces.get(i + 1).getCurrentTile()))
				return false;
		}

		return true;
	}

	public static void clickPiece(Piece piece, List<Player> players, Player activePlayer, int number, Boolean playAgain,
			StartGame game, Die cube) {

		boolean finalTile = false;
		activePlayer.stopPiecesAnimation();

		final Tile oldTile = piece.getCurrentTile();
		Tile newTile;

		final List<Tile> moveTiles = piece.getPlayer().getMoveTiles();

		final boolean firstMove = piece.getCurrentTile().equals(piece.getOriginalTile());

		if (firstMove) {
			newTile = moveTiles.get(0);
//			newTile = piece.getPlayer().getWinTile();
//			finalTile = true;
		}

		else {
			int x = Util.indexOfNextTile(piece, moveTiles) + number;
			if (x < moveTiles.size())
				newTile = moveTiles.get(x);

			else if (x == moveTiles.size()) {
				newTile = piece.getPlayer().getWinTile();
				finalTile = true;
			} else {
				newTile = oldTile;
			}
		}

		piece.setCurrentTile(newTile);

		movePiece(piece, oldTile, newTile, players, new Player[] { activePlayer }, number, new Boolean[] { playAgain },
				finalTile, game, cube);

		activePlayer = Util.getActivePlayer(players);
		activePlayer.playAnimation();

	}

	private static void movePiece(Piece piece, Tile oldTile, Tile newTile, List<Player> players, Player[] activePlayer,
			int number, Boolean[] playAgain, boolean finalTile, StartGame game, Die cube) {

		cube.setDisable(true);
		TranslateTransition tt = new TranslateTransition();
		tt.setDuration(Duration.seconds(1));
		tt.setFromX(oldTile.getCenterX() - oldTile.getCenterX());
		tt.setFromY(oldTile.getCenterY() - oldTile.getCenterY());

		tt.setByX(newTile.getCenterX() - oldTile.getCenterX());
		tt.setByY(newTile.getCenterY() - oldTile.getCenterY());

		tt.setNode(piece);
		tt.setCycleCount(1);
		tt.play();

		tt.setOnFinished(event -> {
			piece.setCurrentTile(newTile);
			piece.setX_Cordinates(newTile.getCenterX());
			piece.setY_Cordinates(newTile.getCenterY());

			piece.setTranslateX(0);
			piece.setTranslateY(0);

			if (!newTile.isSafeTile())
				playAgain[0] = Util.checkCollission(piece, Util.getAllPiece(players));

			if (playAgain[0]) {
				Piece pieceToBeRemoved = Util.getRemovePiece(piece, players);
				pieceToBeRemoved.setCurrentTile(pieceToBeRemoved.getOriginalTile());
				pieceToBeRemoved.setX_Cordinates(pieceToBeRemoved.getOriginalTile().getCenterX());
				pieceToBeRemoved.setY_Cordinates(pieceToBeRemoved.getOriginalTile().getCenterY());
			}

			Util.adjustPieceOnOneTile(newTile, Util.getAllPiece(players), players, finalTile);

			Util.adjustPieceOnOneTile(oldTile, Util.getAllPiece(players), players, finalTile);

			activePlayer[0].stopPiecesAnimation();

			checkGameState(players, activePlayer[0], game, cube);

			if (number != 6)
				if (!playAgain[0])
					if (!finalTile) {
						Util.changePlayerTurn(players);
						activePlayer[0].stopAnimation();
						activePlayer[0] = Util.getActivePlayer(players);
					}

			activePlayer[0].playAnimation();
			cube.setDisable(false);
		});
	}

	private static Piece getRemovePiece(Piece piece, List<Player> players) {
		List<Piece> pieces = Util.getAllPiece(players);

		Tile pieceCurrentTile = piece.getCurrentTile();
		Player player = piece.getPlayer();

		for (int i = 0; i < pieces.size(); i++) {

			// remove the opponent pieces if the pieces are not safe
			if (pieceCurrentTile.equals(pieces.get(i).getCurrentTile()) && pieces.get(i).getPlayer() != player
					&& !pieceCurrentTile.isSafeTile()) {
				return pieces.get(i);

			}
		}
		return null;
	}

	private static void checkGameState(List<Player> players, Player activePlayer, StartGame game, Die die) {
		if (activePlayer.PlayerWon()) {

			Text text = Util.playerWin(activePlayer, players, LudoKing.TILE_SIZE);

			game.getChildren().add(text);
		}

		// System.out.println(GameLogic.gameEnd(players));
		if (Util.gameEnd(players)) {

			Button button = new Button("Restart!");

			button.setPrefSize(LudoKing.TILE_SIZE * 5, LudoKing.TILE_SIZE * 5);

			button.setLayoutX(game.getBoundsInLocal().getCenterX() - button.getPrefWidth() / 2);
			button.setLayoutY(game.getBoundsInLocal().getCenterY() - button.getPrefHeight() / 2);

			die.setDisable(true);

			game.getChildren().add(button);
		}

	}

	public static DropShadow dropShadow(Color color, int offset) {
		DropShadow sh = new DropShadow();
		sh.setBlurType(BlurType.TWO_PASS_BOX);
		sh.setColor(color);
		sh.setOffsetX(offset);
		sh.setOffsetY(offset);
		return sh;
	}

	public static void openStageColorChangeStage(List<Player> players) {
		// Size of buttons
		final int W = 200;
		final int H = 40;

		// To choose color, the default color will be random
		ColorPicker pick = new ColorPicker(Color.color(Math.random(), Math.random(), Math.random()));
		pick.getStyleClass().add("button");
		pick.setPrefSize(W, 2 * H);
		pick.setEffect(Util.dropShadow(LudoKing.SHADOW_COLOR, LudoKing.SHADOW_SIZE));
		pick.setStyle("-fx-color-rect-width: 60px;" + "-fx-color-rect-height: 60px;" + "-fx-color-label-visible: false;"
				+ "-fx-min-width: 150px;" + "-fx-min-height: 100px;" + "-fx-pref-width: 150px;"
				+ "-fx-pref-height: 100px");

		// Stage for choose color options
		Stage colorStage = new Stage();

		// This will hold the color picker on the top and the two button in bottom
		VBox cantianer = new VBox();

		// Hold the two button side by side
		HBox buttons = new HBox();

		// Change the color of the plaer and close the stage
		Button ok = new Button("OK");
		ok.setEffect(Util.dropShadow(LudoKing.SHADOW_COLOR, LudoKing.SHADOW_SIZE));
		ok.setPrefSize(W, H);
		ok.setOnAction(okay -> {
			Player activePlayer = Util.getActivePlayer(players);
			activePlayer.stopAnimation();
			activePlayer.setColor(pick.getValue());
			activePlayer.playAnimation();
			colorStage.close();
		});

		// Do not change any thing, just close the stage
		Button cancel = new Button("Cancel");
		cancel.setEffect(Util.dropShadow(LudoKing.SHADOW_COLOR, LudoKing.SHADOW_SIZE));
		cancel.setPrefSize(W, H);
		cancel.setOnAction(can -> {
			colorStage.close();
		});

		// Button "OK" and "Cancel" are side by side
		buttons.getChildren().addAll(ok, cancel);
		buttons.setSpacing(10);
		buttons.setPadding(new Insets(10));

		// Both buttons, "OK" and "Cancel" are below the color picker
		cantianer.getChildren().addAll(pick, buttons);
		cantianer.setSpacing(10);
		cantianer.setPadding(new Insets(10));
		cantianer.setAlignment(Pos.CENTER);

		colorStage.setScene(new Scene(cantianer));
		colorStage.setTitle("Change Color");
		colorStage.show();

	}

	public static boolean gameEnd(List<Player> list) {

		// All player are playing
		int num_Of_Remaining_Players = list.size();

		// If a player has finished, update the number of players who has not finished
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).PlayerWon())
				num_Of_Remaining_Players--;
		}

		// Game end if only one player has not finished yet
		return num_Of_Remaining_Players == 1;
	}

	public static List<Piece> getAllPiece(List<Player> list) {
		List<Piece> temp = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			temp.addAll(list.get(i).getAllPieces());
		}
		return temp;
	}

	public static int indexOfNextTile(Piece piece, List<Tile> moveTiles) {
		for (int i = 0; i < moveTiles.size(); i++) {
			if (piece.getCurrentTile().equals(moveTiles.get(i)))
				return i;
		}

		return -1;

	}

	public static int findIndex(Tile startTile, List<Tile> moveTiles) {
		for (int i = 0; i < moveTiles.size(); i++) {
			if (startTile.equals(moveTiles.get(i)))
				return i;
		}
		return -1;
	}

	public static void adjustPieceOnOneTile(Tile currentTile, List<Piece> pieces, List<Player> players,
			Boolean playAgain) {

		// If the player piece arrives in the last tile, then return
		if (playAgain && Util.getActivePlayer(players).getWinTile().equals(currentTile)) {
			adjustPieceOnOneWinTile(currentTile, pieces, players);
			return;
		}

		// Tile currentTile = tile;
		List<Piece> list = new ArrayList<>();

		int num_Of_Pieces = 1;

		float tileX_Layout = (float) currentTile.getLayoutX();

		for (int i = 0; i < pieces.size(); i++) {
			if (currentTile.equals(pieces.get(i).getCurrentTile())) {
				num_Of_Pieces++;
				list.add(pieces.get(i));
			}
		}

		float space = LudoKing.TILE_SIZE / num_Of_Pieces;

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setX_Cordinates(tileX_Layout + space * (i + 1));
			list.get(i).setY_Cordinates(currentTile.getCenterY());

		}
	}

	private static void adjustPieceOnOneWinTile(Tile currentTile, List<Piece> pieces, List<Player> players) {

		boolean vertical = false;
		Player currentPlayer = Util.getActivePlayer(players);
		for (int i = 0; i < players.size(); i++) {
			if (currentTile.equals(players.get(i).getWinTile()) && i % 2 == 0) {
				currentPlayer = players.get(i);
				vertical = true;
			}

		}

		// Tile currentTile = tile;
		List<Piece> list = new ArrayList<>();

		int num_Of_Pieces = 0;

		for (int i = 0; i < currentPlayer.getAllPieces().size(); i++) {
			if (currentTile.equals(currentPlayer.getPiece(i).getCurrentTile())) {
				num_Of_Pieces++;
				list.add(currentPlayer.getPiece(i));
			}
		}

		double width = currentTile.getBoundsInParent().getWidth();
		double hight = currentTile.getBoundsInParent().getHeight();

		double s = width > hight ? width : hight;
		double space = s / (num_Of_Pieces + 1);

		for (int i = 0; i < list.size(); i++) {

			if (vertical) {
				list.get(i).setX_Cordinates(currentTile.getCenterX());
				list.get(i).setY_Cordinates(currentTile.getLayoutY() - currentTile.getCELL_SIZE() + space * (i + 1));

			} else {

				list.get(i).setX_Cordinates(currentTile.getLayoutX() + space * (i + 1));
				list.get(i).setY_Cordinates(currentTile.getCenterY());

			}

		}

	}

	public static boolean checkCollission(Piece p, List<Piece> pieces) {
		boolean collission = false;
		// current player
		Player player = p.getPlayer();

		// the tile wil the piece has just moved
		Tile pieceCurrentTile = p.getCurrentTile();

		// check collission for all pieces
		for (int i = 0; i < pieces.size(); i++) {

			// remove the opponent pieces if the pieces are not safe
			if (pieceCurrentTile.equals(pieces.get(i).getCurrentTile()) && pieces.get(i).getPlayer() != player
					&& !pieceCurrentTile.isSafeTile()) {
				// move opponent pieces to the start position
//				pieces.get(i).setX_Cordinates(pieces.get(i).getBaseTile().getCenterX());
//				pieces.get(i).setY_Cordinates(pieces.get(i).getBaseTile().getCenterY());
//
//				// update the tile which the piece belongs to
//				pieces.get(i).setTile(pieces.get(i).getBaseTile());

				collission = true;
			}

		}
		return collission;
	}

	public static Text getText(String str, int size, Color color) {
		Text t = new Text(str);
		t.setFont(Font.font(size));
		t.setFill(color);
		return t;
	}

	public static void changePlayerTurn(List<Player> players) {

		int index = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getTurn())
				index = i;
		}

		int futurePlayer = (index + 1) % players.size();

		while (futurePlayer != index) {

			if (!players.get(futurePlayer).PlayerWon()) {

				players.get(index).setTurn(false);
				players.get(futurePlayer).setTurn(true);

				break;
			} else {
				futurePlayer = (futurePlayer + 1) % players.size();
			}
		}
	}

	public static boolean checkWinner(Player player, List<Piece> pieces) {
		List<Piece> temp = new ArrayList<>();
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).getCurrentTile().equals(pieces.get(i).getPlayer().getWinTile()) && player.getTurn()) {
				temp.add(pieces.get(i));
			}
		}

		if (temp.size() > 3)
			return true;
		return false;
	}

	public static Text playerWin(Player activePlayer, List<Player> list, int size) {
		int num_PlayerWon = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).PlayerWon())
				num_PlayerWon++;
		}

		Text text = new Text(String.valueOf(num_PlayerWon));
		text.setFill(Util.randomColor(0.5f));

		text.setStroke(LudoKing.BORDOR_COLOR);
		text.setStrokeWidth(3);
		text.setFont(Font.font(5 * size));

		text.setEffect(Util.dropShadow(LudoKing.SHADOW_COLOR, 5));

		Tile[] tile = activePlayer.getHomeTiles();
		Tile t = (Tile) tile[0].getParent();

		text.setLayoutX(t.getCenterX() - text.getBoundsInParent().getWidth() / 2);
		text.setLayoutY(t.getCenterY() + text.getBoundsInParent().getHeight() / 4);

		return text;

	}

	public static Color randomColor(float op) {

		return Color.color(Math.random(), Math.random(), Math.random(), op);
	}

	public static Player getActivePlayer(List<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getTurn())
				return players.get(i);
		}
		return null;
	}

	public static Node creatTitle() {
		Text title = new Text("LUDO KING");
		title.setEffect(Util.dropShadow(LudoKing.SHADOW_COLOR, LudoKing.SHADOW_SIZE));
		title.setFont(Font.font("normal", FontWeight.BOLD, FontPosture.REGULAR, 50));
		title.setFill(Util.randomColor(1.0f));
		title.setStroke(LudoKing.BORDOR_COLOR);

		FillTransition fill = new FillTransition();
		fill.setAutoReverse(true);
		fill.setCycleCount(Timeline.INDEFINITE);
		fill.setDuration(Duration.seconds(4));
		fill.setFromValue(Util.randomColor(1.0f));
		fill.setToValue(Util.randomColor(1.0f));
		fill.setShape(title);

		fill.play();

		return title;
	}

}
