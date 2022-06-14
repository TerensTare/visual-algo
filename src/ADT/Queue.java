package ADT;

import javafx.scene.layout.FlowPane;

public interface Queue<T> {
    // backend
    public void enqueue(T element);

    public T dequeue();

    // public boolean isEmpty();

    public void removeDuplicates();

    public void clear();

    public int find(T element);

    // fronted
    public FlowPane pane();
}