package raf.graffito.dsw.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateManager {
    private State currentState;

    public StateManager() {

        this.currentState = new SelectState();
    }

    public void setState(State state) {
        if (state != null) {
            this.currentState = state;
        }
    }
}

