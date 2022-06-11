import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Glow;
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
    private ArrayList<String> list; // list which saves txtfield string inputs, used in find

    private HBox queue; // queue frontend
    private TextField txt; // inputs are saved here to be accessed from the buttons
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

        // firstly methods which require a non-empty queue are hidden
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

    // private methods

    private void setupEnqueueButton() {
        enqueue = new Button("Enqueue");

        enqueue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txt.getText().trim().isEmpty()) {
                    Alert noText = new Alert(AlertType.INFORMATION);
                    noText.setTitle("Alert");
                    noText.setHeaderText("No Inputs!");
                    noText.setContentText("Please input any information");
                    noText.show();
                } else {
                    StackPane st = createNode(txt);
                    queue.getChildren().add(st);
                    list.add(txt.getText().trim()); // adds the contents of txtfield to the list
                    txt.clear();

                    // makes buttons visible
                    showButtons(true);
                }
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

        find.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                int index = list.indexOf(txt.getText().trim()); // index of desired node
                if (index == -1) {
                    Alert noText = new Alert(AlertType.INFORMATION);
                    noText.setTitle("Alert");
                    noText.setHeaderText("No Such Inputs!");
                    noText.setContentText("Please input another value");
                    noText.show();
                } else {
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    Glow glow = new Glow(0.5);
                    queue.getChildren().get(index).setEffect(glow);
                    executorService.schedule(() -> setGlow(queue), 2, TimeUnit.SECONDS); // turns off glow after 2 sec
                }
            }
        });
    }

    private void showButtons(boolean show) {
        dequeue.setVisible(show);
        clear.setVisible(show);
        find.setVisible(show);
    }

    private static void setGlow(HBox queue) {
        Glow glow = new Glow(0);
        int size = queue.getChildren().size();
        for (int i = 0; i < size; i++) {
            queue.getChildren().get(i).setEffect(glow);
        }
    }

    private static <T> StackPane createNode(TextField value) {
        StackPane pane = new StackPane(
                new Rectangle(200, 100, Color.AQUAMARINE),
                new Label(value.getText()));
        return pane;
    }
}