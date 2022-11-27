

import java.util.ArrayList;


public abstract class Player  {
    ArrayList<Card> cards;

    public void takeCard(Deck deck) throws NoValidFigure, NoValidColor {
            cards.add(deck.popCard());
    }

    public void takeCard(Deck deck, int quantity) throws NoValidFigure, NoValidColor {
        for (int i = 0; i < quantity; i++) {
            cards.add(deck.popCard());
        }
    }

    public void removeCard() {
        cards.clear();
        }
    }



