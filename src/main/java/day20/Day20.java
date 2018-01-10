package day20;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 {

    private static final String INPUT = "Day20Input";
    private static final String TEST = "Day20Part1Test";
    private static final String TEST2 = "Day20Part2Test";
    private static final int LONG_TERM = 10000;

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("PART1 answer for TEST: " + findClosestParticle(TEST, Day20::getClosest));
        System.out.println("PART1 answer for INPUT: " + findClosestParticle(INPUT, Day20::getClosest));

        System.out.println("PART2 answer for TEST: " + findClosestParticle(TEST2, Day20::computeCollisions));
        System.out.println("PART2 answer for INPUT: " + findClosestParticle(INPUT, Day20::computeCollisions));
    }

    public static int findClosestParticle(String input, Function<Particle[], Integer> function) throws IOException, URISyntaxException {
        List<String> particleList = getParticleCoordinates(input);
        Particle[] particles = getParticleArray(particleList);

        int result = 0;
        for (int i = 0; i < LONG_TERM; i++) {
            Arrays.stream(particles).forEach(particle -> {
                if (particle != null) {
                    particle.move();
                }
            });
            result = function.apply(particles);
            System.out.println(result);
        }

        return result;
    }

    public static int computeCollisions(Particle[] particles) {
        int totalLeft = 0;

        for (int i = 0; i < particles.length - 1; i++) {
            if (particles[i] != null) {
                Particle currentParticle = particles[i];
                Set<Integer> particlesToRemove = new HashSet<>();
                for (int j = i + 1; j < particles.length; j++) {
                    if (particles[j] != null) {
                        if (currentParticle.samePosition(particles[j])) {
                            particlesToRemove.add(j);
                            particlesToRemove.add(i);
                        }
                    }
                }
                for (Integer particleId : particlesToRemove) {
                    particles[particleId] = null;
                }
                if (particles[i] != null) {
                    totalLeft++;
                }
            }
        }

        return totalLeft;
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
