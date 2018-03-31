package org.shorne.connect4;

import org.junit.Test;

import org.junit.Assert;

/**
 * Test validation code using in the game user interface.
 * 
 * @author shorne
 *
 */
public class GameMainTest {

    @Test
    public void testInputValidation() {
        GameMain game = new GameMain();
        Assert.assertEquals(0, game.validate(" 55 "));
        Assert.assertEquals(0, game.validate("-1"));
        Assert.assertEquals(0, game.validate("bad"));
        Assert.assertEquals(0, game.validate("^  3s bad^"));
        Assert.assertEquals(5, game.validate(" 5 "));
        Assert.assertEquals(3, game.validate("   3 "));
        Assert.assertEquals(1, game.validate("1"));
        Assert.assertEquals(2, game.validate("2"));
        
    }
}
