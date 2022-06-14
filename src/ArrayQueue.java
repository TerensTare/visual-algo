import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ADT.Queue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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
        var set = new HashSet<T>();

        for (int i = 0; i < elements.size(); i++) {
            if (set.contains(elements.get(i))) {
                pane.getChildren().remove(i);
                elements.remove(i);
                --i;
            } else {
                set.add(elements.get(i));
            }
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
    public Pane pane() {
        return pane;
    }
}
