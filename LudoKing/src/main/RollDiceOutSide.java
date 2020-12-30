package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import die.Cube;
import die.Die;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import newludo.StartGame;
import piece.Piece;
import player.Player;
import util.Util;

public class RollDiceOutSide implements EventHandler<MouseEvent> {
	public List<Player> players;
	public Cube cube;
	public Boolean playAgain;
	public Player activePlayer;
	public Timeline animation;
	public List<Piece> tempPiece;
	public StartGame game;
	private int number;
	private Die die;
	private boolean isDie = true;
	private static Rotate rx = new Rotate(0, Rotate.X_AXIS);
	private static Rotate ry = new Rotate(0, Rotate.Y_AXIS);
	private static Rotate rz = new Rotate(0, Rotate.Z_AXIS);
	private boolean scaleControl = true;
	private double scale = 1;
	private static final Random random = new Random();

	public RollDiceOutSide(List<Player> players, Boolean playerAgain, Player activePlayer, StartGame game, Die die,
			ColorPicker picker) {
		this.players = players;
		this.playAgain = playerAgain;
		this.activePlayer = activePlayer;
		this.tempPiece = new ArrayList<>();
		this.game = game;
		this.die = die;
		this.cube = new Cube(LudoKing.TILE_SIZE, Color.WHITESMOKE, 1.0);
		this.cube.setLayoutX(this.die.getLayoutX() + LudoKing.TILE_SIZE / 2);
		this.cube.setLayoutY(this.die.getLayoutY() + LudoKing.TILE_SIZE / 2);
	}

	@Override
	public void handle(MouseEvent event) {

		animation = new Timeline(new KeyFrame(Duration.millis(70), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				switchDieToCube();

				rotateDice();

				setScale();

				stopDice();
			}

		}));

		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

	}

	private void switchDieToCube() {
		if (isDie) {
			game.getChildren().remove(die);
			game.getChildren().add(cube);
			isDie = false;
		}

	}

	private void rotateDice() {

		rx.setAngle(random.nextInt(45));
		ry.setAngle(random.nextInt(45));
		rz.setAngle(random.nextInt(45));

		cube.getTransforms().addAll(rx, ry, rz);
	}

	private void setScale() {
		if (scaleControl)
			scale += 0.15;
		else
			scale -= 0.15;

		if (scale > 1.5)
			scaleControl = false;

		cube.setScaleX(scale);
		cube.setScaleY(scale);
		cube.setScaleZ(scale);
	}

	private void stopDice() {
		if (scale < 1) {
			scaleControl = true;

			animation.stop();

			number = new Random().nextInt(6) + 1;
			// number = 6;

			setDieNumber();

			prepareForNextRoll();
		}
	}

	private void setDieNumber() {
		game.removeCube(cube);

		isDie = true;
		if (isDie)
			game.getChildren().add(die);

		switch (number) {
		case 6: // angles to appear 6
			die.setNumber(6);
			break;
		case 5: // angles to appear 5
			die.setNumber(5);
			break;

		case 4:// angles to appear 4
			die.setNumber(4);
			break;

		case 3: // angles to appear 3
			die.setNumber(3);
			break;

		case 2: // angles to appear 2
			die.setNumber(2);
			break;

		case 1: // angles to appear 1
			die.setNumber(1);
			break;

		}

	}

	private void prepareForNextRoll() {

		activePlayer = Util.getActivePlayer(players);

		tempPiece = activePlayer.getActivePieces(number);
		// System.out.println(tempPiece.size());

		if (Util.checkAutoMove(tempPiece)) {
			activePlayer.stopAnimation();
			Util.clickPiece(tempPiece.get(0), players, activePlayer, number, playAgain, game, die);
			return;
		} else {
			
			setPiecesOnClick(tempPiece, number);

			if (number == 6 && tempPiece.size() > 0) {

				activePlayer.playPieceAnimation(number);
				activePlayer.stopAnimation();

				die.setDisable(true);

			} else if (tempPiece.size() > 0) {
				activePlayer.playPieceAnimation(number);

				activePlayer.stopAnimation();

				die.setDisable(true);

			} else {

				activePlayer.stopAnimation();
				Util.changePlayerTurn(players);
				activePlayer = Util.getActivePlayer(players);

				activePlayer.playAnimation();

				die.setDisable(false);

			}
		}
	}

	private void setPiecesOnClick(List<Piece> temp, int number) {

		for (int k = 0; k < temp.size(); k++) {
			temp.get(k).setOnMouseClicked(e -> {
				Util.clickPiece((Piece) e.getSource(), players, activePlayer, number, playAgain, game, die);
			});
			temp.get(k).setDisable(true);
		}
	}

}// end class