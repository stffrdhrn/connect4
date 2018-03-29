package org.shorne.connect4;

import java.io.Console;

public class GameMain {

    private static final int columns = 7;
    private static final int rows  = 6;
    private static final Player [] players = { Player.RED, Player.GREEN };

    private static final Console console = System.console();

    private static int readAndValidate () {
        try {
           int number = 0;
           String input = console.readLine();
           input = input.trim();

           if ("q".equals(input)) {
               System.exit (0);
           }
           /* Try to parse a number if we can */
           try {
               number = Integer.parseInt(input);
           } catch (Exception e) { /* ignore, detected below */ }

           if (number <= 0 || number > columns) {
               number = 0;
               System.err.printf ("No valid column in input: %s, try again.\n",
                                  input);
           }
           return number;
        } catch (Exception e) {
           throw new RuntimeException ("Stdin read failed ", e);
        }
    }

    public static void main (String [] args) {
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

            board.render();
            System.out.printf ("\nPlayer %d [%s] - choose column (1-%d): ",
                               playerIndex + 1,
                               player.name(),
                               columns);

            int column = readAndValidate ();
            if (column > 0) {
               board.dropDisc (player, column);
               turn++;
            }
        } while (board.getWinner() == Player.NONE);
    }
}
