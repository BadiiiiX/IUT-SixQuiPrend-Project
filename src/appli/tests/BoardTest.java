package appli.tests;

import static org.junit.Assert.*;

import appli.game.Board;
import org.junit.Test;

public class BoardTest {

    private final Board firstBoard = new Board();
    private final Board secondBoard = new Board();

    @Test
    public void randomBoard() {
        firstBoard.generateCards();
        secondBoard.generateCards();

        assertNotEquals("[BOARD] Board must to with random shuffle.", firstBoard.getDrawPile(), secondBoard.getDrawPile());
    }
}
