package day23;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 {

    private static final String INPUT = "Day23Input";

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for INPUT: " + getMulCount(INPUT));
    }

    public static int getMulCount(String input) throws IOException, URISyntaxException {
        List<String> instructionsList = getInstructionsList(input);
        int count = 0;

        Register register = new Register();
        for (int i = 0; i < instructionsList.size(); i++) {
            String[] instructionArray = instructionsList.get(i).split(" ");
            String command = instructionArray[0];
            String x = instructionArray[1];
            String y = instructionArray[2];

            switch (command) {
                case "set":
                    register.set(x, y);
                    break;
                case "sub":
                    register.subtract(x, y);
                    break;
                case "mul":
                    register.multiply(x, y);
                    count++;
                    break;
                case "jnz":
                    i = register.jump(x, y, i);
                    break;
            }
        }
        return count;
    }


    private static List<String> getInstructionsList(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Day23.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        return lines.collect(Collectors.toList());
    }
}
