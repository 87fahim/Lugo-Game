package main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

public class EndGameAnimation extends Group {
	static Timeline timeline;
	public EndGameAnimation(float W, float H) {
		Group root = new Group();
		
		Group circles = createCircle(W);
		
		Rectangle colors = createColorfullRectangle(W, H);
		
		
		Group blendModeGroup = createBlendMode(circles, colors, W, H);
		createTimeline(circles, W, H);

		root.getChildren().add(blendModeGroup);	
		
//		this.setWidth(W);
//		this.setHeight(H);
//		this.getChildren().add(root);
	}

	public static Pane endGame(final float W, final float H) {
		Pane p = new Pane(new EndGameAnimation(W, H));
		p.setPrefSize(W, H);
		timeline.play();
		return p;
	}
	private void createTimeline(Group circles, float W, float H) {
		timeline = new Timeline();
		for (Node circle: circles.getChildren()) {
		    timeline.getKeyFrames().addAll(
		        new KeyFrame(Duration.ZERO, // set start position at 0
		            new KeyValue(circle.translateXProperty(), Math.random() * W),
		            new KeyValue(circle.translateYProperty(), Math.random() * H)
		        ),
		        new KeyFrame(new Duration(40000), // set end position at 40s
		            new KeyValue(circle.translateXProperty(), Math.random() * W),
		            new KeyValue(circle.translateYProperty(), Math.random() * H)
		        )
		    );
		}
		// play 40s of animation
		timeline.play();
	}

	private Group createBlendMode(Group circles, Rectangle colors, float W, float H) {
		colors.setBlendMode(BlendMode.OVERLAY);
		Group blendModeGroup = new Group(new Group(new Rectangle(W, H,  Color.BLACK), circles), colors);
		
		return blendModeGroup;
	}

	private Rectangle createColorfullRectangle(float W, float H) {
		Rectangle colors = new Rectangle(W, H,
		new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
				new Stop[] { new Stop(0, Color.web("#f8bd55")), new Stop(0.14, Color.web("#c0fe56")),
						new Stop(0.28, Color.web("#5dfbc1")), new Stop(0.43, Color.web("#64c2f8")),
						new Stop(0.57, Color.web("#be4af7")), new Stop(0.71, Color.web("#ed5fc2")),
						new Stop(0.85, Color.web("#ef504c")), new Stop(1, Color.web("#f2660f")), }));
//		colors.widthProperty().bind(this.widthProperty());
//		colors.heightProperty().bind(this.heightProperty());
		return colors;
	}

	private Group createCircle(float w) {
		Group circles = new Group();
		for (int i = 0; i < 30; i++) {
			Circle circle = new Circle(50, Color.web("white", 0.05));
			circle.setStrokeType(StrokeType.OUTSIDE);
			circle.setStroke(Color.web("white", 0.16));
			circle.setStrokeWidth(4);
			circles.getChildren().add(circle);
		}
		circles.setEffect(new BoxBlur(10, 10, 3));
		return circles;
	}
}
