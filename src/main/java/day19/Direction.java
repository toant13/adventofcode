package day19;

import java.util.function.Function;

public enum Direction {
    UP(x -> x, y -> y - 1),
    RIGHT(x -> x + 1, y -> y),
    DOWN(x -> x, y -> y + 1),
    LEFT(x -> x - 1, y -> y),;


    Function<Integer, Integer> xDirection;
    Function<Integer, Integer> yDirection;

    Direction(Function<Integer, Integer> xDirection, Function<Integer, Integer> yDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

    public int getNextX(int x) {
        return this.xDirection.apply(x);
    }

    public int getNextY(int y) {
        return this.yDirection.apply(y);
    }
}
