package board;

public enum NUM {
	PLAYER_ONE(0),
	PLAYER_TWO(1),
	PLAYER_THREE(2),
	PLAYER_FOUR(3), 
	PLAYER1_DEG(135), 
	PLAYER2_DEG(225), 
	PLAYER3_DEG(45), 
	PLAYER4_DEG(-45);
	
	private final int number;
	NUM(int num){
		this.number = num;
	}

	public int num() {
		return this.number;
	}
	
	
}
