package day23;

import java.util.HashMap;
import java.util.Map;

public class Register {
    Map<Character, Long> registerMap;

    public Register() {
        this.registerMap = new HashMap<>();
    }

    public Register(Map<Character, Long> registerMap) {
        this.registerMap = registerMap;
    }

    public void set(String x, String y) {
        char key = x.charAt(0);

        if (Character.isAlphabetic(y.charAt(0))) {
            long value = registerMap.getOrDefault(y.charAt(0), 0L);
            registerMap.put(key, value);
        } else {
            registerMap.put(key, Long.parseLong(y));
        }
    }

    public void subtract(String x, String y) {
        char key = x.charAt(0);

        if (Character.isAlphabetic(y.charAt(0))) {
            long value = registerMap.getOrDefault(y.charAt(0), 0L);
            long original = registerMap.getOrDefault(key, 0L);
            registerMap.put(key, original - value);
        } else {
            registerMap.put(key, registerMap.getOrDefault(key, 0L) - Long.parseLong(y));
        }
    }

    public void multiply(String x, String y) {
        char key = x.charAt(0);

        if (Character.isAlphabetic(y.charAt(0))) {
            long value = registerMap.getOrDefault(y.charAt(0), 0L);
            long original = registerMap.getOrDefault(key, 0L);
            registerMap.put(key, original * value);
        } else {
            registerMap.put(key, registerMap.getOrDefault(key, 0L) * Long.parseLong(y));
        }
    }

    public int jump(String x, String y, int currentInstruction) {
        if (Character.isAlphabetic(x.charAt(0))) {
            char key = x.charAt(0);
            long valueOfX = registerMap.getOrDefault(key, 0L);
            if (valueOfX != 0) {
                return currentInstruction + (int) (Long.parseLong(y)) - 1;
            }
        } else {
            long valueOfX = Long.parseLong(x);
            if (valueOfX != 0) {
                return currentInstruction + (int) (Long.parseLong(y)) - 1;
            }
        }
        return currentInstruction;
    }
}
