package appli.tests;

import static org.junit.Assert.*;

import appli.game.Card;
import org.junit.Test;

public class CardTest {

    @Test
    public void CalcHeads() {
        Card c1 = new Card(45);
        Card c2 = new Card(11);
        Card c3 = new Card(55);
        Card c4 = new Card(12);

        assertEquals("[CARD] If card number finish with 5, set heads to 2", c1.getHeads(), 2);
        assertEquals("[CARD] If card number finish with same number (11,22), set heads to 5", c2.getHeads(), 5);
        assertEquals("[CARD] If card number finish with same number (11,22) AND 5, set heads to 2 and 5", c3.getHeads(), 7);
        assertEquals("[CARD] ELSE, set heads to 1", c4.getHeads(), 1);
    }

}
