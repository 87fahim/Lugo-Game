package board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import main.LudoKing;
import util.Util;

public class Star extends Pane{

	public Star( double size) {

		final Double[] values = new Double[] {

				0.0, size/3.0, 			//1
				size/2.7, size/3.0,		//2
				size/2.0, 0.0,			//3
				size/1.5, size/3d,		//4
				size, size/3d,			//5
				size*2.2/3, size*1.8/3,	//6
				size*2.7/3, size,		//7
				size/2, size*2.3/3,		//8
				size*0.5/3, size,		//9
				size*0.8/3, size*1.9/3,	//10
				0.0, size/3				//11
				
		};
		
		Polygon p = new Polygon();
		
		p.getPoints().addAll(values);
		
		p.setFill(Util.randomColor(0.3f));
		
		p.setStroke(LudoKing.BORDOR_COLOR);
		
		this.getChildren().add(p);
	}
	public void setX_Coordinate(double x) {
		this.setLayoutX(x - this.getBoundsInParent().getWidth()/2);
	}
	public void setY_Coordinate(double y) {
		this.setLayoutY(y - this.getBoundsInParent().getHeight()/2);
	}
}
