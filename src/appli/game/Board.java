package appli.game;

import java.util.ArrayList;
import java.util.Collections;

import static appli.utils.ArrayListManager.popElement;

public class Board {
    private final ArrayList<Card> drawPile;
    private final ArrayList<Player> players;
    private final ArrayList<ArrayList<Card>> cardsQueue;
    private static final int CARD_NUMBER = 104;

    public Board() {
        this.drawPile = new ArrayList<>();
        this.players = new ArrayList<>();
        this.cardsQueue = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.cardsQueue.add(new ArrayList<>());
        }
    }

    /**
     * === GETTERS ===
     */


    public ArrayList<Player> getPlayer() {
        return this.players;
    }

    public int countPlayers() {
        return this.getPlayer().size();
    }

    public ArrayList<ArrayList<Card>> getCardsQueue() {
        return this.cardsQueue;
    }

    public ArrayList<Card> getQueue(int columnNumber) {
        return this.cardsQueue.get(columnNumber);
    }

    public ArrayList<Card> getDrawPile() {
        return this.drawPile;
    }


    /**
     * === MODIFIERS (ADD/REMOVE) ===
     */

    public Card popDrawPile() {
        return popElement(this.drawPile);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void addCardToPlayer(Player player) {
        Card cardToTake = popDrawPile();
        this.players.get(this.players.indexOf(player)).addCard(cardToTake);
    }

    public void addCardToColumn(int columnNumber, Card cardToSet) {
        this.cardsQueue.get(columnNumber).add(cardToSet);
    }


    /**
     * === METHODS ===
     */

    public void generateCards() {
        for (int i = 1; i < CARD_NUMBER + 1; i++) {
            this.drawPile.add(new Card(i));
        }
        this.shuffleDrawPile();
    }

    public void shuffleDrawPile() {
        Collections.shuffle(this.drawPile);
    }

}
