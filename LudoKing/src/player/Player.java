package player;

import java.util.List;

import board.Tile;
import board.WinTile;
import javafx.scene.paint.Color;
import piece.Piece;

public interface Player{
	
	public static final int NUM_OF_MOVE_TILES = 56;
	public static final int NUM_OF_HOME_TILES = 4;
	public static final int NUM_OF_PIECES = 4;
	public static final int NUM_OF_WIN_TILES = 4;
	
	
	
	public Tile[] getHomeTiles();
	public List<Tile> getMoveTiles();
	//public Tile[] getMoveTiles();

	public void setHomeTiles(Tile[] tiles);
	
	public void setMoveTiles(Tile[] tiles);
	
	public void setTurn(boolean b);
	public boolean getTurn();
	
	public String getName();
	public void setName(String name);
	public Color getColor();
	
	public void setColor(Color c);

	public WinTile getWinTile();
	public void setWinTile(WinTile tile);
	
	public Tile getMoveTile(int index);
	

	public void playAnimation();
	public void stopAnimation();
	
//	public void setPlayerWins(boolean b);
//	public boolean getPlayerWins();
	
	public List<Piece> getActivePieces(int number);
	
	public boolean PlayerWon();
	
	public void setPiece(Piece p);
	public Piece getPiece(int index);
	
	public void setAllPieces(List<Piece> list);
	public List<Piece> getAllPieces();
	
	public void playPieceAnimation(int number);
	public void stopPiecesAnimation();
	public void printMoveTiles();
	

	
	public Tile getHomeTile(int index);
	
}
