import Day7.GraphBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day8 {

    public static final String INPUT_FILENAME = "Day8Input";
    public static final String PART1TEST_FILENAME = "Day8Part1Test";

    public static final String SPACE_DELIMITER = " ";
    public static final String ADD = "inc";

    public static final String LESS_THAN_EQUAL_TO = "<=";
    public static final String LESS_THAN_TO = "<";
    public static final String GREATER_THAN_EQUAL_TO = ">=";
    public static final String GREATER_THAN_TO = ">";
    public static final String EQUAL_TO = "==";
    public static final String NOT_EQUAL_TO = "!=";

    public static final int REGISTER_INDEX = 0;
    public static final int REGISTER_ACTION_INDEX = 1;
    public static final int REGISTER_ACTION_VALUE_INDEX = 2;
    public static final int EXPRESSION_KEY_INDEX = 4;
    public static final int EXPRESSION_INDEX = 5;
    public static final int EXPRESSION_COMPARISON_INDEX = 6;

    private static int highestValue = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException, URISyntaxException {
        highestValue = Integer.MIN_VALUE;
        System.out.println("PART1 answer for TEST input: " + part1(PART1TEST_FILENAME));
        System.out.println("PART2 highest value for TEST input: " + highestValue);
        highestValue = Integer.MIN_VALUE;
        System.out.println("PART1 answer for GIVEN input: " + part1(INPUT_FILENAME));
        System.out.println("PART2 highest value for GIVEN input: " + highestValue);
    }

    public static int part1(String fileName) throws IOException, URISyntaxException {
        Map<String, Integer> register = new HashMap<>();


        Path path = Paths.get(GraphBuilder.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        lines.forEach(s -> {
            String[] instructionArray = s.split(SPACE_DELIMITER);

            if (!register.containsKey(instructionArray[REGISTER_INDEX])) {
                register.put(instructionArray[REGISTER_INDEX], 0);
            }

            if (evaluateExpress(register, instructionArray[EXPRESSION_KEY_INDEX], instructionArray[EXPRESSION_INDEX], Integer.parseInt(instructionArray[EXPRESSION_COMPARISON_INDEX]))) {
                updateRegister(register, instructionArray[REGISTER_INDEX], instructionArray[REGISTER_ACTION_INDEX], Integer.parseInt(instructionArray[REGISTER_ACTION_VALUE_INDEX]));
            }
        });

        return findLargestRegisterValue(register);
    }

    private static int findLargestRegisterValue(Map<String, Integer> register) {
        return register.values().stream().max(Comparator.naturalOrder()).get();
    }

    private static void updateRegister(Map<String, Integer> register, String registerName, String action, int actionValue) {
        if (action.equals(ADD)) {
            int current = register.get(registerName);
            int result = current + actionValue;
            highestValue = Integer.max(result, highestValue);
            register.put(registerName, result);
        } else {
            int current = register.get(registerName);
            int result = current - actionValue;
            highestValue = Integer.max(result, highestValue);
            register.put(registerName, result);
        }
    }

    private static boolean evaluateExpress(Map<String, Integer> register, String expressionKey, String expression, int comparisonValue) {
        if (!register.containsKey(expressionKey)) {
            register.put(expressionKey, 0);
        }

        int expressionKeyValue = register.get(expressionKey);
        boolean result = false;
        switch (expression) {
            case LESS_THAN_EQUAL_TO:
                result = expressionKeyValue <= comparisonValue;
                break;
            case LESS_THAN_TO:
                result = expressionKeyValue < comparisonValue;
                break;
            case GREATER_THAN_EQUAL_TO:
                result = expressionKeyValue >= comparisonValue;
                break;
            case GREATER_THAN_TO:
                result = expressionKeyValue > comparisonValue;
                break;
            case EQUAL_TO:
                result = expressionKeyValue == comparisonValue;
                break;
            case NOT_EQUAL_TO:
                result = expressionKeyValue != comparisonValue;
                break;
        }

        return result;
    }


}
