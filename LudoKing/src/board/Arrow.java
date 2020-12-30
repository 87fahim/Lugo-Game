package board;


import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.LudoKing;

public class Arrow extends Pane{
	Shape s;
	
	public Arrow(float size,  int degree){
		
		double x =  size/3;
		
		Rectangle r1 = new Rectangle(x, x);
		r1.setLayoutX(0);
		r1.setLayoutY(0);
		
		Rectangle r2 = new Rectangle(x, x);
		r2.setLayoutX(size/9);
		r2.setLayoutY(size/9);
		
		s = Shape.subtract(r1, r2);
		s.setRotate(degree);
		s.setStroke(LudoKing.BORDOR_COLOR);
		s.setStrokeWidth(1);
		s.setFill(LudoKing.BACKGROUND_COLOR);
		
		this.getChildren().addAll(s);
		
	}
	
	public static Shape makeArrow(int size , int degree) {
		return new Arrow(size, degree).getArrowShape();
	}
	
	public Shape getArrowShape() {
		return this.s;
	}



}
