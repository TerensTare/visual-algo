import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WidgetManager {
    private ArrayList<TextField> list;

    private HBox queue; // queue frontend
    private TextField txt; // duhet ta ruajme ketu sepse aksesohen nga butonat
    // butonat
    private Button enqueue;
    private Button dequeue;
    private Button find;

    public WidgetManager() {
        list = new ArrayList<>();

        queue = new HBox();
        queue.setSpacing(20);

        txt = new TextField();
        txt.setPromptText("Enter a value");

        setupEnqueueButton();
        setupDequeueButton();
        setupFindButton();

        // fillimisht butonat qe duan nje queue jo boshe nuk shfaqen
        showButtons(false);
    }

    public HBox queue() {
        return queue;
    }

    public TextField textField() {
        return txt;
    }

    public Button enqueue() {
        return enqueue;
    }

    public Button dequeue() {
        return dequeue;
    }

    public Button find() {
        return find;
    }

    // metodat private

    private void setupEnqueueButton() {
        enqueue = new Button();
        enqueue.setText("Enqueue");

        enqueue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StackPane st = createNode(txt);
                queue.getChildren().add(st);
                list.add(txt); // shton txt n list q t rujm cdo input, mund t perdoret pr funksione t tjera tn
                               // e km ber pr dequeue
                txt.clear();

                // shfaqi butonat e fshehur
                showButtons(true);
            }
        });
    }

    private void setupDequeueButton() {
        dequeue = new Button();
        dequeue.setText("Dequeue");

        dequeue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                StackPane st = new StackPane();
                st = createNode(list.get(0)); // krijon n st me index 0 t listes se txt
                int index = queue.getChildren().indexOf(st) + 1; // mer index te st tek children t queue
                queue.getChildren().remove(index); // fshin child n index, q do jet i pari

                // fshihi butonat nqs queue eshte bosh
                if (queue.getChildren().size() == 0) {
                    showButtons(false);
                }
            }
        });
    }

    private void setupFindButton() {
        find = new Button();
        find.setText("Find");

        // TODO: handle click event
    }

    private void showButtons(boolean show) {
        dequeue.setVisible(show);
        find.setVisible(show);
    }

    private static <T> StackPane createNode(TextField value) {
        StackPane pane = new StackPane(
                new Rectangle(200, 100, Color.AQUAMARINE),
                new Label(value.getText()) //
        );
        return pane;
    }
}