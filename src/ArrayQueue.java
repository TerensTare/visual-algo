import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ADT.Queue;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
        Set<T> set = new HashSet<>();
        var str = new SequentialTransition();
        for (int i = 0; i < elements.size(); i++) {
            if (set.contains(elements.get(i))) {
                var ft = new FadeTransition(Duration.millis(500), pane.getChildren().get(i)); // creates fade
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                str.getChildren().add(ft);
            }
            set.add(elements.get(i));
        }
        set.clear();
        str.play();
        str.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent arg0) {
                for (int i = 0; i < elements.size(); i++) {
                    if (set.contains(elements.get(i))) {
                        pane.getChildren().remove(i);
                        elements.remove(i);
                        i--;
                    }
                    set.add(elements.get(i));
                }
            }
        });
        set.clear();
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
    public ArrayList<Integer> find(T element) {
        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i) == element)
                indexList.add(i);
        }

        if (!indexList.isEmpty()) {
            DropShadow shadow = new DropShadow();
            shadow.setRadius(10);
            shadow.setColor(Color.RED);

            for (int i = 0; i < indexList.size(); i++) {
                int index = indexList.get(i);
                pane.getChildren().get(index).setEffect(shadow);
            }

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> QueueFX.setGlow(pane), 2, TimeUnit.SECONDS);
        }

        return indexList;
    }

    @Override
    public Pane pane() {
        return pane;
    }

    @Override
    public int size() {
        return elements.size();
    }
}
