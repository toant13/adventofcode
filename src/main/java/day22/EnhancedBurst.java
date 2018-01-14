package day22;

import java.util.Map;

public class EnhancedBurst {
    Direction direction;
    Map<String, Character> gridMap;

    int x;
    int y;

    static final char CLEANED = '.';
    static final char INFECTED = '#';
    static final char WEAKENED = 'W';
    static final char FLAGGED = 'F';

    public EnhancedBurst(Map<String, Character> gridMap, int x, int y) {
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

    public void moveForward() {
        switch (direction) {
            case UP:
                this.y -= 1;
                break;
            case RIGHT:
                this.x += 1;
                break;
            case DOWN:
                this.y += 1;
                break;
            case LEFT:
                this.x -= 1;
                break;
        }
    }

    public void moveReverse() {
        switch (direction) {
            case UP:
                this.y += 1;
                direction = Direction.DOWN;
                break;
            case RIGHT:
                this.x -= 1;
                direction = Direction.LEFT;
                break;
            case DOWN:
                this.y -= 1;
                direction = Direction.UP;
                break;
            case LEFT:
                this.x += 1;
                direction = Direction.RIGHT;
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

    private boolean checkState(char stateToCheck) {
        String key = x + "," + y;
        if (!gridMap.containsKey(key)) {
            return false;
        }

        char state = gridMap.get(key);
        if (state != stateToCheck) {
            return false;
        }

        return true;
    }

    public boolean isInfected() {
        return checkState(INFECTED);
    }

    public boolean isWeakened() {
        return checkState(WEAKENED);
    }

    public boolean isClean() {
        String key = x + "," + y;
        return !gridMap.containsKey(key);
    }

    public boolean isFlagged() {
        return checkState(FLAGGED);
    }

    public void clean() {
        String key = x + "," + y;
        if (gridMap.containsKey(key)) {
            gridMap.remove(key);
        }
    }

    public void weaken() {
        String key = x + "," + y;
        gridMap.put(key, WEAKENED);
    }

    public void flag() {
        String key = x + "," + y;
        if (gridMap.containsKey(key)) {
            gridMap.put(key, FLAGGED);
        }
    }

    public void infect() {
        String key = x + "," + y;
        if (gridMap.containsKey(key)) {
            gridMap.put(key, INFECTED);
        }
    }


}
