package org.shorne.connect4;

import java.io.Console;

/**
 * Main program for the connect4 game, loads the board and
 * players then starts the main user input loop.
 * 
 * @author shorne
 *
 */
public class GameMain {

    private static final Console console = System.console();

    private int columns = 7;
    private int rows  = 6;
    private Player [] players = { Player.RED, Player.GREEN };

    private int readAndValidate () {
        try {
            String input = console.readLine();
            return validate(input);
        } catch (Exception e) {
            /* Rethrow the error as runnable since we can't handle it anyway
             * and need to fail fast.
             */
            throw new RuntimeException ("Stdin read failed ", e);
        }
    }

    protected int validate(String input) {
        int number = 0;
        input = input.trim();

        /* Try to parse a number if we can */
        try {
            number = Integer.parseInt(input);
        } catch (Exception e) { /* ignore, detected below */ }

        if (number <= 0 || number > columns) {
            number = 0;
            System.err.printf ("Invalid column in input: %s, try again.\n",
                    input);
        }
        return number;
    }

    /**
     * Start the game, asking for user input and displaying the
     * results of each turn.  This game will exit when the game
     * is finished due to a win or a draw.
     */
    public void start() {
        Board board = new Board (columns, rows);
        int turn = 0;

        if (console == null) {
            System.err.println ("No console detected, are you running one?");
            System.exit (1);
        }

        do {
            /* Get the player for this turn */
            int playerIndex = turn % players.length;
            Player player = players[playerIndex];

            System.out.print(board.render());
            System.out.printf("\nPlayer %d [%s] - choose column (1-%d): ",
                    playerIndex + 1,
                    player.name(),
                    columns);

            /* Read and drop a disc, if it succeeds move to next turn */
            int column = readAndValidate ();
            if (column > 0) {
                if (board.dropDisc (player, column)) {
                   turn++;
                } else {
                    System.err.printf ("Column is full, try again.\n");
                }
            }
        } while (board.getWinner() == Player.NONE
                && !board.isFull());

        System.out.print(board.render());
        if (board.isFull()) {
            System.out.println ("\nIts a draw, try again next time.");
        } else {
            System.out.printf("\nPlayer %d [%s] wins!\n",
                    ((turn - 1) % players.length) + 1, 
                    board.getWinner());
        }
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /* The main entry point */
    public static void main (String [] args) {
        GameMain gameMain = new GameMain();
        /* Run game with defaults. */
        gameMain.start();
    }

}
