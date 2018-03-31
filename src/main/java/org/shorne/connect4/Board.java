package org.shorne.connect4;

/**
 * The playing board data structure, provides logic
 * for dropping discs and detecting winners.
 *
 * The playing board laid out as follows
 *
 * rows| | | | |    | |
 *    .
 *    2| | | | |    | |
 *    1| | | | |    | |
 *    0| | | | |    | |
 *      0 1 2 3 ... columns-1
 *
 * The constructor takes the columns and rows parameters
 * and initializes a blank state.  By default winners are
 * detected when a player has more than 3 in a row.
 */
public class Board {

    /* Parameters */
    private int columns;
    private int rows;
    private int connect = 4;

    /* Board state */
    private Player [][] board;
    private int [] columnHeights;
    private int occupied;

    /* Winner search globals */
    private Player connected = Player.NONE;
    private int connections = 0;

    public Board (int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.board = new Player [columns][rows];
        this.columnHeights = new int [columns];
    }

    /**
     * Drops a disk into a column.  This method expects
     * that you have already validated the column is within
     * range of the board.
     *
     * @player the player that is dropping the disc (i.e. RED/GREEN)
     * @column the column to drop (starting at 1)
     * @return <code>true</code> if drop successful, <code>false</code> if
     * the column is already full.
     */
    public boolean dropDisc (Player player, int column) {
        Player [] columnStack = board [column - 1];
        int height = this.columnHeights[column - 1];

        /* If we are full dont allow a put */
        if (columnHeights[column - 1] == (this.columns - 1)) {
            return false;
        }

        columnStack[height] = player;
        columnHeights[column - 1] = height + 1;
        occupied++;

        return true;
    }

    /**
     * Render the board contents into a string for displaying.
     *
     * @return the rendered ascii board
     */
    public String render () {
        StringBuilder boardRender = new StringBuilder();
        String [] columnValues = new String [this.columns];

        /* render bottom up */
        for (int row = (this.rows - 1); row >= 0 ; row--) {
            for (int column = 0; column < this.columns; column++) {
                Player slot = board[column][row];
                columnValues[column] = (slot == null) ? " "
                        : slot.name().substring(0, 1);
            }
            boardRender.append('|')
                       .append(String.join("|", columnValues))
                       .append('|')
                       .append('\n');
        }

        return boardRender.toString();
    }

    private void scanReset() {
        connected = Player.NONE;
        connections = 0;
    }
    private boolean scanInterate(Player slot) {
        if (slot != null) {
            if (slot == connected) {
                if (++connections >= this.connect) {
                    return true;
                }
            } else {
                connected = slot;
                connections = 1;
            }
        } else {
            connected = Player.NONE;
        }
        return false;
    }

    /**
     * Scans the board and returns the winner if there is one.  Returns
     * Player.NONE if none.
     *
     * This uses a simple brute force scanning algorithm. For a board it
     * will scan columns by rows 3 times.  First looking for column connect
     * winners.  Second looking for row connection winners.  Finally, it
     * scans again looks for diagonals in both directions.
     *
     * It could probably be optimized, but it not really needed.
     */
    public Player getWinner () {
        /* Scan rows */
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                Player slot = board[column][row];
                if (scanInterate (slot)) {
                    return slot;
                }
            }
            /* reset for next row */
            scanReset();
        }
        scanReset();

        /* Scan columns */
        for (int column = 0; column < this.columns; column++) {
            for (int row = 0; row < this.rows; row++) {
                Player slot = board[column][row];
                if (scanInterate (slot)) {
                    return slot;
                }
            }
            /* reset for next column */
            scanReset();
        }
        scanReset();

        /* Scan Diagonals */
        for (int column = 0; column < this.columns; column++) {
            for (int row = 0; row < this.rows; row++) {
                /* Diagonals up */
                for (int i = 0; i < this.connect; i++) {
                    /* If we hit the limit stop */
                    if (column+i == this.columns ||
                        row+i == this.rows) {
                        break;
                    }
                    Player slot = board[column+i][row+i];
                    if (scanInterate (slot)) {
                        return slot;
                    }
                }
                /* Reset for diagonals down */
                scanReset();

                /* Diagonals down */
                for (int i = 0; i < this.connect; i++) {
                    /* If we hit the limit stop */
                    if (column+i == this.columns ||
                        row-i < 0) {
                        break;
                    }
                    Player slot = board[column+i][row-i];
                    if (scanInterate (slot)) {
                        return slot;
                    }
                }
                /* Reset for diagonals up in next row */
                scanReset();
            }
            /* Reset for next column */
            scanReset();
        }
        /* Reset for next check */
        scanReset();

        return Player.NONE;
    }

    /**
     * Return <code>true</code> if the board is full.
     *
     * @return <code>true</code> if the board is full
     */
    public boolean isFull() {
        return this.occupied == (this.columns * this.rows);
    }
}
