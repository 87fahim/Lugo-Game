package board;

import javafx.scene.shape.Polygon;
import main.LudoKing;

public class WinTile extends Tile {

	public WinTile(double size) {
		super((int) size);
		Polygon p = new Polygon();
		p.getPoints().addAll(new Double[] { 0.0, 0.0, 3 * size, 0.0, 2 * size, size, size, size, 0.0, 0.0 });

		p.setFill(LudoKing.BACKGROUND_COLOR);
		p.setStroke(LudoKing.BORDOR_COLOR);

		this.setPrefSize(3 * size, size);
		this.getChildren().removeAll(this.getChildren());
		this.getChildren().add(p);
		this.setTileShape(p);

	}

}
