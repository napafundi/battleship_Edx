import java.util.*;

public class battleship {
    public static Scanner scan = new Scanner(System.in);
    public static int[][] playerCoords = new int[5][2];
    public static int[][] compCoords = new int[5][2];
    public static ArrayList<int[]> playerGuesses = new ArrayList<int[]>();
    public static ArrayList<int[]> compGuesses = new ArrayList<int[]>();
    public static int[][] gameOverTest = new int [5][2];

    /**
     * Creates a 10x10 2D array filled with whitespaces for the game map.
     * Fill gameOverTest with [10,10] arrays for game over testing
     * Fill playerCoords and compCoords with [10,10] arrays to allow for [0,0] selection
     */
    public static String[][] createMap() {
        for (int[] row: gameOverTest) {
            Arrays.fill(row, 10);
        }
        for (int[] row: playerCoords) {
            Arrays.fill(row, 10);
        }
        for (int[] row: compCoords) {
            Arrays.fill(row, 10);
        }
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

    /**
     * Introduction for the game, displays the array map.
     * @param map array to be displayed in console
     */
    public static void intro(String[][] map) {
        System.out.println("**** Welcome to Battleship! ****");
        System.out.println("Right now, the sea is empty. \n");
        displayMap(map);
    }

    /**
     * Checks if the the given [x,y] coordinates can be placed in the given array
     * @param x x-coordinate
     * @param y y-coordinate
     * @param array tested array
     * @return false if [x,y] already exists in the coordinate array, else true
     */
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

    /**
     * Checks the array to see if a guess exists in the opponents coordinates, removes ship from array if hit (changes
     * coordinates to {0,0})
     * @param guess to be tested by function
     * @param array opponent's array of placed ships
     * @return true if guess is a hit, else false
     */
    public static boolean checkSpot(int[] guess, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            if (Arrays.equals(array[i], guess)) {
                int[] removeShip = {10,10};
                array[i] = removeShip;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a guess for this coordinate pair has already been made
     * @param guess to be checked by function
     * @param guesses array of guesses already made
     * @return true if guess exists in guesses, else false so a guess here can be made
     */
    public static boolean checkGuess(int[] guess, ArrayList<int[]> guesses) {
        if (guesses.size() == 0) {
            return false;
        }
        for (int i = 0; i < guesses.size(); i++) {
            int[] testArray = guesses.get(i);
            if (Arrays.equals(testArray, guess)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the opponents coordinate array is equal to the gameOverTest array filled with zeroes
     * @param oppArray
     * @return
     */
    public static boolean gameOver(int[][] oppArray) {
        if (Arrays.deepEquals(oppArray, gameOverTest)) {
            return true;
        }
        return false;
    }

    public static void placeShips(int[][] playerCoords, int[][] compCoords, String[][] map) {
        // Place player ships
        int x,y;
        System.out.println("Deploy your ships:");
        for (int i = 0; i < playerCoords.length; i++) {
            int[] newSpot = new int[2];
            System.out.print("Enter X coordinate for your ship #" + (i+1) + " (0-9): ");
            x = scan.nextInt();
            System.out.print("Enter Y coordinate for your ship #" + (i+1) + " (0-9): ");
            y = scan.nextInt();
            while (!checkSpot(x, y, playerCoords)) {
                System.out.println("You cannot place a ship there, please enter coordinates within the map that have not been taken.");
                System.out.print("Enter X coordinate for your ship #" + (i+1) + " (0-9): ");
                x = scan.nextInt();
                System.out.print("Enter Y coordinate for your ship #" + (i+1) + " (0-9): ");
                y = scan.nextInt();
            }
            newSpot[0] = y;
            newSpot[1] = x;
            playerCoords[i] = newSpot;
            map[y][x] = "@";
            displayMap(map);
        }
        // Place computer ships
        System.out.println("Computer is placing ships...");
        for (int j = 0; j < compCoords.length; j++) {
            int[] newSpot = new int[2];
            x = (int)(Math.random() * 9);
            y = (int)(Math.random() * 9);
            while (!checkSpot(x, y, playerCoords) && !checkSpot(x, y, compCoords)) {
                x = (int)(Math.random() * 9);
                y = (int)(Math.random() * 9);
            }
            newSpot[0] = y;
            newSpot[1] = x;
            compCoords[j] = newSpot;
            System.out.println("Ship #" + (j+1) + " deployed.");
        }
    }

    public static void playGame(int[][] playerCoords, ArrayList<int[]> playerGuesses, int[][] compCoords, ArrayList<int[]> compGuesses, String[][] map) {
        while(true) {
            // Player turn
            int x,y;
            int[] playerGuess = new int[2];
            System.out.print("Enter X coordinate for your attack: ");
            x = scan.nextInt();
            System.out.print("Enter Y coordinate for your attack: ");
            y = scan.nextInt();
            playerGuess[0] = y;
            playerGuess[1] = x;
            while (checkGuess(playerGuess, playerGuesses) || x < 0 || x > 9 || y < 0 || y > 9) {
                System.out.println("You've either already attacked this spot, or it's not within the map confines. Try again.");
                System.out.print("Enter X coordinate for your attack: ");
                x = scan.nextInt();
                System.out.print("Enter Y coordinate for your attack: ");
                y = scan.nextInt();
                playerGuess[0] = y;
                playerGuess[1] = x;
            }
            while (!checkSpot(x, y, playerCoords) || map[y][x].equals("X")) {
                System.out.println("You cannot attack here! It's your own ship!");
                System.out.print("Enter X coordinate for your attack: ");
                x = scan.nextInt();
                System.out.print("Enter Y coordinate for your attack: ");
                y = scan.nextInt();
                playerGuess[0] = y;
                playerGuess[1] = x;
            }
            playerGuesses.add(playerGuess);
            int k = y;
            int l = x;
            if (checkSpot(playerGuess, compCoords)) {
                System.out.println("You hit the computer's ship!");
                map[k][l] = "x";
                if (gameOver(compCoords)) {
                    System.out.println("Congratulations, you've won!");
                    break;
                }
            } else {
                System.out.println("You missed!");
                map[k][l] = "o";
            }
            displayMap(map);
            // Computer turn
            System.out.println("The computer is now attacking...");
            int[] compGuess = new int[2];
            x = (int)(Math.random() * 9);
            y = (int)(Math.random() * 9);
            compGuess[0] = y;
            compGuess[1] = x;
            while (checkGuess(compGuess, compGuesses) || !checkSpot(x, y, compCoords)) {
                x = (int)(Math.random() * 9);
                y = (int)(Math.random() * 9);
                compGuess[0] = y;
                compGuess[1] = x;
            }
            compGuesses.add(compGuess);
            if (checkSpot(compGuess, playerCoords)) {
                System.out.println("The computer hit your ship!");
                map[y][x] = "X";
                if (gameOver(playerCoords)) {
                    System.out.println("The computer won! Try again.");
                    break;
                }
            } else {
                System.out.println("The computer missed!");
            }
        }
    }

    public static void main(String[] args) {
        String[][] map = createMap();
        intro(map);
        placeShips(playerCoords, compCoords, map);
        playGame(playerCoords, playerGuesses, compCoords, compGuesses, map);
    }
}
