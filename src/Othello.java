import java.util.Scanner;

public class Othello {

    public static char Black_Chip = 'B';
    public static char White_Chip = 'W';
    public static char Free_Space = '-';
    public static int Board_Size = 8;

    private static char[][] gridout = new char[Board_Size][Board_Size];//physical

    private static char[][] grid = new char[Board_Size][Board_Size];//lcgical view


    public static void Startup() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = Free_Space;
            }
        }
        grid[3][3] = White_Chip;
        grid[3][4] = Black_Chip;
        grid[4][3] = Black_Chip;
        grid[4][4] = White_Chip;

        System.out.println("constructor");
        overMethod();
    }


    public static void main(String[] args) {
        System.out.println("The game is starts");
        char move = Black_Chip;

        Startup();

        while (!gameOver()) {
            Display();//display Borard

            UserTurn(move);

            if (move == Black_Chip) {
                move = White_Chip;
            } else {
                move = Black_Chip;
            }

        }//while loop before full borard

        EndGameResults();

    }

    private static void overMethod() {
        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[i].length; j++) {
                gridout[i][j] = grid[i][j];
            }

        }

    }

    private static void UserTurn(char move) {
        Scanner sc = new Scanner(System.in);

        if (move == Black_Chip) {
            System.out.println("It's black player's turn.");

        } else {
            System.out.println("It's white player's turn.");

        }
        System.out.print("Input the position of piece: ");
        char[] input = sc.nextLine().toLowerCase().toCharArray();
        System.out.println();

        if (InputInvalid(input)) {
            System.out.println("wrong Fromat Please Enter again. eg. a2,e5..");
            UserTurn(move);
        } else {
            int column = ColumnWord(input[0]);
            int row = Integer.parseInt(String.valueOf(input[1]));

//            System.out.println(input[0] + " " + input[1]);
//            System.out.println(row + " " + column);

            if (InputIllegalMove(row - 1, column - 1)) {
                takeTurn(move, row - 1, column - 1);
            } else {
                System.out.println("This position is illegal.");
                UserTurn(move);

            }


        }


    }

    private static boolean InputIllegalMove(int row, int column) {
        System.out.println(row + "" + column);
        if (grid[row][column] == Free_Space) {
            return true;
        } else return false;
    }

    private static int ColumnWord(char c) {
        int a = 0;
        switch (c) {
            case 'a':
                a = 1;
                break;
            case 'b':
                a = 2;
                break;
            case 'c':
                a = 3;
                break;
            case 'd':
                a = 4;
                break;
            case 'e':
                a = 5;
                break;
            case 'f':
                a = 6;
                break;
            case 'g':
                a = 7;
                break;
            case 'h':
                a = 8;
                break;
        }
        return a;
    }

    private static void takeTurn(char turn, int row, int col) {
        // ADD CODE HERE
        //Check Above
        grid[row][col] = turn;
        //check above & below
        direction(row, col, turn, 0, -1);
        direction(row, col, turn, 0, 1);
        //check right & left
        direction(row, col, turn, 1, 0);
        direction(row, col, turn, -1, 0);
        //check corners
        direction(row, col, turn, 1, 1);
        direction(row, col, turn, 1, -1);
        direction(row, col, turn, -1, 1);
        direction(row, col, turn, -1, -1);

        // This method must be called to refresh the board after the logic of the game has changed
//        System.out.println("Take turn");
        overMethod();

    }

    private static void direction(int row, int column, char colour, int colDir, int rowDir) {
        int currentRow = row + rowDir;
        int currentCol = column + colDir;
        if (currentRow == 8 || currentRow < 0 || currentCol == 8 || currentCol < 0) {
            return;
        }
        while (grid[currentRow][currentCol] == Black_Chip || grid[currentRow][currentCol] == White_Chip) {
            if (grid[currentRow][currentCol] == colour) {
                while (!(row == currentRow && column == currentCol)) {
                    grid[currentRow][currentCol] = colour;
                    currentRow = currentRow - rowDir;
                    currentCol = currentCol - colDir;
                }
                break;
            } else {
                currentRow = currentRow + rowDir;
                currentCol = currentCol + colDir;
            }
            if (currentRow < 0 || currentCol < 0 || currentRow == 8 || currentCol == 8) {
                break;
            }
        }
    }

    private static Boolean InputInvalid(char[] input) {


        if (input.length > 2 || input.length == 0 || !InputForamt(input) || input.length < 2)
            return true;
        else return false;
    }

    private static boolean InputForamt(char[] input) {
        char[] wordchar = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        char[] num = {'1', '2', '3', '4', '5', '6', '7', '8'};


        Boolean a = false;
        Boolean b = false;
        if (input.length == 2) {
            for (int i = 0; i < 8; i++) {
                if (input[0] == wordchar[i]) {
                    a = true;
                    break;
                } else a = false;
            }
            for (int j = 0; j < 8; j++) {
                if (input[1] == num[j]) {
                    b = true;
                    break;
                } else b = false;
            }
        }
        if (a && b) {
            return true;
        } else return false;

    }

    private static void EndGameResults() {
        System.out.println("Result");
        int countw = 0;
        int countb = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == Black_Chip) {
                    countb++;
                } else if (grid[i][j] == White_Chip) {
                    countw++;
                }
            }
        }
        System.out.println("Black:" + countb + "pieces");
        System.out.println("White:" + countw + "pieces");

        if (countb > countw) {
            System.out.println("Game over. The winner is black.");
        } else if (countw > countb) {
            System.out.println("Game over. The winner is white.");
        } else {
            System.out.println("Game over. It is a tie game.");
        }

    }

    private static boolean gameOver() {
        // ADD CODE HERE
        boolean full = false;
        int countTot = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (grid[i][j] == Black_Chip || grid[i][j] == White_Chip) {
                    countTot++;
                }
            }
        }
        if (countTot == 64) {
            full = true;
        }
        return full;
    }


    public static void Display() {
        Othello myObj = new Othello();

        upperCell();
        for (int i = 0; i < grid.length; i++) {

            System.out.print(i + 1 + "\t");
            for (int j = 0; j < gridout[i].length; j++) {
                System.out.print(gridout[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();//underlines
    }

    public static void upperCell() {
        char[] text = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        for (int i = 0; i < 8; i++) {
            System.out.print("\t" + text[i]);
        }
        System.out.println();
    }
}

