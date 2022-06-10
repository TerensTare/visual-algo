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

// Checklist before inserting a new widget:
// 1. Add a variable to WidgetManager
// 2. (optional) Add a setup<WidgetName> method to WidgetManager
// 3. create the widget on the constructor
// 4. If you did step 2. add a call to setup<WidgetName> in the constructor

public class WidgetManager {
    private ArrayList<TextField> list; // nuk perdoret m tek dequeue por ende ruan infot e txtfield

    private HBox queue; // queue frontend
    private TextField txt; // duhet ta ruajme ketu sepse aksesohen nga butonat
    // butonat
    private Button enqueue;
    private Button dequeue;
    private Button find;

    private Button clear;

    public WidgetManager() {
        list = new ArrayList<>();

        queue = new HBox();
        queue.setSpacing(20);

        txt = new TextField();
        txt.setPromptText("Enter a value");

        setupEnqueueButton();
        setupDequeueButton();
        setupFindButton();
        setupClearButton();

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

    public Button clear() {
        return clear;
    }

    public Button find() {
        return find;
    }

    // metodat private

    private void setupEnqueueButton() {
        enqueue = new Button("Enqueue");

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
        dequeue = new Button("Dequeue");

        dequeue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                queue.getChildren().remove(0);
                list.remove(0);

                if (queue.getChildren().size() == 0) {
                    showButtons(false);
                }
            }
        });
    }

    private void setupClearButton() {
        clear = new Button("Clear");

        clear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                queue.getChildren().removeAll(queue.getChildren());
                list.clear();
                showButtons(false);
            }
        });

    }

    private void setupFindButton() {
        find = new Button("Find");

        // TODO: handle click event
    }

    private void showButtons(boolean show) {
        dequeue.setVisible(show);
        clear.setVisible(show);
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