package day25;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Tape {

    int cursorPosition;
    Set<Integer> positionValues;
    States currentState;
    Map<States, Consumer<Tape>> map;

    public Tape(int cursorPosition, Functions functions) {
        this.cursorPosition = cursorPosition;
        this.positionValues = new HashSet<>();
        this.currentState = States.A;
        this.map = functions.getFunctions();
    }

    public void process(){
        map.get(currentState).accept(this);

    }
}
