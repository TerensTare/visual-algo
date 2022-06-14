import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

// A class that contains helper methods related to animating the operations on a queue.

public class QueueFX {
    public static void setGlow(FlowPane queue) {
        int size = queue.getChildren().size();
        for (int i = 0; i < size; i++) {
            queue.getChildren().get(i).setEffect(null);
        }
    }

    public static StackPane createNode(String text) {

        Rectangle rect = new Rectangle(100, 50);
        rect.setArcWidth(30.0);
        rect.setArcHeight(20.0);

        rect = (Rectangle) animateNodeCreation(rect);
        StackPane pane = new StackPane(rect, new Label(text));

        return pane;
    }

    public static Node animateNodeCreation(Node rect) {

        var fltr = new FillTransition(Duration.seconds(1), (Shape) rect, Color.WHITE, Color.AQUAMARINE);
        fltr.setCycleCount(2);
        fltr.setAutoReverse(true);

        var str = new SequentialTransition();
        str.getChildren().add(fltr);
        str.play();

        return rect;
    }

    public static SequentialTransition animateNodeRemoval(Node node) { // creates seqtransition

        var ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        var str = new SequentialTransition();
        str.getChildren().add(ft);
        str.play();

        return str;
    }

    static SequentialTransition animateClear(FlowPane queue) {

        var str = new SequentialTransition();

        for (int i = 0; i < queue.getChildren().size(); i++) {

            var ft = new FadeTransition(Duration.millis(300), queue.getChildren().get(i)); // creates fade
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            str.getChildren().add(ft);
        }

        return str;
    }
}
