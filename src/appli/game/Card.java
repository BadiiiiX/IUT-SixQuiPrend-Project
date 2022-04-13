package appli.game;

public class Card {

    private final int number;
    private final int heads;

    public Card(int number) {
        this.number = number;
        this.heads = this.calculateHeads();
    }

    /**
     * === GETTERS ===
     */


    public int getNumber() {
        return number;
    }

    public int getHeads() {
        return heads;
    }

    /**
     * === METHODS ===
     */


    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.number);
        if (this.getHeads() > 1) sb.append(" (").append(this.getHeads()).append(")");

        return sb.toString();
    }

    private int calculateHeads() {
        int heads = 0;
        int firstNumber = this.number % 10;
        boolean isSameNumber = this.number % 11 == 0;

        if (firstNumber == 0) heads += 3;
        if (firstNumber == 5) heads += 2;
        if (isSameNumber) heads += 5;

        return heads == 0 ? 1 : heads;
    }

}
