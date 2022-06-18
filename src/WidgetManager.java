import ADT.Queue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

// Checklist before inserting a new widget:
// 1. Add a variable to WidgetManager
// 2. (optional) Add a setup<WidgetName> method to WidgetManager
// 3. create the widget on the constructor
// 4. If you did step 2. add a call to setup<WidgetName> in the constructor

public class WidgetManager {
    public Queue<Integer> queue; // queue frontend

    private ChoiceBox<String> impl; // choicebox for choosing the implementation
    private TextField txt; // inputs are saved here to be accessed from the buttons

    private Button enqueue;
    private Button dequeue;
    private Button find;
    private Button removeDuplicates;
    private Button clear;

    public WidgetManager() {
        queue = new ArrayQueue<>();

        impl = new ChoiceBox<>();
        impl.getItems().addAll("Array", "Stack");
        impl.valueProperty().set("Array");

        txt = new TextField();
        txt.setPromptText("Enter a value");
        txt.setPrefSize(220, 40);

        setupEnqueueButton();
        setupDequeueButton();
        setupFindButton();
        setupClearButton();
        setupRemoveDuplicatesButton();

        // firstly methods which require a non-empty queue are hidden
        showButtons(false);
    }

    public Pane queue() {
        return queue.pane();
    }

    public ChoiceBox<String> implBox() {
        return impl;
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

    public Button removeDuplicates() {
        return removeDuplicates;
    }

    // private methods

    private void setupEnqueueButton() {
        enqueue = new Button("Enqueue");

        enqueue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txt.getText().trim().isEmpty()) {
                    txt.setPromptText("Try writing a number."); // to set the hint text
                    txt.getParent().requestFocus();
                } else {
                    Integer value = Integer.parseInt(txt.getText());
                    queue.enqueue(value);
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
                queue.dequeue();

                if (queue.size() == 1) {
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
                queue.clear();
                showButtons(false);
            }
        });

    }

    private void setupFindButton() {
        find = new Button("Find");

        find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                String text = txt.getText().trim();

                if (text == "") {
                    Alert noText = new Alert(AlertType.INFORMATION);
                    noText.setTitle("Alert");
                    noText.setHeaderText("No Inputs Given!");
                    noText.setContentText("Please input some value");
                    noText.show();
                    return;
                }

                Integer value = Integer.parseInt(text);
                if (queue.find(value) == null) {
                    Alert noText = new Alert(AlertType.INFORMATION);
                    noText.setTitle("Alert");
                    noText.setHeaderText("No Such Inputs!");
                    noText.setContentText("Please input another value");
                    noText.show();
                } else {
                    txt.clear();
                }
            }
        });
    }

    private void setupRemoveDuplicatesButton() {
        removeDuplicates = new Button("Remove Duplicates");
        removeDuplicates.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                queue.removeDuplicates();
            }
        });

    }

    private void showButtons(boolean show) {
        dequeue.setVisible(show);

        // These actions don't work with the stack implementation.
        // So, hide them when the stack implementation is chosen.
        if (queue instanceof StackQueue<Integer>) {
            clear.setVisible(false);
            find.setVisible(false);
            removeDuplicates.setVisible(false);
        } else {
            clear.setVisible(show);
            find.setVisible(show);
            removeDuplicates.setVisible(show);
        }
    }
}