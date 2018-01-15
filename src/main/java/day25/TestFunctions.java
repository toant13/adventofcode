package day25;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TestFunctions implements Functions {

    Map<States, Consumer<Tape>> map;


    public TestFunctions() {
        map = new HashMap<>();
        map.put(States.A, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.positionValues.remove(tape.cursorPosition);
                tape.cursorPosition -= 1;
                tape.currentState = States.B;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition += 1;
                tape.currentState = States.B;
            }
        });

        map.put(States.B, tape -> {
            if (tape.positionValues.contains(tape.cursorPosition)) {
                tape.cursorPosition += 1;
                tape.currentState = States.A;
            } else {
                tape.positionValues.add(tape.cursorPosition);
                tape.cursorPosition -= 1;
                tape.currentState = States.A;
            }
        });
    }

    @Override
    public Map<States, Consumer<Tape>> getFunctions() {
        return map;
    }
}
