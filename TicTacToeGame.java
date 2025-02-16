import Model.Board;
import Model.Pair; 
import Model.PieceType;
import Model.Player;
import Model.PlayingPieceO;
import Model.PlayingPieceX;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    Deque<Player> players;
    Board gameBoard;
    Scanner inputScanner;

    public TicTacToeGame() {
        initializeGame();
    }

    public void initializeGame() {
        players = new LinkedList<>();
        PlayingPieceX crossPiece = new PlayingPieceX();
        Player player1 = new Player("Player 1", crossPiece);
        PlayingPieceO noughtsPiece = new PlayingPieceO();
        Player player2 = new Player("Player 2", noughtsPiece);

        players.add(player1);
        players.add(player2);

        gameBoard = new Board(3);
        inputScanner = new Scanner(System.in);
    }

    public String startGame() {
        boolean noWinner = true;
        while (noWinner) {
            Player playerTurn = players.removeFirst();
            gameBoard.printBoard();

            List<Pair<Integer, Integer>> freeSpaces = gameBoard.getFreeCells(); 
            if (freeSpaces.isEmpty()) {
                noWinner = false;
                break;
            }

            System.out.print("Player: " + playerTurn.name + ", enter row and column: ");
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.parseInt(values[0]);
            int inputCol = Integer.parseInt(values[1]);

            boolean pieceAddedSuccessfully = gameBoard.addPiece(playerTurn.playingPiece, inputRow, inputCol);
            if (!pieceAddedSuccessfully) {
                System.out.println("Invalid move, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputCol, inputRow, playerTurn.playingPiece.pieceType);
            if (winner) {
                inputScanner.close();
                return playerTurn.name;
            }
        }

        inputScanner.close();
        return "No winner, the game is a draw";
    }

    public boolean isThereWinner(int col, int row, PieceType piece) {
        boolean rowMatch = true;
        boolean colMatch = true;
        boolean diagonalMatch = true;
        boolean reverseDiagonalMatch = true;

        for (int i = 0; i < gameBoard.size; i++) {
            if (gameBoard.board[row][i] == null || gameBoard.board[row][i].pieceType != piece)
                rowMatch = false;

            if (gameBoard.board[i][col] == null || gameBoard.board[i][col].pieceType != piece)
                colMatch = false;

            if (gameBoard.board[i][i] == null || gameBoard.board[i][i].pieceType != piece)
                diagonalMatch = false;

            if (gameBoard.board[i][gameBoard.size - i - 1] == null || gameBoard.board[i][gameBoard.size - i - 1].pieceType != piece)
                reverseDiagonalMatch = false;
        }

        return rowMatch || colMatch || diagonalMatch || reverseDiagonalMatch;
    }
}
