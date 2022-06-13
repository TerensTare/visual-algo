import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
// Checklist before inserting a new widget:
// 1. Add a variable to WidgetManager
// 2. (optional) Add a setup<WidgetName> method to WidgetManager
// 3. create the widget on the constructor
// 4. If you did step 2. add a call to setup<WidgetName> in the constructor

public class WidgetManager {
    private ArrayList<String> list; // list which saves txtfield string inputs, used in find

    private FlowPane queue; // queue frontend
    private TextField txt; // inputs are saved here to be accessed from the buttons
    private Button enqueue;
    private Button dequeue;
    private Button find;
    private Button removeDuplicates;
    private Button clear;
    private Button sort;

    public WidgetManager() {
        list = new ArrayList<>();

        queue = new FlowPane();
        queue.setHgap(10);
        queue.setVgap(20);

        txt = new TextField();
        txt.setPromptText("Enter a value");
        txt.setPrefSize(180, 30);

        setupEnqueueButton();
        setupDequeueButton();
        setupFindButton();
        setupClearButton();
        setupRemoveDuplicatesButton();
        setupSortButton();

        // firstly methods which require a non-empty queue are hidden
        showButtons(false);
    }

    public FlowPane queue() {
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

    public Button removeDuplicates() {
        return removeDuplicates;
    }

    public Button sort() {
        return sort;
    }

    // private methods

    private void setupEnqueueButton() {
        enqueue = new Button("Enqueue");

        enqueue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (txt.getText().trim().isEmpty()) {
                    txt.setPromptText("Try writing a number, eg. 10"); // to set the hint text
                    txt.getParent().requestFocus();
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

                var str = animateNodeRemoval(queue.getChildren().get(0)); // create str with node 0
                str.play();
                str.setOnFinished(new EventHandler<ActionEvent>() { // executes when when seqtransition ends
                    @Override
                    public void handle(ActionEvent arg0) {
                        queue.getChildren().remove(0);
                        list.remove(0);
                    }
                });

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

                var str = animateClear(queue);
                str.play();
                str.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        queue.getChildren().removeAll(queue.getChildren());
                    }

                });

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
                    DropShadow shadow = new DropShadow();
                    shadow.setRadius(10);
                    shadow.setColor(Color.RED);
                    queue.getChildren().get(index).setEffect(shadow);
                    executorService.schedule(() -> setGlow(queue), 2, TimeUnit.SECONDS); // turns off glow after 2 sec
                }
            }
        });
    }

    private void setupRemoveDuplicatesButton() {
        removeDuplicates = new Button("Remove Duplicates");
        removeDuplicates.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Set<String> set = new HashSet<>();
                // Set<Integer> indexSet = new HashSet<>(); // saves unique copy of dup index
                // ArrayList<Integer> indexList = new ArrayList<>();
                ArrayList<String> oldList = new ArrayList<>();
                oldList.addAll(list);
                // var str = new SequentialTransition();
                for (int i = 0; i < list.size(); i++) {
                    if (set.contains(list.get(i))) {
                        // indexSet.add(i);
                        // var ft = new FadeTransition(Duration.millis(500),
                        // queue.getChildren().get(i)); // creates fade
                        // ft.setFromValue(1.0);
                        // ft.setToValue(0.0);
                        // str.getChildren().add(ft);
                        // str.play();
                        queue.getChildren().remove(i);
                        list.remove(i);
                        i--;
                    }
                    set.add(list.get(i));
                }
                set.clear();

                // indexList.addAll(indexSet);
                // str.setOnFinished(new EventHandler<ActionEvent>() {
                // @Override
                // public void handle(ActionEvent arg0) {
                // int cnt = 1;

                // for (int i = 0; i < indexList.size(); i++) {
                // int index = indexList.get(i);
                // if (i > 0) {
                // index -= cnt;
                // cnt++;
                // }
                // StackPane pane = (StackPane) queue.getChildren().get(index);
                // Label lb = (Label) pane.getChildren().get(1);
                // System.out.println(lb.getText());
                // System.out.println(index);
                // queue.getChildren().remove(index);
                // }

                // }

                // });

                // set.clear();
            }
        });

    }

    private void setupSortButton() {
        sort = new Button("Sort");

        sort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                list.sort((lhs, rhs) -> Integer.parseInt(lhs) - Integer.parseInt(rhs));

                queue.getChildren().removeAll(queue.getChildren());
                for (String s : list) {
                    StackPane st = createNode(s);
                    queue.getChildren().add(st);
                }
            }
        });
    }

    private void showButtons(boolean show) {
        dequeue.setVisible(show);
        clear.setVisible(show);
        find.setVisible(show);
        removeDuplicates.setVisible(show);
        sort.setVisible(show);
    }

    private static void setGlow(FlowPane queue) {
        int size = queue.getChildren().size();
        for (int i = 0; i < size; i++) {
            queue.getChildren().get(i).setEffect(null);
        }
    }

    // TODO: remove this overload
    private static <T> StackPane createNode(TextField value) {
        return createNode(value.getText());

        // Rectangle rect = new Rectangle(100, 50);
        // rect.setArcWidth(30.0);
        // rect.setArcHeight(20.0);

        // rect = (Rectangle) animateNodeCreation(rect);
        // StackPane pane = new StackPane(rect, new Label(value.getText()));

        // return pane;
    }

    // TODO: why do we need <T> here?
    private static <T> StackPane createNode(String text) {

        Rectangle rect = new Rectangle(100, 50);
        rect.setArcWidth(30.0);
        rect.setArcHeight(20.0);

        rect = (Rectangle) animateNodeCreation(rect);
        StackPane pane = new StackPane(rect, new Label(text));

        return pane;
    }

    private static Node animateNodeCreation(Node rect) {

        var fltr = new FillTransition(Duration.millis(1000), (Shape) rect, Color.WHITE, Color.AQUAMARINE);
        fltr.setCycleCount(2);
        fltr.setAutoReverse(true);

        var str = new SequentialTransition();
        str.getChildren().add(fltr);
        str.play();

        return rect;
    }

    private static SequentialTransition animateNodeRemoval(Node node) { // creates seqtransition

        var ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        var str = new SequentialTransition();
        str.getChildren().add(ft);
        str.play();

        return str;
    }

    private static SequentialTransition animateClear(FlowPane queue) {

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