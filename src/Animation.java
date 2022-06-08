import javafx.stage.Stage;

public abstract class Animation {
    public abstract void animate(Stage stage);

    public Animation then(Animation other) {
        return null;
    }
}
