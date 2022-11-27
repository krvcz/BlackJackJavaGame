

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Croupier extends Player {

    public Croupier() {
        this.cards = new ArrayList<>();
    }

    public void shuffleCards(Deck deck)
    {
        LinkedList<Card> cards = new LinkedList<>(deck.getCards());
        Collections.shuffle(cards);
        deck.setCards(cards);
    }

    public void autoDecision(Deck deck, int score) throws NoValidFigure, NoValidColor {
        if (score <= 17) takeCard(deck);
    }


}
