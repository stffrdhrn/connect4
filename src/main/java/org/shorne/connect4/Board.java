package org.shorne.connect4;

public class Board {

    private int columns;
    private int rows;
    private Player [][] board;

    public Board (int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.board = new Player [rows][columns];
    }

    public boolean dropDisc (Player player, int col) {
        return true;
    }

    public void render () {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                System.out.print( " | " );
            }
            System.out.println();
        }
    }

    public Player getWinner () {
        return Player.NONE;
    }
}
