package org.shorne.connect4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test limits of the board.
 * 
 * @author shorne
 *
 */
public class BoardTest {

    /**
     * Test for columns to become full.
     */
    @Test
    public void testDropDiscFill () {
        Board board = new Board(7, 6);
        Assert.assertTrue(board.dropDisc(Player.GREEN, 1));
        Assert.assertTrue(board.dropDisc(Player.GREEN, 1));
        Assert.assertTrue(board.dropDisc(Player.GREEN, 1));
        Assert.assertTrue(board.dropDisc(Player.GREEN, 1));
        Assert.assertTrue(board.dropDisc(Player.GREEN, 1));
        Assert.assertTrue(board.dropDisc(Player.GREEN, 1));

        Assert.assertFalse(board.dropDisc(Player.RED, 1));
    }

    /* We dont validate the column, that is done on the UI side */
    @Test (expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsLow () {
        Board board = new Board(7, 6);
        Assert.assertTrue(board.dropDisc(Player.GREEN, 0));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsHigh () {
        Board board = new Board(7, 6);
        Assert.assertTrue(board.dropDisc(Player.GREEN, 10));
    }

    @Test
    public void testRunTestCases() {
        List<TestCase> testCases = readTestCases("testcases.txt");
         
        for (TestCase testCase : testCases) {

            /* Create a new board for each case */
            Board board = new Board(7, 6);
            int turn = 0;
            Player [] players = { Player.RED, Player.GREEN };
            for (int move : testCase.moves) {
                Assert.assertEquals("No winner unil we are done", Player.NONE, board.getWinner());
                board.dropDisc(players [turn++ % players.length], move);
            }
            System.out.println(board.render());
            
            Assert.assertEquals(testCase.winner, board.getWinner());
            Assert.assertEquals(testCase.expectedResult, board.render());
            if (testCase.winner == Player.NONE) {
                Assert.assertTrue("Ensure board is full if no winner", board.isFull());
            }
            
        }
    }
    
    protected class TestCase {
        List<Integer> moves;
        String expectedResult = "";
        Player winner;
    }

    /**
     * Read test cases from a text file. The format of the test case will be:
     * 
     * CASE=4,4,5,5,3,2,6
     * WINNER=RED
     * | | | | | | | |
     * | | | | | | | |
     * | | | | | | | |
     * | | | | | | | |
     * | | | |G|G| | |
     * | |G|R|R|R|R| |
     * 
     * Where CASE is the comma separated list of moves.
     * Where WINNER is the PLAYER that wins after the moves are complete
     * After CASE and WINNER every line starting with '|' is considers
     * part of the results.
     */
    protected List<TestCase> readTestCases (String file) {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        this.getClass().getResourceAsStream(file)));
        try {
            List<TestCase> testCases = new LinkedList<>();

            String line;
            TestCase testCase = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("CASE")) {
                    testCase = new TestCase();
                    String movesStr = line.split("=")[1];
                    String [] movesStrs = movesStr.split(",");
                    testCase.moves = Arrays.asList(movesStrs)
                            .stream()
                            .map(n -> Integer.parseInt(n))
                            .collect(Collectors.toList());
                    testCases.add(testCase);
                } else if (line.startsWith("WINNER")) {
                    String winnerStr = line.split("=")[1];
                    testCase.winner = Player.valueOf(winnerStr);
                } else if (testCase != null && line.startsWith("|")) {
                    testCase.expectedResult += line + System.lineSeparator();
                } else {
                    testCase = null;
                }
            }

            return testCases;
        } catch (Exception e) {
            /* OK, If we fail, tests will fail */
            throw new RuntimeException("Failed to read file: " + file, e);
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                throw new RuntimeException("Failed to close br?", e);
            }
        }
    }
}
