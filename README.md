# Lugo-Game-JavaFX
# This a four player game where each player roll the dice in their turn and move thier pieces on the board.
# Please play attention to the class heirarchy (packages)
 ------------------------------------------------------------------------------------------------------------------
1. main package: This pakcage contain class to start (initial) the game and roll the dice. It has three class, which two class play 
   implortant role in the game while the third class is not completed yet.
   
  1.1. LudoKing: This class is responsible for starting the game. 
       This class has JavaFX BorderPane, where the left child is another Pane
       which contain two buttons, "Restart" which restarts the game or resets the game; "Change Color" which changes the color of a players.
     
       The top child of the BorderPane is just a simple text "LUDO KING" with color fill animation.
     
       The center Child of the BorderPane is Pane which has board, and pieces.
     
   1.2. RollDiceOutSide: This class is the class responsible to create an animation for rolling the dice, the logic for which action should be
        taken regarding the result of the die.
        
   1.3. EndGameAnimation: This class is supposed to responsible for animation when the game is over. It was completed when this project'
        was uploaded. So it has not much effect on the game.
 ------------------------------------------------------------------------------------------------------------------
  2. board package: This package is responsible to creating the game board and holding it component during the game.
                    It has the following class;
    2.1. Tile: This class is the base class to create all rectangle shapes in board. It has the properties, such has color, size, location,
               and animation which can be modified during the game.
    2.2  StartPostion: The class is used create the start position for each player. It extends to Tile class and has foud inner tiles
               to hold the position of player pieces. There are four instance of this class in board, which starts from top-left, top-right
               bottom-right, and finishes and bottom-left corner.
    2.3  WinTile: This clas is used to mark the winning position of a player. When a player all pieces arrived to this tile, the player wins.
               This class extends to Tile class, but the Shape of the tile is custome shape. There are four instance of this class in the board
               which are located in center of the game.
    2.4  Start: This class ...
        
  ----------------
