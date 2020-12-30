package die;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Cube extends Group {
	private final Rotate rx = new Rotate(0, Rotate.X_AXIS);
	private final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
	private final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
	private final List<Die> sides;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public Cube(double size, Color color, double shade) {
		this.sides = new ArrayList<>();
		getTransforms().addAll(rz, ry, rx);
		createCube(size, color, shade);
		setStrokeWidth((int) (size/40));
		setArc(size/3, size/3);

		getChildren().addAll(this.sides);
	}

	private void createCube(double size, Color color, double shade) {
		// BACK FACE
		Die d1 = new Die(size, size);
		d1.setTranslateX(-0.5 * size);
		d1.setTranslateY(-0.5 * size);
		d1.setTranslateZ(0.5 * size);
		d1.setColor(color.deriveColor(0.0, 1.0, (1 - 0.5 * shade), 1.0));
		d1.setNumber(ONE);

		// BOTTON FACE
		Die d2 = new Die(size, size);
		d2.setTranslateX(-0.5 * size);
		d2.setTranslateY(0 * size);
		d2.setRotationAxis(Rotate.X_AXIS);
		d2.setRotate(90);
		d2.setColor(color.deriveColor(0.0, 1.0, (1 - 0.4 * shade), 1.0));
		d2.setNumber(TWO);

		// RIGHT FACE
		Die d3 = new Die(size, size);
		d3.setTranslateX(-1.0 * size);
		d3.setTranslateY(-0.5 * size);
		d3.setRotationAxis(Rotate.Y_AXIS);
		d3.setRotate(90);
		d3.setColor(color.deriveColor(0.0, 1.0, (1 - 0.3 * shade), 1.0));
		d3.setNumber(THREE);

		// LEFT FACE
		Die d4 = new Die(size, size);
		d4.setTranslateX(0 * size);
		d4.setTranslateY(-0.5 * size);
		d4.setRotationAxis(Rotate.Y_AXIS);
		d4.setRotate(90);
		d4.setColor(color.deriveColor(0.0, 1.0, (1 - 0.2 * shade), 1.0));
		d4.setNumber(FOUR);

		// TOP FACE
		Die d5 = new Die(size, size);
		d5.setTranslateX(-0.5 * size);
		d5.setTranslateY(-1.0 * size);
		d5.setRotationAxis(Rotate.X_AXIS);
		d5.setRotate(90);
		d5.setColor(color.deriveColor(0.0, 1.0, (1 - 0.1 * shade), 1.0));
		d5.setNumber(FIVE);

		// FRONT FACE
		Die d6 = new Die(size, size);
		d6.setTranslateX(-0.5 * size);
		d6.setTranslateY(-0.5 * size);
		d6.setTranslateZ(-0.5 * size);
		d6.setColor(color);
		d6.setNumber(SIX);

		sides.add(d1);
		sides.add(d2);
		sides.add(d3);
		sides.add(d4);
		sides.add(d5);
		sides.add(d6);

	}

	public int getSideNumber(int index) {
		return this.sides.get(index).getFaceNumber();
	}
	
	public void setStrokeColor(Color c) {
		for (int i = 0; i < this.sides.size(); i++) {
			this.sides.get(i).setStrokeColor(c);
		}
	}

	public void setStrokeWidth(int s) {
		for (int i = 0; i < this.sides.size(); i++) {
			this.sides.get(i).setStrokeWidth(s);
		}
	}

	public void setArc(double x, double y) {
		for (int i = 0; i < this.sides.size(); i++) {
			this.sides.get(i).setArc(x, y);
		}
	}
}
