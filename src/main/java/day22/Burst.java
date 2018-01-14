package day22;

import java.util.Set;

public class Burst {
    Direction direction;
    Set<String> gridMap;

    int x;
    int y;

    static final char INFECTED = '#';

    public Burst(Set<String> gridMap, int x, int y) {
        direction = Direction.UP;
        this.gridMap = gridMap;
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        switch (direction) {
            case UP:
                this.x -= 1;
                direction = Direction.LEFT;
                break;
            case RIGHT:
                this.y -= 1;
                direction = Direction.UP;
                break;
            case DOWN:
                this.x += 1;
                direction = Direction.RIGHT;
                break;
            case LEFT:
                this.y += 1;
                direction = Direction.DOWN;
                break;
        }
    }

    public void moveRight() {
        switch (direction) {
            case UP:
                this.x += 1;
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                this.y += 1;
                direction = Direction.DOWN;
                break;
            case DOWN:
                this.x -= 1;
                direction = Direction.LEFT;
                break;
            case LEFT:
                this.y -= 1;
                direction = Direction.UP;
                break;
        }
    }

    public boolean isInfected() {
        return gridMap.contains(x + "," + y);
    }

    public void clean() {
        String key = x + "," + y;
        if (gridMap.contains(key)) {
            gridMap.remove(key);
        }
    }

    public void infect() {
        String key = x + "," + y;
        gridMap.add(key);
    }
}
