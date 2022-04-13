package appli.utils;

import appli.game.Card;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

    @Override
    public int compare(Card firstCard, Card secondCard) {
        return firstCard.getNumber() - secondCard.getNumber();
    }

}