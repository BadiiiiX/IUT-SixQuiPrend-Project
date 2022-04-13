package appli.tests;

import static org.junit.Assert.*;

import appli.game.Card;
import appli.game.Player;
import org.junit.Test;


public class PlayerTest {

    private final Card randomCard = new Card(14);
    private final Card anotherRandomCard = new Card(77);
    private final Player randomPlayer = new Player("random");

    public PlayerTest() {
    }

    @Test
    public void CardDeckManagerTester() {

        this.randomPlayer.getDeck().add(randomCard);
        assertTrue("[PLAYER] cardExist don't work, 14 must be in the player deck.", this.randomPlayer.cardExist(14));

        this.randomPlayer.addCard(randomCard);
        assertTrue("[PLAYER] addCard don't work, 14 must be in the player deck.", this.randomPlayer.cardExist(14));
    }

    @Test
    public void infoPlayerTester() {
        assertEquals("[PLAYER] Player name is incorrect.", randomPlayer.getName(), "random");

        assertEquals("[PLAYER] Player toString need to return player name.", randomPlayer.toString(), "random");
    }

}
