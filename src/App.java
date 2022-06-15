import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class App extends Application {
    private VBox root;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: transparent;");
        Scene scene = new Scene(bp, 1200, 600);
        scene.setFill(new RadialGradient(
                0, 0, 0, 0, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#636363")),
                new Stop(1, Color.web("#a2ab58"))));

        WidgetManager widgets = new WidgetManager();
        ChoiceBox<String> implBox = widgets.implBox();
        TextField txt = widgets.textField();

        Button enqueue = buttonStyle(widgets.enqueue());
        Button dequeue = buttonStyle(widgets.dequeue());
        Button clear = buttonStyle(widgets.clear());
        Button find = buttonStyle(widgets.find());
        Button removeDuplicates = buttonStyle(widgets.removeDuplicates());

        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.addRow(0, enqueue, dequeue, clear, find, removeDuplicates);
        buttonPane.setStyle("-fx-background-color: transparent;");

        HBox inputBox = new HBox();
        inputBox.setStyle("-fx-background-color: transparent;");
        inputBox.setPadding(new Insets(20, 5, 55, 5));
        inputBox.setSpacing(40);
        inputBox.getChildren().addAll(txt, buttonPane);

        root = new VBox(inputBox, widgets.queue(), implBox);
        implBox.setOnAction(e -> {
            switch (implBox.getValue()) {
                case "Array":
                    widgets.queue = new ArrayQueue<>();
                    root = new VBox(inputBox, widgets.queue(), implBox);
                    bp.setTop(root);
                    break;

                case "Stack":
                    widgets.queue = new StackQueue<>();
                    root = new VBox(inputBox, widgets.queue(), implBox);
                    bp.setTop(root);
                    break;
            }

        });
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 30, 10, 30));
        root.setStyle("-fx-background-color: transparent;");

        bp.setTop(root);
        bp.setStyle("-fx-background-color: transparent;");

        stage.getIcons().add(new Image("/images/icon2.jpg"));
        stage.setTitle("QUEUES");
        stage.setScene(scene);
        stage.show();
    }

    private static Button buttonStyle(Button button) {

        button.setPrefSize(160, 40);
        button.setStyle("-fx-font-size:15; -fx-background-color: #a14633; -fx-text-fill: white");
        return button;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}