import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("/images/icon2.jpg"));
        stage.setTitle("QUEUES");
        TextField txt = new TextField();
        Button enqueue = new Button();
        enqueue.setText("Enqueue");
        Button dequeue = new Button();
        dequeue.setText("Dequeue");
        Button find = new Button();
        find.setText("Find");
        HBox inputBox = new HBox();
        inputBox.setPadding(new Insets(10));
        inputBox.setSpacing(10);
        inputBox.getChildren().addAll(txt, enqueue, dequeue, find);
        BorderPane bp = new BorderPane();
        VBox root = new VBox(inputBox);
        root.setAlignment(Pos.CENTER);
        bp.setTop(root);
        HBox queue = new HBox();
        queue.setSpacing(20);
        ArrayList<TextField> list = new ArrayList<>(); // list qe ruan cdo txt
        if (list.isEmpty()) {
            dequeue.setVisible(false);
            find.setVisible(false);
        }
        enqueue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StackPane st = new StackPane();
                st = createNode(txt);
                queue.getChildren().add(st);
                txt.clear();
                list.add(txt); // shton txt n list q t rujm cdo input, mund t perdoret pr funksione t tjera tn
                               // e km ber pr dequeue
                if (!list.isEmpty()) {
                    dequeue.setVisible(true);
                    find.setVisible(true);
                }
            }
        });
        dequeue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                StackPane st = new StackPane();
                st = createNode(list.get(0)); // krijon n st me index 0 t listes se txt
                int index = queue.getChildren().indexOf(st) + 1; // mer index te st tek children t queue
                queue.getChildren().remove(index); // fshin child n index, q do jet i pari
                list.remove(index);
                if (list.isEmpty()) {
                    dequeue.setVisible(false);
                    find.setVisible(false);
                }
            }
        });
        root.getChildren().add(queue);
        stage.setScene(new Scene(bp, 1200, 600));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private static <T> StackPane createNode(TextField value) {
        StackPane pane = new StackPane(
                new Rectangle(200, 100, Color.AQUAMARINE),
                new Label(value.getText()));
        return pane;
    }
}
