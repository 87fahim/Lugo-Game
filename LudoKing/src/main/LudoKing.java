package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import board.Board;
import die.Die;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import newludo.StartGame;
import player.Player;
import util.Util;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LudoKing extends Application {
	private BorderPane borderPane;

	public static final int TILE_SIZE = 60
			;
	public static int number = 1;
	public static final int NUM_OF_PLAYERS = 4;
	public static final int NUM_OF_WIN_TILES = 4;
	public static final int NUM_OF_PIECES = NUM_OF_PLAYERS * 4;
	public static final int W = 100;
	public static final int H = 40;

	private List<Player> playersList;
	private Board mainBoard;
	private Boolean playAgain = false;
	private Player activePlayer;
	private ColorPicker picker;
	private StartGame game;
	private Die die;
	
	private static Random random = new Random();
	
	public static final Color SHADOW_COLOR = Color.DARKGRAY;
	public static final Color BORDOR_COLOR = Color.BLACK;
	public static final Color BACKGROUND_COLOR = Color.WHITESMOKE;

	public static final int SHADOW_SIZE = 5;

	@Override
	public void start(Stage primaryStage) {

		createBorderPane();

		createContent();

		StackPane stMain = new StackPane(borderPane);

		Scene scene = new Scene(stMain);
		scene.setCamera(new PerspectiveCamera());
		primaryStage.setScene(scene);

		primaryStage.show();

	}

	private void createContent() {

		mainBoard = new Board(TILE_SIZE);

		playersList = new ArrayList<>();

		die = new Die(TILE_SIZE, TILE_SIZE);

		die.setNumber(random.nextInt(6) + 1);

		game = new StartGame(mainBoard, playersList, die);

		activePlayer = playersList.get(random.nextInt(4));

		activePlayer.setTurn(true);

		activePlayer.playAnimation();

		die.setOnMouseClicked(new RollDiceOutSide(playersList, playAgain, activePlayer, game, die, picker));
;
		borderPane.setCenter(game);
	}

	private void createBorderPane() {

		borderPane = new BorderPane();

		borderPane.setLeft(createControls());

		Text title = new Text("LUDO KING!");

		title.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BLACK, FontPosture.REGULAR, 70));
//		title.setStyle("-fx-font: 100px Tahoma;" + 
//				"    -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);" + 
//				"    -fx-stroke: black;" + 
//				"    -fx-stroke-width: 2;");
		title.setEffect(Util.dropShadow(SHADOW_COLOR, SHADOW_SIZE));
		title.setStroke(LudoKing.BORDOR_COLOR);
		FillTransition fill = new FillTransition();
		fill.setAutoReverse(true);
		fill.setCycleCount(Timeline.INDEFINITE);
		fill.setDuration(Duration.seconds(5));
		fill.setFromValue(Util.randomColor(1));
		
		fill.setToValue(Util.randomColor(1));
		fill.setShape(title);
		fill.play();

		borderPane.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);

	}


	private VBox createControls() {
		VBox vb = new VBox(restartGame(), changePlayerColor());
		vb.setPadding(new Insets(10));
		vb.setSpacing(10);
		return vb;
	}

	private Button changePlayerColor() {
		Button change = new Button("Change Color");
		change.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BLACK, FontPosture.REGULAR, 12));
		change.setTextFill(LudoKing.BORDOR_COLOR);
		change.setPrefSize(W, H);
		change.setEffect(Util.dropShadow(SHADOW_COLOR, 5));
		change.setOnAction(event -> {
			Util.openStageColorChangeStage(playersList);
		});
		return change;
	}

	private Button restartGame() {
		Button restart = new Button("Restart!");
		restart.setPrefSize(W, H);
		restart.setPrefWidth(100);
		restart.setEffect(Util.dropShadow(SHADOW_COLOR, 5));
		restart.setOnMouseClicked(e -> {
			createContent();
		});
		return restart;
	}

	public static void main(String[] args) {
		
		
		launch(args);

	}

}
