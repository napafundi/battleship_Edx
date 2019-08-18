import java.util.Arrays;
import java.util.Scanner;

public class battleship {
    public static Scanner scan = new Scanner(System.in);
    public static int[][] playerCoords = new int[5][2];
    public static int[][] compCoords = new int[5][2];

    /**
     * Creates a 10x10 2D array filled with whitespaces for the game map.
     */
    public static String[][] createMap() {

        String[][] map = new String[10][10];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = " ";
            }
        }
        return map;
    }

    /**
     * Displays the current map, surrounded by the map-frame.
     */
    public static void displayMap(String[][] map) {

        System.out.println("   0123456789   ");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.print("| " + i);
            System.out.print("\n");
        }
        System.out.println("   0123456789   ");
    }

    public static void intro(String[][] map) {
        System.out.println("**** Welcome to Battleship! ****");
        System.out.println("Right now, the sea is empty. \n");
        displayMap(map);
    }

    public static boolean checkSpot(int x, int y, int[][] array) {
        if (x < 0 || x > 9 || y < 0 || y > 9) {
            return false;
        }
        int[] testArray = {y,x};
        for (int i = 0; i < array.length; i++) {
            if (Arrays.equals(array[i], testArray)) {
                return false;
            }
        }
        return true;
    }

    public static void placeShips(int[][] playerCoords, int[][] compCoords, String[][] map) {
        System.out.println("Deploy your ships:");
        for (int i = 0; i < playerCoords.length; i++) {
            System.out.print("Enter X coordinate for your ship #" + i + " (0-9): ");
            int x = scan.nextInt();
            System.out.print("Enter Y coordinate for your ship #" + i + " (0-9): ");
            int y = scan.nextInt();
            while (!checkSpot(x, y, playerCoords)) {
                System.out.println("You cannot place a ship there, please enter coordinates within the map that have not been taken");
                System.out.print("Enter X coordinate for your ship (0-9): ");
                x = scan.nextInt();
                System.out.print("Enter Y coordinate for your ship (0-9): ");
                y = scan.nextInt();
            }
            int[] newSpot = {y,x};
            playerCoords[i] = newSpot;
            map[y][x] = "@";
            displayMap(map);
        }
    }

    public static void main(String[] args) {
        String[][] map = createMap();
        intro(map);
        placeShips(playerCoords, compCoords, map);
    }
}
