import java.util.Stack;

import ADT.Queue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class StackQueue<T> implements Queue<T> {
    private Stack<T> stack1;
    private Stack<T> stack2;

    private VBox pane;

    private FlowPane stack1Pane;
    private FlowPane stack2Pane;

    public StackQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();

        stack1Pane = new FlowPane();
        stack1Pane.setHgap(10);
        stack1Pane.setVgap(20);

        stack2Pane = new FlowPane();
        stack2Pane.setHgap(10);
        stack2Pane.setVgap(20);

        pane = new VBox(stack1Pane, stack2Pane);
    }

    @Override
    public void enqueue(T element) {
        push(stack1Pane, stack1, element);
    }

    @Override
    public T dequeue() {
        if (stack1.size() == 0) {
            return null;
        }

        transition(stack1Pane, stack2Pane, stack1, stack2);

        var removeHead = QueueFX.animateNodeRemoval(stack2Pane.getChildren().get(stack2.size() - 1));
        removeHead.play();

        stack2Pane.getChildren().remove(stack2.size() - 1);

        var element = stack2.pop();

        transition(stack2Pane, stack1Pane, stack2, stack1);

        return element;
    }

    @Override
    public void removeDuplicates() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public int find(T element) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Pane pane() {
        return pane;
    }

    // helper methods
    private static <T> void push(FlowPane pane, Stack<T> stack, T value) {
        pane.getChildren().add(QueueFX.createNode(value.toString()));
        stack.push(value);
    }

    private static <T> void transition(FlowPane fromPane, FlowPane toPane, Stack<T> from, Stack<T> to) {
        while (from.size() > 0) {
            var node = fromPane.getChildren().get(from.size() - 1);

            var anim = QueueFX.animateNodeRemoval(node);
            anim.play();

            anim.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    fromPane.getChildren().remove(node);
                }
            });

            var value = from.pop();
            to.push(value);

            toPane.getChildren().add(QueueFX.createNode(value.toString()));
        }

        // fromPane.getChildren().clear();
    }

    @Override
    public int size() {
        return stack1.size();
    }
}
