import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ADT.Queue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

// TODO:
// animate removeDuplicates

public class ArrayQueue<T> implements Queue<T> {
    private ArrayList<T> elements;

    private FlowPane pane;

    public ArrayQueue() {
        elements = new ArrayList<>();

        pane = new FlowPane();
        pane.setHgap(10);
        pane.setVgap(20);
    }

    @Override
    public void enqueue(T element) {
        pane.getChildren().add(QueueFX.createNode(element.toString()));
        elements.add(element);
    }

    @Override
    public T dequeue() {
        if (elements.size() == 0) {
            return null;
        }

        var element = elements.get(0);

        var anim = QueueFX.animateNodeRemoval(pane.getChildren().get(0));
        anim.play();

        anim.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                pane.getChildren().remove(0);
                elements.remove(0);
            }
        });

        return element;
    }

    @Override
    public void removeDuplicates() {
        for (int j = 0; j < elements.size() - 1; ++j) {
            removeDuplicatesImpl(j, j + 1);
        }
    }

    @Override
    public void clear() {
        var anim = QueueFX.animateClear(pane);
        anim.play();

        anim.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                pane.getChildren().clear();
                elements.clear();
            }
        });
    }

    @Override
    public int find(T element) {
        int index = elements.indexOf(element);
        if (index != -1) {
            DropShadow shadow = new DropShadow();
            shadow.setRadius(10);
            shadow.setColor(Color.RED);

            pane.getChildren().get(index).setEffect(shadow);

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> QueueFX.setGlow(pane), 2, TimeUnit.SECONDS);
        }

        return index;
    }

    @Override
    public FlowPane pane() {
        return pane;
    }

    // helper methods
    private void removeDuplicatesImpl(int j, int i) {
        if (i == elements.size()) {
            return;
        } else {
            if (elements.get(j).equals(elements.get(i))) {
                var anim = QueueFX.animateNodeRemoval(pane.getChildren().get(i));
                anim.play();

                Integer idx = i;

                anim.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        int index = idx.intValue();

                        pane.getChildren().remove(index);
                        elements.remove(index);

                        removeDuplicatesImpl(j, index);
                    }
                });
            } else {
                removeDuplicatesImpl(j, i + 1);
            }
        }
    }
}
