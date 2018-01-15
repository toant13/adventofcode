package day25;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InputFunctions implements Functions {

    Map<States, Consumer<Tape>> map;

    public InputFunctions() {
        map = new HashMap<>();
        map.put(States.A, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.positionValues.remove(tape.cursorPosition);
                tape.cursorPosition -= 1;
                tape.currentState = States.C;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition += 1;
                tape.currentState = States.B;
            }
        });

        map.put(States.B, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.cursorPosition += 1;
                tape.currentState = States.D;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition -= 1;
                tape.currentState = States.A;
            }
        });

        map.put(States.C, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.positionValues.remove(tape.cursorPosition);
                tape.cursorPosition -= 1;
                tape.currentState = States.E;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition += 1;
                tape.currentState = States.A;
            }
        });

        map.put(States.D, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.positionValues.remove(tape.cursorPosition);
                tape.cursorPosition += 1;
                tape.currentState = States.B;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition += 1;
                tape.currentState = States.A;
            }
        });

        map.put(States.E, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.cursorPosition -= 1;
                tape.currentState = States.C;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition -= 1;
                tape.currentState = States.F;
            }
        });

        map.put(States.F, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.cursorPosition += 1;
                tape.currentState = States.A;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition += 1;
                tape.currentState = States.D;
            }
        });
    }

    @Override
    public Map<States, Consumer<Tape>> getFunctions() {
        return map;
    }
}
