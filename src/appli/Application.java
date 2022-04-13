package appli;

import appli.game.Game;

import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) {

        Game game = new Game();

        try {
            game.init();
        } catch (FileNotFoundException ignored) {
        }

        while (!game.isFinished()) {

            game.play();
            game.setCards();

        }

        game.endGame();

    }
}
