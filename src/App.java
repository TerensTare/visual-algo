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
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: transparent;");
        Scene scene = new Scene(bp, 1200, 600);
        scene.setFill(new RadialGradient(
        0, 0, 0, 0, 1, true,                  
        CycleMethod.NO_CYCLE,                 
        new Stop(0, Color.web("#81c483")),    
        new Stop(1, Color.web("#fcc200")))
);

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
        inputBox.setStyle("-fx-background-color: transparent;");

        inputBox.setPadding(new Insets(0, 0, 20, 0));
        inputBox.setSpacing(60);
        inputBox.getChildren().addAll(txt, buttonPane);

        VBox root = new VBox(inputBox, widgets.queue());
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setStyle("-fx-background-color: transparent;");

       
        bp.setTop(root);
        bp.setStyle("-fx-background-color: transparent;");


        stage.getIcons().add(new Image("/images/icon2.jpg"));
        stage.setTitle("QUEUES");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}