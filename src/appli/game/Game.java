package appli.game;

import appli.utils.ArrayListManager;
import appli.utils.CardComparator;
import appli.utils.Console;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static appli.utils.ArrayListManager.*;
import static java.lang.System.in;

public class Game {

    Board board;

    private final LinkedHashMap<Player, Card> cardSaver;
    private final LinkedHashMap<Player, Integer> cardSetter;

    private boolean haveProblem = false;

    public Game() {
        this.board = new Board();

        this.cardSaver = new LinkedHashMap<>();
        this.cardSetter = new LinkedHashMap<>();
    }

    /**
     * === GLOBAL METHODS ===
     */


    public void init() throws FileNotFoundException {

        this.board.generateCards();

        this.createPlayers();

        this.serveCards();

        this.initColumns();

        this.printWelcomeMessage();
    }

    public void play() {
        for (Player player : this.board.getPlayer()) {
            this.callPlayer(player);

            Console.pause();

            showSeries();

            showDeck(player);

            int card = readCard(player);

            takeCardToSave(player, card);

            Console.clearScreen();
        }
    }

    public void setCards() {

        Card[] cardSorted = new Card[this.board.getPlayer().size()];

        this.sortCardSaver(cardSorted);
        this.placeAllCards(cardSorted);
        this.announceVictims();
        this.cardSetter.clear();
    }

    public boolean isFinished() {
        for (Player player : this.board.getPlayer()) {
            if (player.getDeck().isEmpty()) return true;
        }

        return false;
    }

    public void endGame() {
        System.out.println("** Score final");
        this.printScores();
    }

    /**
     * === INIT METHODS ===
     */


