package die;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import main.LudoKing;


public class Die extends Pane {
	private double width;
	private double height;
	private int faceNumber;
	private Color color;
	private Rectangle rec;

	public Die(double width, double height) {
		this.width = width;
		this.height = height;
		this.faceNumber = 0;
		this.color = LudoKing.BACKGROUND_COLOR;
		this.getChildren().add(new Rectangle(width/2, height/2));
		this.getChildren().add(createBorder());
		this.setNumber(faceNumber);
	}

	public void setArc(double x, double y) {
		this.rec.setArcWidth(x);
		this.rec.setArcHeight(y);
	}
	
	public int getFaceNumber() {
		return this.faceNumber;
	}

	public double getDieWidth() {
		return this.width;
	}

	public double getDieHeight() {
		return this.height;
	}

	public void setNumber(int n) {
		List<Circle> list = new ArrayList<Circle>();
		createCircle(list, n);
		setCircleCoordinates(list, n);
		this.getChildren().clear();
		this.getChildren().add(createBorder());
		this.getChildren().addAll(list);
	}

	public void setColor(Color color) {
		this.color = color;
		this.rec.setFill(this.color);
	}
	private Rectangle createBorder() {
		rec = new Rectangle();
		rec.setWidth(this.width);
		rec.setHeight(this.height);
		rec.setFill(color);
		rec.setStroke(LudoKing.BORDOR_COLOR);
		rec.setStrokeWidth(this.width/100);
		rec.setStrokeType(StrokeType.INSIDE);
		rec.setArcWidth(this.width/8);
		rec.setArcHeight(this.width/8);
		return rec;
	}

	private void setCircleCoordinates(List<Circle> list, int n) {
		double radius = this.width / 10;
		switch (n) {
		case 1:
			list.get(0).setLayoutX(this.width / 2);
			list.get(0).setLayoutY(this.width / 2);
			break;

		case 2:
			list.get(0).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(0).setLayoutY(this.width * (1.0 / 3.0) - radius);
			list.get(1).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(1).setLayoutY(this.width * (2.0 / 3.0) + radius);
			break;

		case 3:
			list.get(0).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(0).setLayoutY(this.width * (1.0 / 3.0) - radius);

			list.get(1).setLayoutX(this.width * (1.0 / 2));
			list.get(1).setLayoutY(this.width * (1.0 / 2));

			list.get(2).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(2).setLayoutY(this.width * (2.0 / 3) + radius);
			break;

		case 4:
			list.get(0).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(0).setLayoutY(this.width * (1.0 / 3.0) - radius);

			list.get(1).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(1).setLayoutY(this.width * (1.0 / 3) - radius);

			list.get(2).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(2).setLayoutY(this.width * (2.0 / 3) + radius);

			list.get(3).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(3).setLayoutY(this.width * (2.0 / 3) + radius);

			break;

		case 5:
			list.get(0).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(0).setLayoutY(this.width * (1.0 / 3.0) - radius);

			list.get(1).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(1).setLayoutY(this.width * (1.0 / 3) - radius);

			list.get(2).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(2).setLayoutY(this.width * (2.0 / 3) + radius);

			list.get(3).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(3).setLayoutY(this.width * (2.0 / 3) + radius);

			list.get(4).setLayoutX(this.width * (1.0 / 2));
			list.get(4).setLayoutY(this.width * (1.0 / 2));

			break;

		case 6:
			list.get(0).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(0).setLayoutY(this.width * (1.0 / 3.0) - radius);

			list.get(1).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(1).setLayoutY(this.width * (1.0 / 3) - radius);

			list.get(2).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(2).setLayoutY(this.width * (1.0 / 2));

			list.get(3).setLayoutX(this.width * (2.0 / 3) + radius);
			list.get(3).setLayoutY(this.width * (2.0 / 3) + radius);

			list.get(4).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(4).setLayoutY(this.width * (2.0 / 3) + radius);

			list.get(5).setLayoutX(this.width * (1.0 / 3) - radius);
			list.get(5).setLayoutY(this.width * (1.0 / 2));
			break;
		default:
		}

	}

	public void setStrokeColor(Color c) {
		this.rec.setStroke(c);
	}
	public void setStrokeWidth(int s) {
		this.rec.setStrokeWidth(s);
	}
	private void createCircle(List<Circle> list, int i) {
		for (int j = 0; j < i; j++) {
			list.add(new Circle(this.width / 10));
		}
	}

}
