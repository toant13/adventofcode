package day20;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 {

    private static final String INPUT = "Day20Input";
    private static final String TEST = "Day20Part1Test";
    private static final int LONG_TERM = 1000;

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + findClosestParticle(TEST));
        System.out.println("PART1 answer for INPUT: " + findClosestParticle(INPUT));
    }

    public static int findClosestParticle(String input) throws IOException, URISyntaxException {
        List<String> particleList = getParticleCoordinates(input);


        Particle[] particles = getParticleArray(particleList);

        int particleId = 0;
        for (int i = 0; i < LONG_TERM; i++) {
            for (int j = 0; j < particles.length; j++) {
                particles[j].move();
            }
            particleId = getClosest(particles);
        }

        return particleId;
    }

    public static int getClosest(Particle[] particles) {
        int particleId = 0;
        for (int i = 1; i < particles.length; i++) {
            if (particles[i].getDistance() < particles[particleId].getDistance()) {
                particleId = i;
            }
        }
        return particleId;
    }

    private static Particle[] getParticleArray(List<String> particleList) {
        Particle[] particles = new Particle[particleList.size()];
        for (int i = 0; i < particleList.size(); i++) {
            particles[i] = new Particle(particleList.get(i));
        }

        return particles;
    }

    private static List<String> getParticleCoordinates(String fileName) throws IOException, URISyntaxException, URISyntaxException {
        Path path = Paths.get(Day20.class.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        return lines.collect(Collectors.toList());
    }
}
