package player;

import java.util.ArrayList;
import java.util.List;

import board.Tile;
import board.WinTile;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import main.LudoKing;
import piece.Piece;
import util.Util;

public class PlayerBase implements Player {
	protected WinTile plyaerWinTile;
	protected Tile[] playerHomeTiles;
	protected List<Piece> playerPieces;
	protected String playerName;
	protected Color playerColor;
	protected boolean playerTurn;
	protected Tile[] moveTiles;
	protected static final int[] COLORED_TILES = new int[] { 0, 51, 52, 53, 54, 55 };
	protected static final int INDEX_OF_ARROW = 50;

	public PlayerBase(Tile[] homeTile, Tile[] moveTile, boolean turn, WinTile winTile) {
		playerName = "";
		playerColor = Util.randomColor(1.0f);
		playerTurn = turn;
		playerHomeTiles = new Tile[Player.NUM_OF_HOME_TILES];
		plyaerWinTile = winTile;
		playerPieces = new ArrayList<Piece>();

		setHomeTiles(homeTile);
		setMoveTiles(moveTile);

		creatPieces();

		setColor(playerColor);
		addShadow();

	}

	private void addShadow() {
		for (int i = 0; i < playerHomeTiles.length; i++) {
			playerHomeTiles[i].setEffect(Util.dropShadow(LudoKing.SHADOW_COLOR, 5));
		}

	}

	private void creatPieces() {

		for (int i = 0; i < 4; i++) {
			Tile tempTile = playerHomeTiles[i];

			Piece pi = new Piece(LudoKing.TILE_SIZE, this);

			pi.setX_Cordinates(tempTile.getCenterX());

			pi.setY_Cordinates(tempTile.getCenterY());

			pi.setCurrentTile(tempTile);
			pi.setOriginalTile(tempTile);

			playerPieces.add(pi);
		}

	}

	@Override
	public Tile getHomeTile(int index) {
		return playerHomeTiles[0];
	}

	@Override
	public Tile[] getHomeTiles() {

		return playerHomeTiles;
	}

	@Override
	public void setHomeTiles(Tile[] tiles) {
	}

	@Override
	public WinTile getWinTile() {
		return plyaerWinTile;
	}

	@Override
	public void setWinTile(WinTile tile) {
		plyaerWinTile = tile;
	}

	@Override
	public void setMoveTiles(Tile[] tiles) {
		this.moveTiles = tiles;
	}

	@Override
	public List<Tile> getMoveTiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return playerName;
	}

	@Override
	public void setTurn(boolean b) {
		playerTurn = b;

	}

	@Override
	public boolean getTurn() {
		return playerTurn;
	}

	@Override
	public void setName(String name) {
		playerName = name;
	}

	@Override
	public Color getColor() {
		return playerColor;
	}

	@Override
	public String toString() {
		return playerName;
	}

	@Override
	public void playAnimation() {
		((Tile) playerHomeTiles[0].getParent()).playAnimation();
	}

	@Override
	public void stopAnimation() {
		((Tile) playerHomeTiles[0].getParent()).stopAnimation();

	}

	@Override
	public Tile getMoveTile(int index) {

		return null;
	}

	@Override
	public Piece getPiece(int index) {
		return playerPieces.get(index);
	}

	@Override
	public void setAllPieces(List<Piece> list) {
		playerPieces = new ArrayList<>();
		for (int i = 0; i < playerPieces.size(); i++) {
			playerPieces.add(list.get(i));
		}

	}

	@Override
	public List<Piece> getAllPieces() {
		return playerPieces;

	}

	@Override
	public void setPiece(Piece p) {
		playerPieces.add(p);

	}

	@Override
	public boolean PlayerWon() {
		for (int i = 0; i < playerPieces.size(); i++) {
			if (!playerPieces.get(i).getCurrentTile().equals(plyaerWinTile))
				return false;
		}
		return true;
	}

	@Override
	public void playPieceAnimation(int dieNumber) {
		List<Piece> temp = getActivePieces(dieNumber);
		for (int i = 0; i < temp.size(); i++) {
			temp.get(i).setDisable(false);
			temp.get(i).playAnimation();
		}
	}

	@Override
	public void stopPiecesAnimation() {
		for (int i = 0; i < playerPieces.size(); i++) {
			playerPieces.get(i).setDisable(true);
			playerPieces.get(i).stopAnimation();
		}
	}

	@Override
	public void printMoveTiles() {
//		for (int i = 0; i < playerMoveTiles.size(); i++) {
//			System.out.println(playerMoveTiles.get(i).getTileNumber());
//		}
	}

	@Override
	public List<Piece> getActivePieces(int dieNumber) {
		List<Piece> temp = new ArrayList<>();
		Tile currentTile = null;
		Tile baseTile = null;
		
		// if(dieNumber == 6) {
		for (int i = 0; i < playerPieces.size(); i++) {
			//tile that piece is currently located
			currentTile = playerPieces.get(i).getCurrentTile();
			baseTile = playerPieces.get(i).getOriginalTile();

			// if the rolled six, collect pieces that are not on final tile(wintile)
			// and their next move doest not exceed the path of the player
			if (dieNumber == 6) {
				if (!currentTile.equals(plyaerWinTile) && canPieceMove(currentTile, dieNumber)) {
					temp.add(playerPieces.get(i));
				}
			} else {
				//Collect pieces that are not in winTile (has not arrived in the final tile)
				//and are not in start position (has not moved yet)
				//and thier next move does not exceed the path of the player
				if (!currentTile.equals(plyaerWinTile)
						&& !currentTile.equals(baseTile)
						&& canPieceMove(currentTile, dieNumber)) {
					temp.add(playerPieces.get(i));
				}
			}
		} // end for loop

		return temp;
	}

	private boolean canPieceMove(Tile oldTile, int number) {

		int oldIndex = 0;
		for (int i = 0; i < this.getMoveTiles().size(); i++) {
			if (oldTile.equals(this.getMoveTile(i))) {
				oldIndex = i;
			}
		}
		oldIndex += number;

		return oldIndex <= this.getMoveTiles().size();

	}

	@Override
	public void setColor(Color c) {
		Tile tile = ((Tile) playerHomeTiles[0].getParent());
		tile.setColor(c);
		tile.getFillTransition().setFromValue(c);
		tile.getFillTransition().setToValue(c.darker());

		for (int i = 0; i < playerHomeTiles.length; i++) {
			playerHomeTiles[i].setColor(c);
		}

		for (int i = 0; i < playerPieces.size(); i++) {
			playerPieces.get(i).setColor(c);
		}

		for (int i = 0; i < COLORED_TILES.length; i++) {
			getMoveTile(COLORED_TILES[i]).setColor(c);
		}

		plyaerWinTile.setColor(c);

		List<Node> node = getMoveTile(INDEX_OF_ARROW).getChildren();

		((Shape) node.get(1)).setFill(c);

	}

}