    private void createPlayers() throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("src/appli/utils/config.txt"));

        while (in.hasNextLine()) {
            String value = in.nextLine();
            this.board.addPlayer(new Player(value));
        }

        in.close();
    }

    private void serveCards() {
        for (int i = 0; i < 10; i++) {
            for (Player player : this.board.getPlayer()) {
                this.board.addCardToPlayer(player);
            }
        }

        for (Player player : this.board.getPlayer()) {
            player.getDeck().sort(new CardComparator());
        }
    }

    private void initColumns() {
        Card[] cardToSet = new Card[4];
        for (int i = 0; i < 4; i++) {
            cardToSet[i] = this.board.popDrawPile();
        }

        Arrays.sort(cardToSet, new CardComparator());

        for (int i = 0; i < 4; i++) {
            this.board.addCardToColumn(i, cardToSet[i]);
        }
    }

    private void printWelcomeMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Les ")
                .append(board.countPlayers())
                .append(" joueurs sont ")
                .append(ArrayListManager.printDeck(board.getPlayer(), " et "))
                .append(". Merci de jouer à 6 qui prend !");

        System.out.println(sb);
    }

    /**
     * === PLAY METHODS ===
     */


    public void addCardSaver(Player p, Card c) {
        this.cardSaver.put(p, c);
    }

    private void callPlayer(Player p) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("A ")
                .append(p)
                .append(" de jouer.");

        System.out.println(sb);
    }

    private void showSeries() {
        for (int i = 0; i < 4; i++) showSeries(i);
    }

    private void showSeries(int seriesNumber) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("- série n° ")
                .append(seriesNumber + 1)
                .append(" : ")
                .append(printDeck(board.getQueue(seriesNumber)));

        System.out.println(sb);
    }

    private void showDeck(Player p) {
        StringBuilder sb = new StringBuilder()
                .append("- Vos cartes : ")
                .append(printDeck(p.getDeck()));

        System.out.println(sb);
    }

    private int readCard(Player p) {
        Scanner sc = new Scanner(in);
        int choiceCard = -1;
        boolean valueOk = false;
        boolean res = false;
        System.out.print("Saisissez votre choix : ");

        while (!valueOk) {
            if ((res = sc.hasNextInt()) && p.cardExist(choiceCard = sc.nextInt())) {
                valueOk = true;
            } else {
                System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
                if(!res) sc.next();
            }
        }

        return choiceCard;
    }

    private void takeCardToSave(Player player, int number) {
        Card cardFromPlayer = player.popCard(number);
        this.addCardSaver(player, cardFromPlayer);
    }

    /**
     * === SETCARDS METHODS ===
     */


    private void sortCardSaver(Card[] cl) {
        LinkedHashMap<Player, Card> rescueHm = (LinkedHashMap<Player, Card>) this.cardSaver.clone();

        for (int i = 0; i < this.board.getPlayer().size(); i++) {
            cl[i] = Collections.min(rescueHm.values(), new CardComparator());
            rescueHm.remove(getKeyByValue(rescueHm, cl[i]));
        }
    }

    private void placeAllCards(Card[] cl) {

        for (Card c : cl) {
            placeCard(c);
        }

        this.showPlaceMessage();
        this.showSeries();

        this.haveProblem = false;
        this.cardSaver.clear();
    }

    private void placeCard(Card c) {
        int choosedSeries;

        if (this.isTooLittle(c)) {
            StringBuilder sb = new StringBuilder();
            Player p = ArrayListManager.getKeyByValue(this.cardSaver, c);

            if (p != null)
                sb.append("Pour poser la carte ").append(c.getNumber()).append(", ").append(p.getName()).append(" doit choisir la série qu'il va ramasser.");

            this.showPlaceMessage(true);
            System.out.println(sb);
            this.showSeries();

            choosedSeries = this.requestSeries();
            resetSeries(choosedSeries, c);
        } else choosedSeries = this.selectSeries(c);

        if (this.needToResetSeries(choosedSeries)) resetSeries(choosedSeries, c);

        this.board.addCardToColumn(choosedSeries, c);

    }

    private boolean isTooLittle(Card cl) {

        for (ArrayList<Card> qCard : this.board.getCardsQueue()) {
            if (ArrayListManager.getLastElement(qCard).getNumber() < cl.getNumber()) return false;
        }
        return true;
    }

    private int selectSeries(Card c) {

        int seriesToReturn = -1;

        for (int i = 0; i < this.board.getCardsQueue().size(); i++) {
            int testValue = ArrayListManager.getLastElement(this.board.getQueue(i)).getNumber();

            if (c.getNumber() > testValue) seriesToReturn = i;
        }

        if (seriesToReturn == -1) seriesToReturn = this.board.getCardsQueue().size() - 1;

        return seriesToReturn;
    }

    private int requestSeries() {
        Scanner sc = new Scanner(in);
        int choiceCard = -1;
        boolean valueOk = false;
        boolean res = false;
        System.out.print("Saisissez votre choix : ");
        while (!valueOk) {
            if ((res= sc.hasNextInt()) && 5 > (choiceCard = sc.nextInt()) && choiceCard >= 1) {
                valueOk = true;
            } else {
                System.out.print("Ce n'est pas une série valide, saisissez votre choix : ");
                if(!res) sc.next();
            }
        }

        return choiceCard - 1;
    }

    private boolean needToResetSeries(int series) {
        return this.board.getCardsQueue().get(series).size() + 1 == 6;
    }

    private void resetSeries(int series, Card c) {
        int acc = 0;
        Player p = getKeyByValue(this.cardSaver, c);

        for (Card cardReceived : this.board.getCardsQueue().get(series)) {
            acc += cardReceived.getHeads();
            if (p != null) p.addHeads(cardReceived);
        }

        this.cardSetter.put(p, acc);

        this.board.getCardsQueue().get(series).clear();
    }

    private void showPlaceMessage(boolean inFuture) {
        if (inFuture && this.haveProblem) return;

        Card[] cardSorted = new Card[this.board.getPlayer().size()];
        String s = inFuture ? "vont être" : "ont été";
        StringBuilder sb = new StringBuilder();

        this.sortCardSaver(cardSorted);

        sb.append("Les cartes ");
        for (int i = 0; i < cardSorted.length; i++) {
            Card c = cardSorted[i];
            Player p = ArrayListManager.getKeyByValue(this.cardSaver, c);
            if (p != null) sb.append(c.getNumber()).append(" (").append(p.getName()).append(")");
            if (i == cardSorted.length - 2) sb.append(" et ");
            else if (i != cardSorted.length - 1) sb.append(", ");
        }

        sb.append(" ").append(s).append(" posées.");

        System.out.println(sb);
    }

    private void showPlaceMessage() {
        this.showPlaceMessage(false);
    }

    private void announceVictims() {
        if (this.cardSetter.isEmpty()) {
            System.out.println("Aucun joueur ne ramasse de tête de boeufs.");
            return;
        }

        for (Map.Entry<Player, Integer> entry : this.cardSetter.entrySet()) {
            Player p = entry.getKey();
            Integer i = entry.getValue();
            StringBuilder sb = new StringBuilder();

            sb.append(p.getName()).append(" a ramassé ").append(i).append(" têtes de boeufs");
            System.out.println(sb);
        }
    }

    /**
     * === ENDGAME METHODS ===
     */


    private void printScores() {
        for (Player p : this.board.getPlayer()) {
            this.printScore(p);
        }
    }

    private void printScore(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append(p.getName()).append(" a ramassé ").append(p.getHeads()).append(" tête").append((p.getHeads() > 1) ? 's' : "").append(" de boeufs");

        System.out.println(sb);
    }

}
