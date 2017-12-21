package day3;

public class Day3 {


    public static final int INPUT = 368078;
    public static final int DEFAULT_SIZE = 600;

    public static void main(String[] args) {

        int[][] array = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        array[DEFAULT_SIZE / 2 - 1][DEFAULT_SIZE / 2 - 1] = 1;
        array[DEFAULT_SIZE / 2 - 1][DEFAULT_SIZE / 2] = 1;

        System.out.println(part2(array));
    }

    public static int part2(int[][] array) {
        int y = DEFAULT_SIZE / 2 - 1;
        int x = DEFAULT_SIZE / 2;

        while(array[y][x] <= INPUT) {
            // right
            while (array[y - 1][x] != 0) {
                x++;
                array[y][x] = getSum(array, y, x);
                if (array[y][x] > INPUT) {
                    return array[y][x];
                }
            }

            // up
            while (array[y][x - 1] != 0) {
                y--;
                array[y][x] = getSum(array, y, x);
                if (array[y][x] > INPUT) {
                    return array[y][x];
                }
            }

            // left
            while (array[y + 1][x] != 0) {
                x--;
                array[y][x] = getSum(array, y, x);
                if (array[y][x] > INPUT) {
                    return array[y][x];
                }
            }

            // down
            while (array[y][x + 1] != 0) {
                y++;
                array[y][x] = getSum(array, y, x);
                if (array[y][x] > INPUT) {
                    return array[y][x];
                }
            }
        }

        return 0;
    }

    public static int getSum(int[][] array, int y, int x) {
        return array[y + 1][x - 1] + array[y + 1][x] + array[y + 1][x + 1] + array[y][x - 1] + array[y][x + 1] + array[y - 1][x - 1] + array[y - 1][x] + array[y - 1][x + 1];
    }

}