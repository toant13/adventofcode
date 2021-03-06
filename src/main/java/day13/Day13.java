package day13;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {

    private static final String INPUT = "Day13Input";
    private static final String TEST = "Day13Part1Test";

    private static final int DEPTH_INDEX = 0;
    private static final int RANGE_INDEX = 1;

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + getSeverity(TEST));
        System.out.println("PART1 answer for INPUT: " + getSeverity(INPUT));

        System.out.println("PART2 answer for TEST: " + findSmallestDelay(TEST));

        long start = System.currentTimeMillis();
        System.out.println("PART2 answer for INPUT: " + findSmallestDelay(INPUT) + " total time to process: [" + (System.currentTimeMillis() - start) + "ms]");
    }


    public static int findSmallestDelay(String fileName) throws IOException, URISyntaxException {
        int delay = -1;
        boolean caught = false;

        Firewall currentFirewall = buildFirewall(fileName);
        while (!caught) {
            Firewall newFirewall = currentFirewall.clone();
            caught = wasCaught(newFirewall);
            delay++;
            currentFirewall.moveScanner();
        }

        return delay;
    }

    public static boolean wasCaught(Firewall firewall) {
        int numberOfLayers = firewall.getNumberLayers();

        while (firewall.rider < numberOfLayers - 1) {
            firewall.moveRider();
            if (firewall.isRiderCaught()) {
                return false;
            }
            firewall.moveScanner();
        }
        return true;
    }


    public static int getSeverity(String fileName) throws IOException, URISyntaxException {
        int totalSeverity = 0;
        Firewall firewall = buildFirewall(fileName);
        int numberOfLayers = firewall.getNumberLayers();

        while (firewall.rider < numberOfLayers - 1) {
            firewall.moveRider();
            if (firewall.isRiderCaught()) {
                totalSeverity += firewall.getCurrentSeverity();
            }
            firewall.moveScanner();
        }

        return totalSeverity;
    }

    private static Firewall buildFirewall(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(Day13.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        List<String> list = lines.collect(Collectors.toList());

        return buildFirewallFromList(list);
    }

    private static Firewall buildFirewallFromList(List<String> list) {
        String firewallSizeString = list.get(list.size() - 1).replaceAll(" ", "").split(":")[DEPTH_INDEX];
        int firewallSize = Integer.parseInt(firewallSizeString) + 1;
        Firewall firewall = new Firewall(firewallSize);
        for (String s : list) {
            String[] depthAndRange = s.replaceAll(" ", "").split(":");
            int depth = Integer.parseInt(depthAndRange[DEPTH_INDEX]);
            int range = Integer.parseInt(depthAndRange[RANGE_INDEX]);

            Layer layer = new Layer(range);
            firewall.addLayer(layer, depth);
        }

        return firewall;
    }
}
