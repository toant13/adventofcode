import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day4 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        //part 1
        System.out.println(validPassphrase());

        //part 2
        System.out.println(validPassphrasePart2());
    }


    public static int validPassphrase() throws URISyntaxException, IOException {
        int total = 0;


        Path path = Paths.get(Day4.class.getClassLoader().getResource("Day4").toURI());
        Stream<String> lines = Files.lines(path);

        String[] arr = lines.toArray(String[]::new);
        for (String s : arr) {
            String[] array = s.split(" ");
            long unique = Arrays.stream(array).distinct().count();

            if (unique == array.length) {
                total++;
            }
        }

        lines.close();
        return total;
    }


    public static int validPassphrasePart2() throws URISyntaxException, IOException {
        int total = 0;


        Path path = Paths.get(Day4.class.getClassLoader().getResource("Day4").toURI());
        Stream<String> lines = Files.lines(path);

        String[] arr = lines.toArray(String[]::new);
        for (String s : arr) {
            String[] array = s.split(" ");
            boolean ana = false;
            for (int i = 0; i < array.length - 1 && !ana; i++) {
                String current = array[i];
                for (int j = i + 1; j < array.length && !ana; j++) {
                    String comp = array[j];
                    if (isAnagram(current, comp)) {
                        ana = true;
                    }
                }
            }
            if (!ana) {
                total++;
            }
        }

        return total;
    }

    public static boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();

        char[] s1Array = s1.toCharArray();
        for (char c : s1Array) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        char[] s2Array = s2.toCharArray();
        for (char c : s2Array) {
            if (map.containsKey(c)) {
                int freq = map.get(c);
                if (freq == 1) {
                    map.remove(c);
                } else {
                    map.put(c, freq - 1);
                }
            } else {
                return false;
            }
        }

        return map.isEmpty();
    }
}
