package day25;

import java.util.Map;
import java.util.function.Consumer;

public interface Functions {

    Map<States, Consumer<Tape>> getFunctions();

}
