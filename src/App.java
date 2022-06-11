import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       
        WidgetManager widgets = new WidgetManager();
        TextField txt = widgets.textField();
        Button enqueue = widgets.enqueue();
        Button dequeue = widgets.dequeue();
        Button clear = widgets.clear();
        Button find = widgets.find();

        HBox inputBox = new HBox();
        GridPane buttonPane = new GridPane();
        buttonPane.add(enqueue, 1,1);
        buttonPane.add(dequeue, 2,1);
        buttonPane.add(clear, 3,1);
        buttonPane.add(find, 4,1);
        buttonPane.setHgap(10);

        inputBox.setPadding(new Insets(10));
        inputBox.setSpacing(60);
        inputBox.getChildren().addAll(txt, buttonPane);

        VBox root = new VBox(inputBox, widgets.queue());
        root.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setTop(root);

        stage.getIcons().add(new Image("/images/icon2.jpg"));
        stage.setTitle("QUEUES");
        stage.setScene(new Scene(bp, 1200, 600));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}