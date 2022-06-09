package anims;

import javafx.stage.Stage;

// Nje klase qe percakton nje animacion bazik. 
public abstract class Animation {
    public abstract void animate(Stage stage);

    public AnimationNode then(Animation next) {
        return new AnimationNode(this, next);
    }
}

class AnimationNode extends Animation {
    private Animation first; // Animacioni i pare.
    private Animation next; // Animacioni vijues. Nqs eshte null, ky eshte animacioni i fundit.

    public AnimationNode(Animation first, Animation next) {
        this.first = first;
        this.next = next;
    }

    @Override
    public void animate(Stage stage) {
        first.animate(stage); // Nis animacionin e pare.

        if (next != null) { // Nis animacionin tjeter, nqs egziston.
            next.animate(stage);
        }
    }

}