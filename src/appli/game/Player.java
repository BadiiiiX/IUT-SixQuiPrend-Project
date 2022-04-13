package appli.game;

import java.util.ArrayList;


public class Player {

    private final String name;
    private int heads = 0;
    private final ArrayList<Card> deck;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
    }

    /**
     * === GETTERS ===
     */


    public String getName() {
        return name;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public int getHeads() {
        return heads;
    }

    public int getIndex(int cardNumber) {
        for (Card c : this.deck) {
            if (c.getNumber() == cardNumber) return this.deck.indexOf(c);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    /**
     * === MODIFIERS (ADD/REMOVE) ===
     */

    public void addCard(Card card) {
        this.deck.add(card);
    }

    public void addHeads(Card card) {
        this.heads += card.getHeads();
    }

    public Card popCard(int cardNumber) {
        Card c = new Card(cardNumber);
        this.deck.remove(this.getIndex(cardNumber));

        return c;
    }

    /**
     * === METHODS ===
     */

    public boolean cardExist(int cardNumber) {
        for (Card c : this.deck) {
            if (c.getNumber() == cardNumber) return true;
        }
        return false;
    }

    public String toString() {
        return this.getName();
    }

}
