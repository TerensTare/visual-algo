package ADT;

import javafx.scene.layout.Pane;

public interface Queue<T> {
    // backend
    public void enqueue(T element);

    public T dequeue();

    public void removeDuplicates();

    public int size();

    public void clear();

    public int find(T element);

    // fronted
    public Pane pane();
}